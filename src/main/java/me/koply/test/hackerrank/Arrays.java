package me.koply.test.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class Arrays {


    public static void test() {
        var x = List.of(1,2,3,4,5,6,7,8,9,10,11);
        long total = 0;
        for (int i = 5000; i<10000; i++) {
            var first = System.nanoTime();
            var y = rotateLeft(5, x);
            var last = System.nanoTime();
            total += last-first;
        }
        System.out.println((total/5000) + " avg ns");
    }

    public static long arrayManipulation(int n, List<List<Integer>> queries) {
        long[] arr = new long[n+1];
        long maxIndice = -1;
        for (List<Integer> additionList : queries) {
            for (int j = additionList.get(0); j <= additionList.get(1); j++) {
                arr[j] += additionList.get(2);
                if (arr[j] > maxIndice) maxIndice = arr[j];
            }
        }
        return maxIndice;
    }

    public static List<Integer> rotateLeft(int d, List<Integer> arr) {
        List<Integer> temp = new ArrayList<>(arr.size());
        for (int i = d % arr.size(); i!=d || temp.size() == 0;) {
            temp.add(arr.get(i));
            i = (i+1) % arr.size();
        }
        return temp;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static List<Integer> rotateLeftFast(int d, List<Integer> arr) {
        var arr1 = arr.toArray();
        var temp = new Integer[arr.size()];
        System.arraycopy(arr1, d, temp, 0, arr.size()-d);
        System.arraycopy(arr1, 0, temp, arr.size()-d, d);
        return java.util.Arrays.asList(temp);
    }

}