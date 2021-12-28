package me.koply.test.linkedlist;

import java.util.Iterator;

public class LinkedListImpl<T> extends LinkedList<T> implements Iterable<T> {

    public static class Node<T> {
        protected Node<T> prev;
        protected T data;
        protected Node<T> next;

        Node(Node<T> prev, T data, Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T data) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null.");
        addLast(data);
    }

    @Override
    public void add(int index, T data) {
        // [0-11] -> 12 possible
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Range is 0-" + (size-1) + " but your request is " + index);
        if (data == null) throw new IllegalArgumentException("Data cannot be null.");

        var node = new Node<>(null, data, null);
        if (index == size) {
            node.prev = tail;
            tail.next = node;
        } else {
            var old = getNode(index);
            node.next = old;
            node.prev = old.prev;
            old.prev.next = node;
            old.prev = node;
        }
        size++;
    }

    @Override
    public void addLast(T data) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null.");

        if (size != 0) {
            tail.next = new Node<>(tail, data, null);
            tail = tail.next;
        } else {
            head = new Node<>(null, data, null);
            tail = head;
        }
        size++;
    }

    @Override
    public void addFirst(T data) {
        if (data == null) throw new IllegalArgumentException("Data cannot be null.");

        if (head != null) {
            var old = head;
            head = new Node<>(null, data, old);
        } else {
            head = new Node<>(null, data, null);
        }
        size++;
    }

    @Override
    public void remove(T data) {
        Node<T> n = head;
        boolean found = false;

        do {
            n = n.next;
            if (n != null && data.equals(n.data)) {
                found = true;
                break;
            }
        } while (n != null && !data.equals(n.data));

        if (found) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }
    }

    @Override
    public T remove(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException("Range is 0-" + (size-1) + " but your request is " + index);

        var n = getNode(index);
        n.prev.next = n.next;
        n.next.prev = n.prev;
        size--;

        return n.data;
    }


    @Override
    public T get(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException("Range is 0-" + (size-1) + " but your request is " + index);
        var node = getNode(index);
        return node.data;
    }

    @Override
    public T getFirst() {
        return head.data;
    }

    @Override
    public T getLast() {
        return tail.data;
    }

    private Node<T> getNode(int index) {
        // if size-index < index that means that index closer to tail
        boolean fromLast = size-index <= index;

        // size-1 because we need last index
        int currentIndex = fromLast ? size-1 : 0;
        Node<T> n = fromLast ? tail : head;
        for (int i = currentIndex; fromLast ? i > index : i < index; i = fromLast ? i-1 : i+1) {
            n = fromLast ? n.prev : n.next;
        }
        return n;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            // for bypass the (h=h.next) part at the next()
            Node<T> h = new Node<>(null,null,head);
            @Override
            public boolean hasNext() {
                return h.next != null;
            }

            @Override
            public T next() {
                return (h=h.next).data;
            }
        };
    }
}