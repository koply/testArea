package me.koply.test.hashtable;

import me.koply.test.util.Checks;
import me.koply.test.util.IntegerUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class inspirated from HashMap.java for learn all the hashtable things.
 * Use only sync.
 */
public class Hashtable<K, V> extends Table<K, V> {

    public static class Node<K, V> {
        public final K key;
        public final V value;
        public final int hash; // the key's abs(hash)

        public Node(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }
    }

    private transient Node<K, V>[] table;
    private int filledBuckets = 0;

    private static final float LOAD_FACTOR = 0.7f;
    private static final int DEFAULT_SIZE = 7;

    public Hashtable() {
        //noinspection unchecked
        table = (Node<K,V>[]) new Node[DEFAULT_SIZE];
    }

    public Hashtable(int initialCapacity) {
        int capacity = IntegerUtil.isPrime(initialCapacity)
                ? initialCapacity
                : IntegerUtil.nextPrime(initialCapacity);

        //noinspection unchecked
        table = (Node<K,V>[]) new Node[capacity];
    }

    @Override
    public int size() {
        return filledBuckets;
    }

    @Override
    public int exactSize() {
        return table.length;
    }

    @Override
    public boolean isEmpty() {
        return filledBuckets == 0;
    }

    /**
     * @param key key object
     * @param lastIndex length-1
     * @return index of the key
     */
    private int indexOf(Object key, int lastIndex) {
        return Math.abs(Objects.hashCode(key)) % lastIndex;
    }

    /**
     * @param key to be taken the index of
     * @return the index of the specified key
     */
    private int exactIndex(Object key, Node<K,V>[] tab) {
        int i = 0, max = tab.length;
        int n = indexOf(key, max);
        do {
            if (tab[n] != null && tab[n].key == key) return n;
            n = (n+1) % (max-1);
        } while ((++i) < max);
        return -1;
    }

    private int exactIndex(int hash, Node<K,V>[] tab) {
        int i = 0, max = tab.length;
        int n = hash % (max-1);
        do {
            if (tab[n] != null && tab[n].hash == hash) return n;
            n = (n+1) % (max-1);
        } while ((++i) < max);
        return -1;
    }

    public boolean containsKey(Object key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        Node<K,V>[] tab = table;
        return exactIndex(key, tab) != -1;
    }

    public boolean containsValue(Object value) {
        if (value == null) throw new IllegalArgumentException("Value cannot be null.");
        Node<K,V>[] tab; V v;
        if ((tab = table) != null && filledBuckets > 0) {
            for (Node<K,V> node : tab) {
                if (node != null && ((v = node.value) == value || value.equals(v))) return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        return this.getOrDefault(key, null);
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K,V>[] tab = table;
        var index = exactIndex(key, tab);
        return index == -1 ? defaultValue : tab[index].value;
    }

    @Override
    public V remove(Object key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null.");
        Node<K,V>[] tab = table;
        int index = exactIndex(key, tab);
        if (index == -1) return null;
        var node = tab[index];
        tab[index] = null;
        filledBuckets--;
        return node.value;
    }

    @Override
    public boolean remove(K key, V value) {
        if (Checks.isAnyNull(key, value)) throw new IllegalArgumentException("Key or value cannot be null.");
        Node<K,V>[] tab = table; V v;
        int index = exactIndex(key, tab);
        if (index == -1) return false;
        boolean match = (v = tab[index].value) == value || value.equals(v);
        if (match) {
            tab[index] = null;
            filledBuckets--;
        }
        return match;
    }

    @Override
    public V put(K key, V value) {
        if (Checks.isAnyNull(key, value)) throw new IllegalArgumentException("Key or value cannot be null.");
        if (table.length * LOAD_FACTOR >= filledBuckets) {
            // ensures with linear probing
            ensureCapacity(table.length+1);
        }
        int hash = Math.abs(Objects.hashCode(key));
        return internalPut(key, value, hash);
    }

    @Override
    public boolean putIfAbsent(K key, V value) {
        if (Checks.isAnyNull(key, value)) throw new IllegalArgumentException("Key or value cannot be null.");
        int hash = Math.abs(Objects.hashCode(key));
        Node<K,V>[] tab = table;
        boolean x = exactIndex(hash, tab) == -1;
        if (x) internalPut(key, value, hash);
        return x;
    }

    /**
     * @param key key
     * @param value value
     * @param hash -> Math.abs(Objects.hashCode(key))
     * @return old value of that key
     */
    private V internalPut(K key, V value, int hash) {
        Node<K,V>[] tab = table; int length = tab.length;
        // uses linear probing
        int n = hash % (length-1);
        do {
            if (tab[n] == null || tab[n].key == key) { break; }
            n = (n+1) % length;
        } while (tab[n] != null);

        V old = null;
        if (tab[n] != null) old = tab[n].value;
        else filledBuckets++;

        tab[n] = new Node<>(key, value, hash);
        return old;
    }

    private void ensureCapacity(int min) {
        var old = table;
        var newSize = IntegerUtil.isPrime(min) ? min : IntegerUtil.nextPrime(min);
        table = reHashTable(old, newSize);
    }

    private Node<K, V>[] reHashTable(Node<K, V>[] old, int newSize) {
        //noinspection unchecked
        Node<K, V>[] temp = (Node<K,V>[]) new Node[newSize];
        for (var node : old) {
            if (node == null) continue;
            int n = node.hash % newSize;
            while (temp[n] != null) { // linear probing
                n = (n+1) % newSize;
            }
            temp[n] = node;
        }
        return temp;
    }

    private void makeFit(int mapSize) {
        var maxSize = mapSize + filledBuckets;
        var tab = table;
        if (tab.length * LOAD_FACTOR >= maxSize) {
            maxSize = IntegerUtil.nextPrime(maxSize);
            while (tab.length * LOAD_FACTOR >= maxSize) {
                maxSize = IntegerUtil.nextPrime(maxSize+1);
            }
            table = reHashTable(tab, maxSize);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map == null || map.isEmpty()) return;
        makeFit(map.size());
        for (var entry : map.entrySet()) {
            internalPut(entry.getKey(), entry.getValue(), Math.abs(Objects.hashCode(entry.getKey())));
        }
    }

    @Override
    public void putAll(Table<? extends K, ? extends V> map) {
        if (map == null || map.isEmpty()) return;
        makeFit(map.size());
        for (var node : map) {
            internalPut(node.key, node.value, node.hash);
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Node<K,V>[] tab = table;
        int hash = Math.abs(Objects.hashCode(key));
        var index = exactIndex(hash, tab);
        boolean indexExists = index != -1;
        V result = remappingFunction.apply(key, indexExists ? tab[index].value : null);
        internalPut(key, result, hash);
        return result;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        Node<K,V>[] tab = table;
        int hash = Math.abs(Objects.hashCode(key));
        var index = exactIndex(hash, tab);
        if (index == -1) {
            V result = mappingFunction.apply(key);
            internalPut(key, result, hash);
            return result;
        }
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Node<K,V>[] tab = table;
        int hash = Math.abs(Objects.hashCode(key));
        var index = exactIndex(hash, tab);
        if (index != -1) {
            V result = remappingFunction.apply(key, tab[index].value);
            tab[index] = new Node<>(key, result, hash);
            return result;
        }
        return null;
    }

    @Override
    public void clear() {
        this.clear(false);
    }

    @Override
    public void clear(boolean resetSize) {
        if (resetSize) {
            //noinspection unchecked
            table = (Node<K,V>[]) new Node[DEFAULT_SIZE];
            filledBuckets = 0;
            return;
        }
        Node<K,V>[] tab;
        if ((tab = table) != null && filledBuckets > 0) {
            filledBuckets = 0;
            for (int i = -1; ++i < tab.length;)
                tab[i] = null;
        }
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        final int lastIndex = table.length-1;
        final Node<K,V>[] tab = table;
        return new Iterator<>() {
            int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return table != null && currentIndex <= lastIndex && check();
            }

            private boolean check() {
                while (tab[currentIndex] == null) {
                    if (currentIndex < lastIndex) currentIndex++;
                    else return false;
                }
                return true;
            }

            @Override
            public Node<K, V> next() {
                return tab[currentIndex++];
            }
        };
    }
}