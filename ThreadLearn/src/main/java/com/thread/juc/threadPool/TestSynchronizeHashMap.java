package com.thread.juc.threadPool;

import java.util.*;

public class TestSynchronizeHashMap {

    static Map<UUID,UUID> map= Collections.synchronizedMap(new HashMap<UUID,UUID>());
    static int count = TestContants.count;
    static UUID[] keys = new UUID[count];
    static UUID[] values = new UUID[count];
    static final int threadCount = TestContants.threadCount;

    static {
        for (int i = 0; i < count; i++) {
            keys[i] = UUID.randomUUID();
            values[i] = UUID.randomUUID();
        }
    }

    static class MyThread extends Thread{
        int start;
        int gap = count/threadCount;

        public MyThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < start+gap; i++) {
                map.put(keys[i],values[i]);
            }
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new TestHashTable.MyThread(i*(count/threadCount));
        }

        for(Thread thread : threads){
            thread.start();
        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);
        System.out.println(map.size());
    }
}
