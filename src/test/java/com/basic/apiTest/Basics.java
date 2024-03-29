package com.basic.apiTest;
import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Validate if Add Place API is working as expected
		
		   // Add place-> Update place with new address -> Get Place to validate if new address is present in response
		
		// given - all input details
		//when - submit the api - resource, http method
		//then - validate the response
		
		//Add Place
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().log().all().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").
		extract().response().asString();
		
		System.out.println("The response is:: "+response);
		
		JsonPath jp = new JsonPath(response);
		String placeID = jp.getString("place_id");
		System.out.println(placeID);
		
		//update Place
		String newAddress = "70 Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "  \"place_id\": \""+placeID+"\",\r\n"
				+ "  \"address\": \""+newAddress+"\",\r\n"
				+ "  \"key\": \"qaclick123\"\r\n"
				+ "}")
		
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get place
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = jp1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
	}

	
}
