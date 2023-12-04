package dev.cisnux.datastructures.dynamicarray;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class Array<T> implements Iterable<T> {
    private T[] arr;
    private int length = 0; // length use thinks array is
    private int capacity = 0; // actual array size

    public Array() {
        this(16);
    }

    public Array(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        this.capacity = capacity;
        // initialize an array and casting by T
        arr = (T[]) new Object[capacity];
    }

    // O(1)
    public int size() {
        return length;
    }

    // O(1)
    public boolean isEmpty() {
        return size() == 0;
    }

    // O(1)
    public T get(int index) {
        return arr[index];
    }

    // O(1)
    public void set(int index, T arr) {
        this.arr[index] = arr;
    }

    // O(n)
    public void clear() {
        for (int i = 0; i < capacity; i++)
            arr[i] = null;
        length = 0;
    }

    // worst case: O(n), best case: 0(1)
    public Array<T> add(T element) {
        // Time to resize!
        if (length + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2; // double the size
            T[] newArr = (T[]) new Object[capacity];
            // copy all elements in old array to new array
            // for (int i = 0; i < length; i++) newArr[i] = arr[i];
            System.arraycopy(arr, 0, newArr, 0, length);
            arr = newArr; // arr has extra null padded
        }
        // add new element
        arr[length++] = element;
        return this;
    }

    // O(n)
    public T removeAt(int index) {
        if (index >= length || index < 0) throw new IndexOutOfBoundsException();
        T data = arr[index];
        T[] newArr = (T[]) new Object[length - 1];
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (i == index) j--; // skip over index by fixing j temporarily
            else newArr[j] = arr[i];
        }
        arr = newArr;
        capacity = --length;
        return data;
    }

    // O(n)
    public boolean remove(T element) {
        int index = indexOf(element);
        if (index == -1) return false;
        removeAt(index);
        return true;
    }

    // O(n)
    public int indexOf(Object obj) {
        for (int i = 0; i < length; i++)
            if (arr[i].equals(obj))
                return i;
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }


    // Iterator is still fast but not as fast as iterative for loop
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (length == 0) return "[]";
        else {
            final StringBuilder sb = new StringBuilder(length).append("[");
            for (int i = 0; i < length - 1; i++)
                sb.append(arr[i]).append(", ");
            return sb.append(arr[length - 1]).append("]").toString();
        }
    }
}
