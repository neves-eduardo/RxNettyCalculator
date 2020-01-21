package com.neves_eduardo.cloud.tema8.calculator;

import com.neves_eduardo.cloud.tema8.calculator.SupportedOperations;
import com.neves_eduardo.cloud.tema8.operations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Stack;

@Component("Calculator")
public class Calculator {
    private ApplicationContext applicationContext;
    private Stack<Operation> history = new Stack<>();

    @Autowired
    public Calculator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public double calculate(SupportedOperations selectedOperation, double valueA, double valueB) {
        Operation operation = (Operation) applicationContext.getBean(selectedOperation
                .toString()
                .toLowerCase()
                .concat("Operation"), valueA, valueB);
        history.push(operation);
        return  operation.calculate();
    }

    public Stack<Operation> getHistory() {
        return history;
    }


}