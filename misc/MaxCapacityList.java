package project.misc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * {@link MaxCapacityList} is a list with a maximal capacity.
 *
 * @param <T> : the type of objects in this list
 */
public class MaxCapacityList<T> implements List<T>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final List<T> list;
    private final int maxSize;

    /**
     * Construct a new {@link MaxCapacityList}.
     * @param maxSize : the max size of the list
     */
    public MaxCapacityList(int maxSize) {
        this.list = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
    }

    /**
     * Query whether this list has space left.
     * @return true if it is. false otherwise.
     */
    public boolean hasSpace() {
        return this.list.size() < this.maxSize;
    }

    /**
     * Get the first elements of a given collection that can be inserted into this list.
     * @param coll : the collection
     * @return the elements
     */
    private Collection<T> firstElements(Collection<? extends T> coll) {
        // max amount of elements that can be inserted
        int maxElements = this.maxSize - this.list.size();

        // the new collection which will hold the elements
        Collection<T> newColl = new LinkedList<>();

        /* iterate over the elements of the given collection and add the elements that can be added */
        Iterator<? extends T> iterator = coll.iterator();
        int elems = 0;
        while (iterator.hasNext() && elems < maxElements) {
            newColl.add(iterator.next());
            elems++;
        }

        // return the elements that can be added
        return newColl;
    }

    @Override
    public boolean add(T arg0) {
        if (hasSpace()) {
            return this.list.add(arg0);
        }

        return false;
    }

    @Override
    public void add(int arg0, T arg1) {
        if (hasSpace()) {
            this.list.add(arg0, arg1);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        return this.list.addAll(firstElements(arg0));
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends T> arg1) {
        return this.list.addAll(arg0, firstElements(arg1));
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean contains(Object arg0) {
        return this.list.contains(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return this.list.containsAll(arg0);
    }

    @Override
    public T get(int arg0) {
        return this.list.get(arg0);
    }

    @Override
    public int indexOf(Object arg0) {
        return this.list.indexOf(arg0);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override
    public int lastIndexOf(Object arg0) {
        return this.list.lastIndexOf(arg0);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int arg0) {
        return this.list.listIterator(arg0);
    }

    @Override
    public boolean remove(Object arg0) {
        return this.list.remove(arg0);
    }

    @Override
    public T remove(int arg0) {
        return this.list.remove(arg0);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return this.list.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return this.list.retainAll(arg0);
    }

    @Override
    public T set(int arg0, T arg1) {
        return this.list.set(arg0, arg1);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public List<T> subList(int arg0, int arg1) {
        return this.list.subList(arg0, arg1);
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @SuppressWarnings("hiding")
    @Override
    public <T> T[] toArray(T[] arg0) {
        return this.list.toArray(arg0);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
