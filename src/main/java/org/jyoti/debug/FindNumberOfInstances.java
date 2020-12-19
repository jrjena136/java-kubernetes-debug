package org.jyoti.debug;

public class FindNumberOfInstances {
    static int count;
    public FindNumberOfInstances() {
        count++;
        System.out.println("Instance created: " + count);
    }

    public static void main(String[] args) {
        FindNumberOfInstances o1 = new FindNumberOfInstances();
        FindNumberOfInstances o2 = new FindNumberOfInstances();
        FindNumberOfInstances o3 = new FindNumberOfInstances();
        o1 = null;
        System.gc();
    }

    @Override protected void finalize() throws Throwable {
        count--;
        System.out.println("Instance removed");
        super.finalize();
        System.out.println("Active instance: " + count);
    }
}
