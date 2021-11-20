package com.example.demo;

import java.util.*;


public class Study {
    public static void main(String[] args) throws Exception {
        List<String> lst = new Stack<>();
    }

}

/**
 * 탑 클래스를 여러개 만드는 것은 좋지 않지만 학습을 위해 진행했습니다.
 */

abstract class Human {

    void speak() {
        System.out.println("");
    }

}

interface Teaching {
    String name = "Honux";

    void teachProgramming();
}

class Jun extends Human {

    @Override
    public void speak() {
        System.out.println("준이 말을 한다.");
    }
}

class Hoo extends Human {

    @Override
    public void speak() {
        System.out.println("후가 말을 한다. ");
    }

}

class Donngi extends Human {

    @Override
    public void speak() {
        System.out.println("동기도 말을 한다.");
    }
}

class Pio extends Human {

    @Override
    public void speak() {
        System.out.println("피오도 말을한다.");
    }

}

class Mains {
    public static void main(String[] args) throws Exception {
        /**
         *  타입 계층 형성
         * */
        Human donggi = new Donngi();
        donggi.speak();

        Human hoo = new Hoo();
        Human pio = new Pio();

        Map<Human, String> store = new HashMap<>();

        store.put(donggi, "동기");
        store.put(hoo, "후");
        store.put(pio, "피오");

        Set<Human> set = new HashSet<>();
        set.add(donggi);
        set.add(hoo);
        set.add(pio);

        Human jun = new Jun();
        jun.speak();
    }
}
