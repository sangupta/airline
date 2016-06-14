package io.airlift.airline.guava;

import java.util.Iterator;

/**
 * Implementation of PeekingIterator that avoids peeking unless necessary.
 */
public class PeekingIteratorImpl<E> implements PeekingIterator<E> {

    private final Iterator<? extends E> iterator;
    private boolean hasPeeked;
    private E peekedElement;

    public PeekingIteratorImpl(Iterator<? extends E> iterator) {
        this.iterator = GuavaUtil.checkNotNull(iterator);
    }

    @Override
    public boolean hasNext() {
        return hasPeeked || iterator.hasNext();
    }

    @Override
    public E next() {
        if (!hasPeeked) {
            return iterator.next();
        }
        E result = peekedElement;
        hasPeeked = false;
        peekedElement = null;
        return result;
    }

    @Override
    public void remove() {
        GuavaUtil.checkState(!hasPeeked, "Can't remove after you've peeked at next");
        iterator.remove();
    }

    @Override
    public E peek() {
        if (!hasPeeked) {
            peekedElement = iterator.next();
            hasPeeked = true;
        }
        return peekedElement;
    }
}
