package com.basic.apiTest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {
	
	@Test(dataProvider="AddbookData")
	public void addBoodAPI(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String responsss= given().log().all().header("Content-Type", "application/json").
		body(payload.addBook(isbn, aisle)).
		when().post("/Library/Addbook.php").
		then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("response is "+responsss);
		
		JsonPath js = ReUsableMethods.rawToJson(responsss);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="AddbookData")
	public Object[][] getData() {
		
		//array= Collections of similar element
		//multidimensional array = collection of arrays
		
		return new Object[][] {{"ndjemk","836478"},{"mamxk","22389"},{"mjuju","66899"}};
	}
	

}
