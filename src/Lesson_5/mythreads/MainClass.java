package Lesson_5.mythreads;

import java.util.Arrays;

public class MainClass {
    private static final int size = 1000000;
    private static final int h = size / 4;
    private float[] arr;

    public MainClass() {
        arr = new float[size];
    }

    private long countTime() {
        Arrays.fill(arr, 1);
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return System.currentTimeMillis() - timeStart;
    }

    private long countTimeTwo() {
        Arrays.fill(arr, 1);
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        float[] a3 = new float[h];
        float[] a4 = new float[h];

        long timeStart = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.arraycopy(arr, h*2, a3, 0, h);
        System.arraycopy(arr, h*3, a4, 0, h);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a3[i] = (float) (a3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    a4[i] = (float) (a4[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.arraycopy(a3, 0, arr, h*2, h);
        System.arraycopy(a4, 0, arr, h*3, h);

        return System.currentTimeMillis() - timeStart;
    }

    public static void main(String[] args) {
        MainClass a = new MainClass();
        System.out.println("Время работы метода 1: " + a.countTime() + " ms ");
        System.out.println("Время работы метода 2: " + a.countTimeTwo() + " ms ");
    }
}
