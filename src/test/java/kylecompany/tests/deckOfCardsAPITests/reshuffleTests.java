package kylecompany.tests.deckOfCardsAPITests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class reshuffleTests {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://deckofcardsapi.com";
    }

    @Test
    public void verifyAbleToReshuffleAllCards() {
        Response response = RestAssured.get("/api/deck/new/shuffle/?deck_count=20");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to shuffle cards");
        Response secondResponse = RestAssured.get("/api/deck/" + jsonPathEvaluator.get("deck_id") + "/shuffle/");
        JsonPath secondJsonPathEvaluator = secondResponse.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(secondJsonPathEvaluator.get("success"), true, "Reshuffle cards was unsuccessful");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 1040, "Incorrect number of remaining cards");
    }

    @Test
    public void verifyAbleToReshuffleAllRemainingCards() {
        Response response = RestAssured.get("/api/deck/new/shuffle/?deck_count=20");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to shuffle cards");
        Response secondResponse = RestAssured.get("/api/deck/" + jsonPathEvaluator.get("deck_id") + "/shuffle/?remaining=true");
        JsonPath secondJsonPathEvaluator = secondResponse.jsonPath();
        Assert.assertEquals(secondJsonPathEvaluator.get("success"), true, "Reshuffle cards was unsuccessful");
        Assert.assertEquals(secondJsonPathEvaluator.get("shuffled"), true, "Unable to Reshuffle cards");
    }
}
