package main.com.kaptsiug.study.project.model;

import java.util.Arrays;
import java.util.Iterator;

public class OwnArrayList<E> implements Iterable<E> {
    private static final int INIT_CAPACITY = 5;
    private static final String INDEX_EXCEPTION = "ArrayList hasn't had index %d";

    private Object[] initArray;
    private int size;
    private int currentIndex = -1;

    public OwnArrayList() {
        size = INIT_CAPACITY;
        initArray = new Object[size];
    }

    public OwnArrayList(int initSize) {
        size = Math.abs(initSize);
        initArray = new Object[size];
    }


    public boolean add(E elem) {
        if (currentIndex >= size - 1) {
            size *= 2;
            initArray = Arrays.copyOf(initArray, size);
        }

        currentIndex++;
        initArray[currentIndex] = elem;
        return true;
    }

    public void add(int index, E elem) {
        checkIndexValue(index);

        if (currentIndex >= size - 1) {
            size *= 2;
            initArray = Arrays.copyOf(initArray, size);
        }

        System.arraycopy(initArray, index, initArray, index + 1, size - index);
        initArray[index] = elem;
        currentIndex++;
    }

    public void set(int index, E elem) {
        checkIndexValue(index);
        initArray[index] = elem;
    }

    public E get(int index) {
        checkIndexValue(index);
        return (E) initArray[index];
    }

    public E remove(int index) {
        checkIndexValue(index);
        E oldElem = null;

        try {
            oldElem = (E) initArray[index];
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        System.arraycopy(initArray, index + 1, initArray, index, size - 1 - index);
        currentIndex--;

        if (size - currentIndex > 4) {
            size = currentIndex+1;
            initArray = Arrays.copyOf(initArray, size);
        }

        return oldElem;
    }

    public int size() {
        return size;
    }

    private void checkIndexValue(int index) {
        if (index >= size || size < 0) {
            throw new IndexOutOfBoundsException(String.format(INDEX_EXCEPTION, index));
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new OwnIterator<>();
    }


    private class OwnIterator<E> implements Iterator<E> {
        private int index = 0;

        private OwnIterator() {
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            return (E) initArray[index++];

        }
    }
}
