package dev.cisnux.datastructures;

import dev.cisnux.datastructures.dynamicarray.Array;
import dev.cisnux.datastructures.linkedlist.SingleLinkedList;

import java.util.HashMap;

public class Main {
    public static class Animal{
        void say(){
            System.out.println("Hello");
        }
    }

    public static class Cat extends Animal{
        void say() {
            System.out.println("Hello World");
        }
    }

    public static void main(String[] args) {
//        final Array<Integer> dArray = new Array<>();
//        dArray.add(2)
//                .add(3);
//        System.out.println(dArray);
//        final int data = dArray.removeAt(1);
//        System.out.println(data);
//        final SingleLinkedList<Integer> singleLinkedList = new SingleLinkedList<>();
//        singleLinkedList.add(1).add(2).add(7);
//        System.out.println(singleLinkedList);
//        System.out.println(singleLinkedList.remove(2));
//        System.out.println(singleLinkedList);
//        final HashMap<String, Integer> a = new HashMap<>();
//        a.put("data", 202);
//        System.out.println(a.get("data"));
        final Cat cat = new Cat();
        cat.say();
    }
}