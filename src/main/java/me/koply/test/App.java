package me.koply.test;

import me.koply.test.hashtable.Hashtable;
import me.koply.test.hashtable.Table;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        System.out.println("entered");
        Table<String, String> table = new Hashtable<>();
        table.put("asd", "asd");
        System.out.println("initialized and first kv putted");
        table.put("abc", "abc");
        table.put("qwe", "qwe");
        table.put("abc", "zxc");
        table.put("jkl", "jkl");

        System.out.println("time to ensure capacity");
        table.put("over", "loader");
        table.put("overing", "loading");
        System.out.println("overing".hashCode());
        table.put("aa", "aa");
        table.put("bb", "bb");
        System.out.println("capacity ensured. size information: ");
        System.out.println(table.size() + " - " + table.exactSize());

        System.out.println("----- puts finished, capacity ensured for last operation");
        for (var node : table) {
            System.out.println(node.key + " - " + node.value);
        }
        System.out.println("loop done");

        System.out.println("the value of the 'asd' -> " + table.get("asd"));
        var asd = table.remove("asd");
        System.out.println("the value of the removed 'asd' -> " + asd);

        var abc = table.remove("abc", "zxc");
        System.out.println("double remove 'abc' -> " + abc);

        var fal = table.remove("xxxx");
        var anfal = table.remove("xxxxx", "12313");
        System.out.println("wrongs -> " + fal + " - " + anfal);

        System.out.println("---------------");
        System.out.println("contains tests");
        System.out.println(table.containsKey("qwe"));
        System.out.println(table.containsValue("loading"));
        System.out.println(table.containsValue("asdasdd"));
        System.out.println(table.containsKey("AA"));

        System.out.println("---------------");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i<10; i++) map.put(i+"", i+"");

        System.out.println("putting all");
        table.putAll(map);
        System.out.println("putted:");
        for (var node : table) {
            System.out.println(node.key + " - " + node.value);
        }


    }

}