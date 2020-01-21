package com.neves_eduardo.cloud.tema8.operations;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MinOperation implements Operation {
    private double valueA;
    private double valueB;

    public MinOperation(double valueA, double valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    @Override
    public double calculate() {
        return this.valueA-this.valueB;
    }
    @Override
    public String toString() {
        return "  " + valueA + "-" + valueB + "=" + calculate() + " ;";
    }
}
