package me.koply.test.hashtable;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class Table<K,V> implements Iterable<Hashtable.Node<K, V>> {

    /**
      * @return bucket size
     */
    public abstract int size();

    /**
     * @return filled bucket's number
     */
    public abstract int exactSize();
    public abstract boolean isEmpty();

    public abstract V put(K key, V value);

    /**
     * @return true when putted
     */
    public abstract boolean putIfAbsent(K key, V value);

    public abstract V getOrDefault(Object key, V defaultValue);
    public abstract V get(Object key);
    public abstract V remove(Object key);
    public abstract boolean remove(K key, V value);

    public abstract boolean containsKey(Object key);
    public abstract boolean containsValue(Object value);

    public abstract void putAll(Map<? extends K, ? extends V> map);
    public abstract void putAll(Table<? extends K, ? extends V> table);

    public abstract V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction);
    public abstract V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction);
    public abstract V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction);

    /**
     * clears but size does not change
     */
    public abstract void clear();

    /**
     * @param resetSize if true, size changes to 7 (initial capacity)
     */
    public abstract void clear(boolean resetSize);

}