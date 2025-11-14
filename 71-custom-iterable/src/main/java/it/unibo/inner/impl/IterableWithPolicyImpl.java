package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T>{
    private final List<T> array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(T[] array){
        this(array, new Predicate<T>() {
            @Override
            public boolean test(T element){
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(T[] array, Predicate<T> filter){
        this.array = List.of(array);
        this.filter = filter;
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter){
        this.filter = filter;
    }

    @Override
    public Iterator<T> iterator(){
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T>{
        private int index = 0;

        @Override
        public boolean hasNext(){
            while(index < array.size()){
                final T element = array.get(index);
                if(filter.test(element)){
                    return true;
                }
                index++;
            }
            return false;
        }

        @Override
        public T next(){
            if(hasNext()){
                index++;
                return array.get(index-1);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
}
