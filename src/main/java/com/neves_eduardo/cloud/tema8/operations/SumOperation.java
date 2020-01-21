package com.neves_eduardo.cloud.tema8.operations;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SumOperation implements Operation {
    private double valueA;
    private double valueB;

    public SumOperation(double valueA, double valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    @Override
    public double calculate() {
        return valueA+valueB;
    }
    @Override
    public String toString() {
        return "  " + valueA + "+" + valueB + "=" + calculate() + " ;";
    }

}
