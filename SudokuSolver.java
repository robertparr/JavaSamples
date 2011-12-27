package services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {

    private static final int[] elements = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private int[] a;
    private int n, r;
    private BigInteger numLeft, total;
 
    private void init(int places) {
        r = places;
        n = elements.length;
        if (r > n) {
            throw new IllegalArgumentException();
        }
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        a = new int[r];
        BigInteger nFact = getFactorial(n);
        BigInteger rFact = getFactorial(r);
        BigInteger nminusrFact = getFactorial(n - r);
        total = nFact.divide(rFact.multiply(nminusrFact));
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger(total.toString());
    }

    private boolean hasMore() {
        return numLeft.compareTo(BigInteger.ZERO) == 1;
    }

    private static BigInteger getFactorial(int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply(new BigInteger(Integer.toString(i)));
        }
        return fact;
    }

    private int[] getNext() {
        if (numLeft.equals(total)) {
            numLeft = numLeft.subtract(BigInteger.ONE);
            return a;
        }
        int i = r - 1;
        while (a[i] == n - r + i) {
            i--;
        }
        a[i] = a[i] + 1;
        for (int j = i + 1; j < r; j++) {
            a[j] = a[i] + j - i;
        }

        numLeft = numLeft.subtract(BigInteger.ONE);
        return a;
    }

    public List<List<Integer>> getNumbers(int target, int places) {

        List<List<Integer>> lines = new ArrayList<List<Integer>>();
        init(places);
         
        while (hasMore()) {
            List<Integer> combination = new ArrayList<Integer>();
            int[] indices = getNext();
            for (int i = 0; i < indices.length; i++) {
                combination.add(elements[indices[i]]);
            }
            if (target == sum(combination)) {
                lines.add(combination);
            }
        }
        return lines;
    }

    private int sum(List<Integer> list) {
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        return sum;
    }
}