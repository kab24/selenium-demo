package kylecompany.tests.deckOfCardsAPITests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class shuffleDeckTests {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://deckofcardsapi.com";
    }

    @Test
    public void verifyAbleToShuffleNewDeckWithMaxDeckCount() {
        Response response = RestAssured.get("/api/deck/new/shuffle/?deck_count=20");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Decks was not accessed");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 1040, "Incorrect number of remaining cards");
        Assert.assertEquals(jsonPathEvaluator.get("shuffled"), true, "Deck was not able to be shuffled");
    }

    @Test
    public void verifyAbleToShuffleNewDeckWithMinDeckCount() {
        Response response = RestAssured.get("/api/deck/new/shuffle/?deck_count=1");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Deck was not accessed");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 52, "Incorrect number of remaining cards");
        Assert.assertEquals(jsonPathEvaluator.get("shuffled"), true, "Deck was not able to be shuffled");
    }

    @Test
    public void verifyNotAbleToShuffleDecksOverTheMaxLimit() {
        Response response = RestAssured.get("/api/deck/new/shuffle/?deck_count=21");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), false, "Decks were not accessed");
        Assert.assertEquals(jsonPathEvaluator.get("error"), "The max number of Decks is 20.", "Incorrect error message received");
    }
}
