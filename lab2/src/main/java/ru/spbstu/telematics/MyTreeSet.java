package main.java.ru.spbstu.telematics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public  class MyTreeSet<E> implements Iterable<E> {
    private Leaf<E> root;
    private LinkedList<E> list;
    private int size = 0;

    public MyTreeSet() {
        list = new LinkedList<>();
        root = new Leaf<> (null);
    }


    public boolean add(E e) {
        if (size == 0) {
            return initRootLeaf(e);
        }

        Leaf<E> newNode = new Leaf<>(e);
        Leaf<E> lastNode = findLastLeaf(root, newNode);

        if(lastNode == null) {
            return false;
        }

        size++;
        newNode.parent = lastNode;

        if (lastNode.compareTo(newNode) < 0) {
            lastNode.right = newNode;
        } else {
            lastNode.left = newNode;
        }
        return true;
    }

    private boolean initRootLeaf(final E e) {
        root.element = e;
        size++;
        return true;
    }

    private Leaf<E> findLastLeaf(final Leaf<E> oldLeaf, final Leaf<E> newLeaf) {
        Leaf<E> lastLeaf = oldLeaf;
        int compare = oldLeaf.compareTo(newLeaf);

        if (compare < 0 && oldLeaf.right != null) {
            lastLeaf = findLastLeaf(oldLeaf.right, newLeaf);
            return lastLeaf;
        }

        if (compare > 0 && oldLeaf.left != null) {
            lastLeaf = findLastLeaf(oldLeaf.left, newLeaf);
            return lastLeaf;
        }

        if(compare == 0)
            return null;

        return lastLeaf;
    }

    public Collection<E> get() {
        return new Values();
    }

    public MyTreeSet<E> subSet(E fromElement, E toElement)
    {
        MyTreeSet<E> newCollection = new MyTreeSet<>();
        for(E e: this.get()) {
            if (fromElement.toString().compareTo(e.toString()) <= 0 && toElement.toString().compareTo(e.toString()) > 0)
                newCollection.add(e);
        }

        return newCollection;
    }
    public int size() {
        return size;
    }

    private Leaf<E> find(E e) {
        Leaf<E> eLeaf = new Leaf<>(e);
        return search(root, eLeaf);
    }

    public boolean contains(E e){
        Leaf<E> eLeaf = new Leaf<>(e);
        Leaf<E> ptr = search(root, eLeaf);
        if (ptr == null)
            return false;
        else
            return true;
    }

    private Leaf<E> search(Leaf<E> leaf, Leaf<E> eLeaf) {
        int compare = leaf.compareTo(eLeaf);

        if(compare < 0 && leaf.right != null) {
            return search(leaf.right, eLeaf);
        }

        if(compare > 0 && leaf.left != null) {
            return search(leaf.left, eLeaf);
        }

        if(compare == 0) {
            return leaf;
        }

        return null;
    }

    public void clear(){
        size = 0;
        root = null;
    }
    public boolean remove(E e) {
        Leaf<E>  deleteLeaf = find(e);
        if (deleteLeaf == null)
            return false;

        deleteEntry(deleteLeaf);
        size--;
        return true;
    }

    private void deleteEntry(Leaf<E> e) {
        if (e.left == null && e.right == null) {
            if (e == root) {
                root = null;
                return;
            }
            if (e.parent.left == e) {
                e.parent.left = null;
            }
            else {
                e.parent.right = null;
            }
        }
        else if(e.left == null) {
            Leaf<E> deletion = e.findMinR();
            E value = deletion.element;
            e.setValue(value);
            if (deletion.parent.right == deletion) {
                deletion.parent.right = deletion.right;
            }
            else {
                deletion.parent.left = deletion.right;
                if (deletion.right != null)
                    deletion.right.parent = deletion.parent;
            }
        }
        else {
            Leaf<E> deletion = e.findMaxL();
            E value = deletion.element;
            e.setValue(value);
            if (deletion.parent.left == deletion) {
                deletion.parent.left = deletion.left;
            }
            else {
                deletion.parent.right = deletion.left;
                if (deletion.left != null)
                    deletion.left.parent = deletion.parent;
            }
        }
    }

    public boolean isEmpty() {
        if(root != null && root.element != null)
            return false;
        else
            return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int count = 0;
            Iterator<Leaf<E>> iterator = new TreeIterator<>(root);

            @Override
            public boolean hasNext(){
                return iterator.hasNext();
            }

            @Override
            public E next() {
                count++;
                return iterator.next().element;
            }
        };
    }

    public class TreeIterator<E> implements Iterator<Leaf<E>> {
       private Leaf<E> next;

       public  TreeIterator(Leaf<E> root) {
           next = root;
           if(next == null)
               return;

           while (next.left != null)
               next = next.left;
       }

       private void goToLeftmost() {
           while (next.left != null) {
               next = next.left;
           }
       }

       @Override
       public boolean hasNext() {
           return next != null; //&& next.element != null;
       }

       @Override
       public Leaf<E> next() {
           if(!hasNext()) throw new NoSuchElementException();
           Leaf<E> r = next;

           if(next.right != null)
               return goRight(r);

           return goUp(r);
       }

       private Leaf<E> goRight(Leaf<E> r) {
           next = next.right;
           while(next.left != null) {
               next = next.left;
           }
           return r;
       }

       private Leaf<E> goUp(Leaf<E> r) {
           while (true) {
               if (next.parent == null) {
                   next = null;
                   return r;
               }

               if(next.parent.left == null) {
                   next = next.parent;
                   return r;
               }

               next = next.parent;
           }
       }
    }


    public class TreeIteratorValue implements Iterator<E>{
        private Leaf<E> next;

        public TreeIteratorValue(Leaf<E> root) {
            next = root;
            if(next == null)
                return;

            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        public E next(){
            if(!hasNext()) throw new NoSuchElementException();
            Leaf<E> r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return r.element;
            }

            while(true) {
                if(next.parent == null) {
                    next = null;
                    return r.element;
                }
                if(next.parent.left == next) {
                    next = next.parent;
                    return r.element;
                }
                next = next.parent;
            }
        }
    }

    class Values extends AbstractCollection<E> {
        public Iterator<E> iterator() {
            return new TreeIteratorValue(root);
        }

        public int size() {
            return MyTreeSet.this.size();
        }
    }

    class Leaf <E> implements Comparable<E> {
        private Leaf<E> parent;
        private Leaf<E> right;
        private Leaf<E> left;
        private E element;

        private Leaf(E element) {
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        @Override
        public int compareTo(Object obj) {
            Leaf <E> node = (Leaf <E>) obj;
            return this.hashCode() - node.hashCode();
        }

        public Leaf<E> findMinR(){
            Leaf<E> minR = this.right;
            while (minR.left != null)
                minR = minR.left;
            return minR;
        }

        public Leaf<E> findMaxL(){
            Leaf<E> maxL = this.left;
            while (maxL.right != null)
                maxL = maxL.right;
            return maxL;
        }

        public E setValue(E value) {
            E oldValue = this.element;
            this.element = value;
            return oldValue;
        }
    }

    public static void main(String[] args) {
        MyTreeSet<Integer> tree = new MyTreeSet<>();
        System.out.println(tree.isEmpty());
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);

        for (int i : tree.get()) {
            System.out.println(i);
        }
        System.out.println(tree.contains(-21));
        tree.add(-21);
        System.out.println(tree.contains(-21));

    }
}
