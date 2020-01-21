package com.neves_eduardo.cloud.tema8.operations;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DivOperation implements Operation {
    private double valueA;
    private double valueB;

    public DivOperation(double valueA, double valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    @Override
    public double calculate() {
        if(valueB==0) {throw new IllegalArgumentException("You can't divide by zero!");}
        return this.valueA / this.valueB;
    }


    @Override
    public String toString() {
        return "  " + valueA + "/" + valueB + "=" + calculate() + " ;";
    }
}
