package com.automation.services;

import com.automation.enums.MethodTypes;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class BaseServices {


    public Response execute(String type, RequestSpecification req) {

        Response response = null;

        MethodTypes methodType = null;
        try {
            methodType = MethodTypes.valueOf(type);
        } catch (Exception e) {
            Assert.fail("Invalid Method Type " + type);
        }

        switch (methodType) {

            case GET:
                response = req.get();
                break;

            case POST:
                response = req.post();
                break;

            case PATCH:
                response = req.patch();
                break;

            case PUT:
                response = req.put();
                break;

            case DELETE:
                response = req.delete();
                break;

            case OPTIONS:
                response = req.options();
                break;

            default:
                Assert.fail("Invalid method type selected ");
        }

        return response;

    }

}
