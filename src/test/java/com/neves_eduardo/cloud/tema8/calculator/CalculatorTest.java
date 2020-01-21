package com.neves_eduardo.cloud.tema8.calculator;

import com.neves_eduardo.cloud.tema8.config.AppConfig;
import com.neves_eduardo.cloud.tema8.operations.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CalculatorTest {
    @Autowired
    private Calculator calculator;
    private final double DELTA = 0.001;
    @Test
    public void sum() {
        double result = calculator.calculate(SupportedOperations.SUM,256,256);
        assertEquals(result,512,DELTA);
    }

    @Test
    public void min() {
        double result = calculator.calculate(SupportedOperations.MIN,256,256);
        assertEquals(result,0, DELTA);
    }
    @Test
    public void multi() {
        double result = calculator.calculate(SupportedOperations.MULTI,256,256);
        assertEquals(result,65536,DELTA);
    }
    @Test
    public void div() {
        double result = calculator.calculate(SupportedOperations.DIV,256,256);
        assertEquals(result,1,DELTA);
    }
    @Test
    public void pow() {
        double result = calculator.calculate(SupportedOperations.POW,256,2);
        assertEquals(result,65536,DELTA);
    }
    @Test
    public void historyShouldStoreTheOperations(){
        calculator.getHistory().clear();
        calculator.calculate(SupportedOperations.SUM,256,256);
        calculator.calculate(SupportedOperations.SUM,100,100);
        calculator.calculate(SupportedOperations.MIN,256,256);
        calculator.calculate(SupportedOperations.MIN,100,50);
        calculator.calculate(SupportedOperations.MULTI,256,256);
        calculator.calculate(SupportedOperations.MULTI,10,10);
        calculator.calculate(SupportedOperations.DIV,256,256);
        calculator.calculate(SupportedOperations.DIV,10,2);
        calculator.calculate(SupportedOperations.POW,256,2);
        calculator.calculate(SupportedOperations.POW,20,2);

        assertEquals(calculator.getHistory().get(0).calculate(),512,DELTA);
        assertEquals(calculator.getHistory().get(1).calculate(),200,DELTA);
        assertEquals(calculator.getHistory().get(2).calculate(),0,DELTA);
        assertEquals(calculator.getHistory().get(3).calculate(),50,DELTA);
        assertEquals(calculator.getHistory().get(4).calculate(),65536,DELTA);
        assertEquals(calculator.getHistory().get(5).calculate(),100,DELTA);
        assertEquals(calculator.getHistory().get(6).calculate(),1,DELTA);
        assertEquals(calculator.getHistory().get(7).calculate(),5,DELTA);
        assertEquals(calculator.getHistory().get(8).calculate(),65536,DELTA);
        assertEquals(calculator.getHistory().get(9).calculate(),400,DELTA);


        assertEquals(calculator.getHistory().get(0).getClass(),SumOperation.class);
        assertEquals(calculator.getHistory().get(1).getClass(), SumOperation.class);
        assertEquals(calculator.getHistory().get(2).getClass(), MinOperation.class);
        assertEquals(calculator.getHistory().get(3).getClass(), MinOperation.class);
        assertEquals(calculator.getHistory().get(4).getClass(), MultiOperation.class);
        assertEquals(calculator.getHistory().get(5).getClass(),MultiOperation.class);
        assertEquals(calculator.getHistory().get(6).getClass(), DivOperation.class);
        assertEquals(calculator.getHistory().get(7).getClass(), DivOperation.class);
        assertEquals(calculator.getHistory().get(8).getClass(),PowOperation.class);
        assertEquals(calculator.getHistory().get(9).getClass(), PowOperation.class);


    }
}