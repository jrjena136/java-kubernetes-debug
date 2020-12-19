package org.jyoti.debug;

public class Test {
    public static void main(String[] args) {
        System.out.println(M1());
    }

    public static int M1() {
        try {
            System.out.println("In Try");
            return 10;
        } catch(ArithmeticException ae) {
            System.out.println("In Catch");
        } finally {
            System.out.println("In Finally");
            if (true && false)
               return 20;
        }
        return 30;
    }
}
