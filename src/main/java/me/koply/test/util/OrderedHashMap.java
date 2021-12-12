package me.koply.test.util;

import java.util.*;

public class OrderedHashMap<K, V> extends HashMap<K, V> implements Iterable<OrderedHashMap.Node<K, V>> {

    private final List<K> list = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        var x = super.put(key, value);
        if (x == null) list.add(key);
        return x;
    }

    public void putAll(OrderedHashMap<? extends K, ? extends V> m) {
        for (var node : m) {
            put(node.key, node.value);
        }
    }

    @Override
    public void clear() {
        list.clear();
        super.clear();
    }

    @Override
    public V remove(Object key) {
        list.remove(key);
        return super.remove(key);
    }

    @Override
    public boolean remove(Object key, Object value) {
        var x = super.remove(key, value);
        if (x) list.remove(key);
        return x;
    }

    public List<K> getOrderedKeyList() {
        return list;
    }

    public static class Node<K, V> {
        public final K key;
        public final V value;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public Iterator<OrderedHashMap.Node<K, V>> iterator() {
        return new Iterator<>() {
            int currentIndex = 0;
            @Override
            public boolean hasNext() {
                return currentIndex < list.size();
            }

            @Override
            public OrderedHashMap.Node<K, V> next() {
                var k = list.get(currentIndex++);
                return new Node<>(k, get(k));
            }
        };
    }

}