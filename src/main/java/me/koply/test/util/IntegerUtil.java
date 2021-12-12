package me.koply.test.util;

import static java.lang.Math.sqrt;

public class IntegerUtil {

    public static boolean isPrime(long n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long) sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }

    public static int nextPrime(int input){
        int counter;
        input++;
        while (true){
            int l = (int) sqrt(input);
            counter = 0;
            for (int i = 2; i <= l; i ++) {
                if (input % i == 0) counter++;
            }

            if (counter == 0) return input;
            else input++;
        }
    }

}