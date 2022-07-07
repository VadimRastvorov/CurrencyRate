package ru.liga.linearregression;

import org.junit.jupiter.api.Test;

public class LinearregRessionTest {
    @Test
    public void testLinearRegression(){
        double[] x = new double[]{1,2,3,4,5};
        double[] y = new double[]{3,4,5,4,3};
        LinearRegression lineReg = new LinearRegression(x,y);
        double doub = lineReg.intercept();
        double doub2 = lineReg.R2();
        double doub3 = lineReg.slope();
        double doub4 = lineReg.interceptStdErr();
        double doub5 = lineReg.slopeStdErr();
    }
}
