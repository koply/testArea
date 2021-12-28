package me.koply.test.linkedlist;

public abstract class LinkedList<T> {

    public abstract int size();

    public abstract void add(T data);
    public abstract void add(int index, T data);
    public abstract void addFirst(T data);
    public abstract void addLast(T data);

    public abstract void remove(T data);
    public abstract T remove(int index);

    public abstract T get(int index);
    public abstract T getFirst(); // aka head
    public abstract T getLast();  // aka tail

}