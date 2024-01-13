package me.koply.test.util;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Matrix {

    private final static Scanner SC = new Scanner(System.in);

    private static final int[][] defaultArray1 = { {1,2,3},
            {2,3,4},
            {3,4,5} };

    private static final int[][] defaultArray2 = { {2,3,4},
            {3,4,5},
            {4,5,6} };

    public static int[][] readMatrix() {
        // array uzunlukları kullanıcıdan alınıyor
        System.out.println("Lütfen NxM için N giriniz: ");
        int n = SC.nextInt();
        System.out.println("Lütfen NxM için M giriniz: ");
        int m = SC.nextInt();

        // array oluşturuluyor
        int[][] array = new int[n][m];

        // array içine veriler okunuyor
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<m; j++) {
                System.out.printf("[%d][%d]: ", i, j);
                array[i][j] = SC.nextInt();
            }
        }

        return array;
    }

    public static void matrixMultiplication() {
        int[][] a = defaultArray1;
        int[][] b = defaultArray2;

        // (MxN) * (NxP) = (MxP)
        // M: ilk matrisin satır sayısı
        // N: ilk matrisin sütun, ikinci matrisin satır sayısı
        // P: ikinci matrisin sütun sayısı

        int M = a.length;
        int N = b.length;
        int P = b[0].length;

        // ilk matrisin sütun sayısı ile ikinci matrisin satır sayısı eşit olmalı
        if (a[0].length != b.length) {
            System.out.println("MxN * NxP, n'ler esit degil.");
            return;
        }

        int[][] matrix = new int[M][P];
        // mi -> matrix i
        for (int mi = 0; mi<M; mi++) {
            // mj -> matrix j
            for (int mj = 0; mj<P; mj++) {
                // matrix[mi][mj]
                // AxB -> A matrisinin mi satırında gezeceğiz
                // B matrisinin mj sütununda gezeceğiz

                // a matrisi için mi satırındaki i. sütun
                // b matrisi için i satırındaki mj. sütun
                // bu iki verinin çarpımı toplanır ve çarpım sonucunda
                // oluşacak olan matrisin [mi][mj] indexine koyulur
                for (int i = 0; i<N; i++) {
                    matrix[mi][mj] += a[mi][i] * b[i][mj];
                }
            }
        }
        // görselleştirmeden anlamak zor

        printMatrix(matrix);
    }

    public static void matrixAddition() {
        // iki matrisin üst üste gelenleri toplanır ve yeni bir matrise yazılır
        // matris boyutları aynı olmak zorunda
        int[][] a = defaultArray1;
        int[][] b = defaultArray2;

        if (a.length != b.length || a[0].length != b[0].length) {
            System.out.println("Matris boyutları eşit değil. Toplama yapılamaz");
            return;
        }

        int y = a.length; // satır sayısı
        int x = a[0].length; // sütun sayısı

        int[][] result = new int[y][x];

        for (int i = 0; i<y; i++) {
            for (int j = 0; j<x; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }

        printMatrix(result);
    }

    // bifunction -> <parameter, parameter, return-type>
    // (a[i][j], b[i][j], result goes into the array that to be return)
    public static int[][] matrixIteration(BiFunction<Integer, Integer, Integer> function, int[][] a, int[][] b) {
        int y = a.length, x = a[0].length;
        if (y != b.length || x != b[0].length) {
            throw new IllegalArgumentException("Array uzunlukları eşit değil. [Matrix#matrixIteration]");
        }

        int[][] result = new int[y][x];

        for (int i = 0; i<y; i++) {
            for (int j = 0; j<x; j++) {
                result[i][j] = function.apply(a[i][j], b[i][j]);
            }
        }

        return result;
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println(Arrays.deepToString(matrix));
    }

    public static void main(String[] args) {
        matrixMultiplication();
        matrixAddition();

        int[][] x;

        System.out.println("Addition with iteration: ");

        x = matrixIteration(Integer::sum, defaultArray1, defaultArray2);
        printMatrix(x);

        System.out.println("Subtraction with iteration: ");
        x = matrixIteration((a, b) -> a-b, defaultArray1, defaultArray2);
        printMatrix(x);

        System.out.println("Selecting minimum with iteration: ");
        x = matrixIteration(Integer::min, defaultArray1, defaultArray2);
        printMatrix(x);

        System.out.println("Selection maximum with iteration: ");
        x = matrixIteration(Integer::max, defaultArray1, defaultArray2);
        printMatrix(x);

        System.gc();
        System.out.println("Bye, bye!");
    }

}