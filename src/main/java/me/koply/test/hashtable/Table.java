package me.koply.test.hashtable;

import java.util.Map;

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
    public abstract V get(Object key);
    public abstract V remove(Object key);
    public abstract boolean remove(K key, V value);

    public abstract boolean containsKey(Object key);
    public abstract boolean containsValue(Object value);

    public abstract void putAll(Map<? extends K, ? extends V> map);
    public abstract void putAll(Table<? extends K, ? extends V> table);

    /**
     * clears but size does not change
     */
    public abstract void clear();

    /**
     * @param resetSize if true, size changes to 7 (initial capacity)
     */
    public abstract void clear(boolean resetSize);

}