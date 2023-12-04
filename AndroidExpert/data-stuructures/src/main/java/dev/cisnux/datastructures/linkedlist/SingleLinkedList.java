package dev.cisnux.datastructures.linkedlist;

import java.util.Iterator;
import java.util.Objects;

public class SingleLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private static class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public SingleLinkedList<T> add(T element) {
        addLast(element);
        return this;
    }

    public SingleLinkedList<T> addLast(T element) {
        final Node<T> newData = new Node<>(element);
        if (isEmpty()) {
            head = newData;
        } else {
            tail.next = newData;
        }
        tail = newData;
        size++;
        return this;
    }

    public SingleLinkedList<T> addFirst(T element) {
        final Node<T> newData = new Node<>(element);
        if (isEmpty()) {
            head = newData;
            tail = head;
        } else {
            tail.next = newData;
            tail = newData;
        }
        return this;
    }

    // Check the value of the first node if it exists, O(1)
    public T takeFirst() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        return head.data;
    }

    // Check the value of the last node if it exists,O(1)
    public T takeLast() {
        if (isEmpty()) throw new RuntimeException("Empty List");
        return tail.data;
    }

    // Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty List");

        // Extract the data at the head and move
        // the head pointer forwards one node
        T data = head.data;
        head = head.next;
        size--;

        // if the list is empty after removed the first node
        // then set the tail to null
        if (isEmpty()) tail = null;

        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        // Can't remove data from an empty list
        if (isEmpty()) throw new RuntimeException("Empty List");

        T data = tail.data;
        tail = head;

        for (int i = 0; i < size() - 2; i++) {
            tail = tail.next;
        }

        return data;
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(T element) {
        Node<T> tempTrav = null;
        if (element == head.data) {
            removeFirst();
        }
        for (Node<T> trav = head; trav != null; tempTrav = trav, trav = trav.next) {
            if (Objects.equals(element, trav.data)) {
                Objects.requireNonNull(tempTrav).next = trav.next;
                size--;
                return true;
            }
        }
        return false;
    }

    // Find the index of a value in the linked list, O(n)
    public int indexOf(T element) {
        Node<T> trav = head;
        for (int index = 0; trav != null; trav = trav.next, index++) {
            if (Objects.equals(element, trav.data)) {
                return index;
            }
        }
        return -1;
    }

    public boolean contains(T element) {
        return indexOf(element) != -1;
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    private int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> trav = head;
        for (int i = 0; i < size - 1; i++, trav = trav.next) {
            sb.append(trav.data);
            sb.append(", ");
        }
        sb.append(tail.data).append("]");
        return sb.toString();
    }
}
