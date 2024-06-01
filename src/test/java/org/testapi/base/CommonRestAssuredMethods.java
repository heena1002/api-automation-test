package org.testapi.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
public class CommonRestAssuredMethods {
    static Response response;

    public static Response fetchResponse(String methodName, String endpoint){
        if (methodName.equals("get")) {
            response = RestAssured.get(endpoint);
        } else if (methodName.equals("post")) {
            response = RestAssured.post(endpoint);
        }
        return response;
    }



}
