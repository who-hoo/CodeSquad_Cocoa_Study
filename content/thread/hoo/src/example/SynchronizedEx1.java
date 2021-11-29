package example;

import java.util.Arrays;
import java.util.List;

class SynchronizedBungeobbangStore1 {

    private int bakedBungeobbang = 10;

    public int getBakedBungeobbang() {
        return bakedBungeobbang;
    }

    public synchronized void sell(String customer, int count) {
        System.out.println("동기 : 어서오세요 " + customer + ", 붕어빵 지금 " + bakedBungeobbang + "개 있어요");
        System.out.println(customer + " : " + count + "개 주세요.");

        if (bakedBungeobbang < count) {
            System.out.println("동기 : 붕어빵이 부족해요...");
            System.out.println();
            return;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        bakedBungeobbang -= count;
        System.out.println("동기 : " + count + "개 팔아서 " + bakedBungeobbang + "개 남았군");
        System.out.println();
    }
}

class SynchronizedOrder1 implements Runnable {

    SynchronizedBungeobbangStore1 store = new SynchronizedBungeobbangStore1();

    List<String> customers = Arrays.asList("노리", "준", "피오", "후");

    public void run() {
        while(store.getBakedBungeobbang() > 0) {
            String customer = customers.get((int)(Math.random()* customers.size()));
            store.sell(customer, (int)(Math.random()*10 +1));
        }
    }
}

public class SynchronizedEx1 {
    public static void main(String[] args) {
        Runnable order = new SynchronizedOrder1();
        new Thread(order).start();
        new Thread(order).start();
    }
}
