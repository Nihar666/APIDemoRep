package com.basic.apiTest;


import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	
		@Test
		public void sumOfpurchaseamount() {
			
			JsonPath js = new JsonPath(payload.CoursePrice());
			
			int sum=0;
			
			int count = js.getInt("courses.size()");
			System.out.println(count);
			
			for(int i=0;i<count;i++) {
			int price =	js.getInt("courses["+i+"].price");
			
			int copies = js.getInt("courses["+i+"].copies");
			
				int amount = price * copies;
				System.out.println("total amount == "+amount);
				
				sum=sum+amount;
				
				System.out.println(sum);
			}
			
			int purchaseAmount = js.getInt("dashboard.purchaseAmount");
			Assert.assertEquals(sum, purchaseAmount);
			
		}
		
		
	}
	
	
		


