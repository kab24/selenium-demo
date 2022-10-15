package kylecompany.tests.deckOfCardsAPITests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class drawingCardsUsingANewDeckTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://deckofcardsapi.com";
    }

    @Test
    public void verifyAbleToDrawOneCard(){
        Response response = RestAssured.get("/api/deck/new/draw/?count=1");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to draw a card");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 51, "Wrong amount of cards remaining");
    }

    @Test
    public void verifyAbleToDrawAllsCardInADeck(){
        Response response = RestAssured.get("/api/deck/new/draw/?count=52");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), true, "Unable to draw all cards");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 0, "Wrong amount of cards remaining");
    }

    @Test
    public void verifyUnableToDrawMoreMaxAmountOfCardsInADeck(){
        Response response = RestAssured.get("/api/deck/new/draw/?count=53");
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPathEvaluator.get("success"), false, "Able to draw more than the max amount of cards in a deck");
        Assert.assertEquals(jsonPathEvaluator.get("remaining"), 0, "Wrong amount of cards remaining");
    }
}
