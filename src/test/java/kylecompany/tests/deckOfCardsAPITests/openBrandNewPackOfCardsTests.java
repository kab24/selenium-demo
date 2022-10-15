package kylecompany.tests.deckOfCardsAPITests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class openBrandNewPackOfCardsTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://deckofcardsapi.com";
    }

    @Test
    public void verifyAbleToOpenABrandNewDeckOfCards(){
        Response response = RestAssured.get("/api/deck/new/");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to shuffle cards");
        Assert.assertEquals(jsonPathEvaluator.get("shuffled"), false, "Cards could not be shuffled");
    }

    @Test
    public void verifyAbleToOpenABrandNewDeckOfCardsWithJokers(){
        Response response = RestAssured.get("/api/deck/new/?jokers_enabled=true");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to shuffle cards");
        Assert.assertEquals(jsonPathEvaluator.get("shuffled"), false, "Cards could not be shuffled");
    }
}
