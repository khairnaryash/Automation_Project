package com.automation.services;

import com.automation.reporter.ExtentReporter;
import com.automation.reporter.customAssert.CustomAssert;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;

public class GitHubServices extends BaseServices {

    private String baseUrl;

    public GitHubServices(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<String> getUserRepo(HashMap<String, String> testData) {
        String user = testData.get("UserName");
        String endpoint = testData.get("Endpoint").replace("{UserName}", user);

        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.baseUri(baseUrl + endpoint);
        ExtentReporter.getTest().log(Status.INFO, "Executing request : " + baseUrl + endpoint);

        Response resp = execute(testData.get("Method"), httpRequest);

        resp.then().statusCode(200);

        ExtentReporter.getTest().log(Status.PASS, "Status code is 200");
        resp.then().body("[0].owner.login", equalTo(user));
        CustomAssert.assertEquals(resp.getBody().jsonPath().get("[0].owner.login"), user, "User name");
        JsonPath json = resp.getBody().jsonPath();

        List<Object> list = json.getList("");
        ArrayList<String> repoList = new ArrayList<>();
        for (Object o : list) {
            String name = ((LinkedHashMap) o).get("name").toString();
            repoList.add(name);
        }

        return repoList;
    }
}
