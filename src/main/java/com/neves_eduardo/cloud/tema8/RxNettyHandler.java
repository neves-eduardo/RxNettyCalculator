package com.neves_eduardo.cloud.tema8;

import com.neves_eduardo.cloud.tema8.calculator.Calculator;
import com.neves_eduardo.cloud.tema8.calculator.SupportedOperations;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import netflix.karyon.transport.http.health.HealthCheckEndpoint;
import org.apache.commons.lang3.EnumUtils;
import rx.Observable;

public class RxNettyHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final String healthCheckUri;
    private final HealthCheckEndpoint healthCheckEndpoint;
    private final String calculatorURL;
    private Calculator calculator;
    private final int INDEX_OF_VALUE_A = 0;
    private final int INDEX_OF_VALUE_B = 2;
    private final int INDEX_OF_OPERATOR= 1;
    private final int NUMBER_OF_FIELDS=3;

    public RxNettyHandler(Calculator calculator, String calculatorURL, String healthCheckUri, HealthCheckEndpoint healthCheckEndpoint) {
        this.calculator = calculator;
        this.healthCheckUri = healthCheckUri;
        this.healthCheckEndpoint = healthCheckEndpoint;
        this.calculatorURL = calculatorURL;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {

        if(request.getUri().equals(calculatorURL)) {return response.writeStringAndFlush("{\"Message\":\"" + "Welcome to my calculator" + "\"}");}

        if(request.getUri().startsWith(calculatorURL.concat("/history"))) {
            return response.writeStringAndFlush("{\"History\":\"" + calculator.getHistory() + "\"}");
        }

        if(request.getUri().startsWith(calculatorURL)) {
            int prefixLength = calculatorURL.concat("/").length();

            String values[] = request.getPath().substring(prefixLength).split("/");

            if(values.length<NUMBER_OF_FIELDS) {return response.writeStringAndFlush("{\"Message\":\"" + "Missing Operation fields!" + "\"}");}
            if(!isValidOperation(values[INDEX_OF_OPERATOR])) {return response.writeStringAndFlush("{\"Message\":\"" + "Invalid Operation!" + "\"}");}
            if(!isValidNumber(values[INDEX_OF_VALUE_A]) || !isValidNumber(values[INDEX_OF_VALUE_B])){return response.writeStringAndFlush("{\"Message\":\"" + "Invalid Number!" + "\"}");}
            Double valueA = Double.parseDouble(values[INDEX_OF_VALUE_A]);
            Double valueB = Double.parseDouble(values[INDEX_OF_VALUE_B]);
            SupportedOperations operation = SupportedOperations.valueOf(values[INDEX_OF_OPERATOR].toUpperCase());

            return response.writeStringAndFlush("{\"Result\":\"" + calculator.calculate(operation,valueA,valueB) + "\"}");
        }



        if (request.getUri().startsWith(healthCheckUri)) {
            return healthCheckEndpoint.handle(request, response);
        } else {
            response.setStatus(HttpResponseStatus.NOT_FOUND);
            return response.close();
        }
    }


    public boolean isValidNumber(String number) {
        return number.matches("-?\\d+(\\.\\d+)?");
    }

    public boolean isValidOperation(String operation) {
        return EnumUtils.isValidEnum(SupportedOperations.class, operation.toUpperCase());
    }
}