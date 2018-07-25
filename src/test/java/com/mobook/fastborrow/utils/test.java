package com.mobook.fastborrow.utils;

import java.util.ArrayList;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 9:51 2018\7\21 0021
 */
public class test {
    private static ArrayList<Demo> arrayFirst = new ArrayList<Demo>();
    private static ArrayList<ArrayList<Demo>> arrayTwo = new ArrayList<ArrayList<Demo>>();

    public static void main(String[] args) {
        //初始化arrayFirst
        setUpDate();
        ArrayList<Demo> ItemList = new ArrayList<Demo>();
        for (Demo item:arrayFirst){
            ItemList.add(item);
            System.out.println(ItemList.size());
            if (ItemList.size() == 2){
                arrayTwo.add(ItemList);
                System.out.println("clear");
                ItemList.clear();
            }
        }
        System.out.println(arrayTwo.size());
        for (ArrayList<Demo> item:arrayTwo){
            for (Demo demo:item){
                System.out.print(demo.toString());
            }
            System.out.println();
        }

    }

    private static void setUpDate(){
        Demo demo1 = new Demo(1,"han");
        Demo demo2 = new Demo(1,"hu");
        Demo demo3 = new Demo(2,"zhang");
        Demo demo4 = new Demo(2,"wang");
        arrayFirst.add(demo1);
        arrayFirst.add(demo2);
        arrayFirst.add(demo3);
        arrayFirst.add(demo4);
    }

}
