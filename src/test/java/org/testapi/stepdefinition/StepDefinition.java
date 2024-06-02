package org.testapi.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Assert;

import org.testapi.base.FancodeBase;
import org.testapi.util.TestUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class StepDefinition extends FancodeBase {
    private Response response;
    List<Integer> fancodeUsers;
    static final String baseUrl = TestUtils.getProperty("test.api.baseurl");
    private final String jsonFilePath = TestUtils.getProperty("json.file.path");


    @Given("User has the todo tasks")
    public void userHasTheTodoTasks() {
        //hit todos api and fetch json
        RestAssured.baseURI = baseUrl;
        response = getApiResponse("todos");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.body().asString());

        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(response.asString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @And("User belongs to the city FanCode")
    public void userBelongsToTheCityFanCode() {
        //check in users api fancode users
        RestAssured.baseURI = baseUrl;
        response = getApiResponse("users");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.body().asString());
        //pass the response and  get list of users belonging to the fancode city
        fancodeUsers = fetchFancodeUsers(response);
    }

    @Then("User Completed task percentage should be greater than {int}%")
    public void userCompletedTaskPercentageShouldBeGreaterThan(float requiredPercentage) {
        String todosResponse = null;
        try {
            todosResponse = new String(Files.readAllBytes(Paths.get(jsonFilePath)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, String> completedTask = getCompletedTaskCount(todosResponse);
        boolean flag = true;
        if (!fancodeUsers.isEmpty())
            //logic for checking percentage completed is greater than 50 for fancode users
            for (Integer fancodeUser : fancodeUsers) {
                String[] taskCount = completedTask.get(fancodeUser).split("\\s+");
                //calculate percentage of completed task
                float taskPercentage = (float) (Integer.parseInt(taskCount[1]) * 100 / (Integer.parseInt(taskCount[0]) + Integer.parseInt(taskCount[1])));
                if (requiredPercentage > taskPercentage) {
                    flag = false;
                    break;
                }
            }
        //verify that flag = true in case all fancode users completed task percentage more than 50
        Assert.assertTrue(flag);
    }


}
