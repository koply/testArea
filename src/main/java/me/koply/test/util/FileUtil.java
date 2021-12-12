package me.koply.test.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static String readFile(File file) {
        if (!file.exists()) {
            System.out.println("Okunacak dosya yok?");
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            readAll(br, sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        readAll(rd, sb);
        return sb.toString();
    }

    public static void readAll(Reader rd, StringBuilder sb) throws IOException {
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
    }

    public static void writeFile(File file, String str) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}