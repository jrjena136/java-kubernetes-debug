package org.jyoti.debug;
@FunctionalInterface
public interface Action<T, U, V> {
    public V doAction(T t, U u);
    default void count() {
        // some code
    }
    default void display() {
        // some code
    }
    public static void m1() {
        //
    }
}
