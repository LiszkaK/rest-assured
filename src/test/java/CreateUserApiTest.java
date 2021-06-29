import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

import com.github.javafaker.Faker;

public class CreateUserApiTest implements Resources {

    private static String API_ACCESS_TOKEN;
    private JSONObject jsonBody;
    private Faker faker;

    @BeforeTest
    public void testSetup() throws IOException {
        Properties properties = new ReadProperties().getProperties();
        API_ACCESS_TOKEN = properties.getProperty("api.access.token");
        baseURI = URL;
        faker = new Faker();
    }

    @DataProvider
    public Object[][] userDataSupplier() {
        return new Object[][]{
                {faker.internet().emailAddress(), faker.name().fullName(), "Male", "Active"},
                {faker.internet().emailAddress(), faker.name().fullName(), "Female", "Active"},
                {faker.internet().emailAddress(), faker.name().fullName(), "Male", "Inactive"},
        };
    }

    @Test(dataProvider = "userDataSupplier")
    public void createUserTest(String email, String name, String gender, String status) {
        jsonBody = new JSONObject();
        jsonBody.put("email", email);
        jsonBody.put("name", name);
        jsonBody.put("gender", gender);
        jsonBody.put("status", status);

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .header("Authorization", API_ACCESS_TOKEN)
        .when()
                .request("POST", USERS_ROUTE)
        .then()
                .body("code", equalTo(201))
                .body("data.name", equalTo(name))
                .body("data.email", equalTo(email))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status))
                .log().all();
    }
}
