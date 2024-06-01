package org.testapi.stepdefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testapi.base.FancodeBase;
import org.testapi.util.TestUtils;

import java.util.List;
import java.util.Map;

public class StepDefinition extends FancodeBase {
    private Response response;

    List<Integer> fancodeUsers ;
    static String baseUrl = TestUtils.getProperty("test.api.baseurl");
    //hit the users api and get the list of users from fancode city implement this in base class
    Map<Integer, String> completedTask;
    @Given("User has the todo tasks")
    public void userHasTheTodoTasks() {
        //hit todos api and fetch json
        RestAssured.baseURI = baseUrl;
        response = getApiResponse("todos");
        completedTask =  getCompletedTaskCount(response);
    }
    @And("User belongs to the city FanCode")
    public void userBelongsToTheCityFanCode() {
        //check in users api fancode users
        RestAssured.baseURI = baseUrl;
        response = getApiResponse("users");
        //pass the response and  get list of users belonging to fancode city
        fancodeUsers = fetchFancodeUsers(response);
    }

    @Then("User Completed task percentage should be greater than {int}%")
    public void userCompletedTaskPercentageShouldBeGreaterThan(float requiredPercentage) {
        boolean flag = false;
    //apply some logic for checking percentage completed is greater than 50 for all the users
        for (Integer fancodeUser : fancodeUsers) {
            String[] taskCount = completedTask.get(fancodeUser).split("\\s+");
            float taskPercentage = (float) (Integer.parseInt(taskCount[1]) * 100/(Integer.parseInt(taskCount[0]) + Integer.parseInt(taskCount[1])));
            if(requiredPercentage>taskPercentage) {
                flag = true;
                break;
            }
        }
        Assert.assertFalse(flag);
    }


}
