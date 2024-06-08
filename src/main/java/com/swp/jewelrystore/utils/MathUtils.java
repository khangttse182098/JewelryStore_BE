package com.swp.jewelrystore.utils;

public class MathUtils {
    // viet ham tinh n! = 1.2.3...n
    // 0! = 1 quy uoc
    // 20! vua du kieu long
    public static long getFactorial(int n) {
        if(n < 0 || n > 20)
            throw new IllegalArgumentException("n must be between 0 and 20");
        if(n == 0 || n == 1)
            return 1;
        return n * getFactorial(n - 1);
    }

}
