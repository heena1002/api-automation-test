package org.testapi.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CommonRestAssuredMethods {
    static Response response;

    public static Response fetchResponse(String methodName, String endpoint){
        RequestSpecification httpRequest = RestAssured.given();
        if (methodName.equals("get")) {
            response = httpRequest.get(endpoint);
        } else if (methodName.equals("post")) {
            response = httpRequest.post(endpoint);
        }
        return response;
    }



}
