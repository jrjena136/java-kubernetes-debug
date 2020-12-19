package org.jyoti.debug;

import java.util.Arrays;

public class FindMinWindowInString {
    public static void main(String[] args) {
        String input = "ADOBECODEBANC";
        String strT = "ABC";
        long start = System.currentTimeMillis();
        String result = findMinWindow(input.length(), strT.length(), input.toLowerCase(), strT.toLowerCase());
        long end = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("finished execution in " + (end - start) + " ms ");

    }

    static String findMinWindow(int n, int k, String input, String strT) {
        String finalResult = null;
        for (int i = 0; i <= n - k; i++) {
            StringBuffer result = new StringBuffer();
            String temp = input.substring(i, i+k);
            char []inputArr = strT.toCharArray();
            Arrays.sort(inputArr);
            char []inputTmpArr= temp.toCharArray();
            Arrays.sort(inputTmpArr);
            for (int m = 0; m < inputArr.length; m++) {
                if (new String(inputTmpArr).contains(String.valueOf(inputArr[m]))) {
                    result = result.append(inputArr[m]);
                }
            }
            char []resultArr = result.toString().toCharArray();
            Arrays.sort(resultArr);
            if (Arrays.equals(resultArr, inputArr)) {
                finalResult = temp;
                break;
            }
        }
        if (finalResult != null) {
            return finalResult.toUpperCase();
        } else {
            return findMinWindow(input.length(), ++k, input, strT).toUpperCase();
        }
    }
}
