import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class CreateUserApiTest  implements Resources {
    
    private static String API_ACCESS_TOKEN;
    private JSONObject jsonBody;

    @BeforeTest
    public void testSetup() throws IOException {
        Properties properties = new ReadProperties().getProperties();
        API_ACCESS_TOKEN = properties.getProperty("api.access.token");
        baseURI = URL;
    }

    @Test
    public void createUserTest(){
        jsonBody = new JSONObject();
        jsonBody.put("email", "karol@gmail.com");
        jsonBody.put("name", "karol");
        jsonBody.put("gender", "Male");
        jsonBody.put("status", "Active");

        given().contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", API_ACCESS_TOKEN)
                .when().request("POST", USERS_ROUTE)
                .getBody();
    }
}
