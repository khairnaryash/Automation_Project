package com.automation.tests;

import com.automation.utils.BaseAPITest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SampleAPITest //extends BaseAPITest
{

    @Test
    public void test() {

        List<String> list = getUserRepo("django");
        System.out.println(list);
    }

    public List<String> getUserRepo(String user) {

        RestAssured.baseURI = "https://api.github.com/users/{UserName}/repos".replace("{UserName}", user);
        Response resp = RestAssured.given().get();

        resp.then().statusCode(200);
        resp.then().body("[0].owner.login", equalTo(user));

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
