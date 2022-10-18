package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class FList<A> implements Iterable<A> {

    public final boolean isNotEmpty() {
        return this instanceof Cons;
    }

    public final boolean isEmpty() {
        return this instanceof Nil;
    }

    public int lengthHelp(int acc){
        if (isEmpty()) return acc;
        return tail().lengthHelp(acc+1);
    }

    public final int length() {
        return lengthHelp(0);
    }

    public abstract A head();

    public abstract FList<A> tail();

    public static <A> FList<A> nil() {
        return (Nil<A>) Nil.INSTANCE;
    }

    public final FList<A> cons(final A a) {
        return new Cons(a, this);
    }

    public final <B> FList<B> map(Function<A,B> f) {
        if (isEmpty()) return nil();
        FList<B> tail = tail().map(f);
        return new Cons(f.apply(head()), tail);
    }

    public final FList<A> filter(Predicate<A> f) {
        if (isEmpty()) return nil();
        FList<A> tail = tail().filter(f);
        if (!f.test(head())) return tail;
        return new Cons(head(), tail);
    }


    public Iterator<A> iterator() {
        return new Iterator<A>() {
            int size = length();
            FList<A> list = FList.this;


            public boolean hasNext() {
                if (size != length()) throw new ConcurrentModificationException();
                return list.isNotEmpty();
            }

            public A next() {
                A ret = list.head();
                list = list.tail();
                return ret;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    private static final class Nil<A> extends FList<A> {
        public static final Nil<Object> INSTANCE = new Nil();

        public A head() {throw new IllegalArgumentException();}

        public FList<A> tail() {throw new IllegalArgumentException();}
    }

    private static final class Cons<A> extends FList<A> {
        A value;
        FList<A> next;

        Cons(A value, FList<A> next){
            this.value = value;
            this.next = next;
        }

        public A head() {return value;}

        public FList<A> tail() {return next;}
    }

    public static void main(String[] args) {
        FList<Integer> fl = nil();
        fl = fl.cons(1);
        fl = fl.cons(2);
        fl = fl.cons(3);
        for (int i : fl){
            System.out.printf("%d ", i);
        }
        System.out.println();
        for (int i : fl){
            System.out.printf("%d ", i);
            for (int j : fl){
                System.out.printf("%d ", j);
            }
            System.out.println();
        }
        fl = fl.map((i) -> i*i);
        for (int i : fl){
            System.out.printf("%d ", i);
        }
        System.out.println();
        fl = fl.filter((i) -> i%2 == 0);
        for (int i : fl){
            System.out.printf("%d ", i);
        }

    }
}