package APITest;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSenderOptions;

import org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.sun.xml.xsom.impl.scd.Iterators.Map;

public class Network_apiTest {
	
	final static String url="http://api.citybik.es/v2/networks";
	  
	public Response getresponse(String url)
	{
		RestAssured.defaultParser = Parser.JSON;
		  return 
		           given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
	                when().get(url).
	                then().contentType(ContentType.JSON).extract().response();
	}
	
	
	  @Test 
	  public void getResponseBody(){
	  System.out.println("Test to get response body\n");
	  
	  given().when().get(url).then().log().body();
	  System.out.println("Test to get response body is passed\n");
	  
	  }
	  
	  @Test 
	  public void getResponseStatus(){
	  System.out.println("Test to get response status code"); 
	  int statusCode= given().
	  when().get(url).getStatusCode();
	  
	  System.out.println("The response status code is "+statusCode);
	  given().when().get(url).then().assertThat().statusCode(200);
	  System.out.println("Test to get response status code is passed\n");
	  }
	 
	 
	 @Test
	 public void getSpecificPartOfResponseBody(){
		System.out.println("Test to verify frankfurt city is in Germany and get the latitude and longitude");
		
		Response response = getresponse(url);
		String json = response.asString();
		JsonPath path = JsonPath.from(json);
//		String city = path.get("networks.[?(@.id == 'visa-frankfurt')].location.city");
		String city = path.get("networks[384].location.city");
		System.out.println("City retrieved is " + city);
		String country = path.get("networks[384].location.country");
		float longitude = path.get("networks[384].location.longitude");
		float latitude = path.get("networks[384].location.latitude");
		assertEquals("DE", country);
		System.out.println("Test to verify frankfurt city is in Germany is passed with latitude as " + latitude + " and longitude as " + longitude + "\n");
				
		}
	
}
