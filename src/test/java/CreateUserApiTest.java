import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import model.CreateUserModel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class CreateUserApiTest implements Resources {

    private static String API_ACCESS_TOKEN;

    @BeforeTest
    public void testSetup() throws IOException {
        Properties properties = new ReadProperties().getProperties();
        API_ACCESS_TOKEN = properties.getProperty("api.access.token");
        baseURI = URL;
    }

    @DataProvider
    public Object[][] userDataSupplier() {
        return new Object[][]{
                {new CreateUserModel.UserBuilder(CreateUserModel.UserPostFiled.GENDER_MALE, CreateUserModel.UserPostFiled.STATUS_ACTIVE).build()},
                {new CreateUserModel.UserBuilder(CreateUserModel.UserPostFiled.GENDER_FEMALE, CreateUserModel.UserPostFiled.STATUS_INACTIVE).build()},
                {new CreateUserModel.UserBuilder(CreateUserModel.UserPostFiled.GENDER_MALE, CreateUserModel.UserPostFiled.STATUS_ACTIVE).build()},
                {new CreateUserModel.UserBuilder(CreateUserModel.UserPostFiled.GENDER_FEMALE, CreateUserModel.UserPostFiled.STATUS_ACTIVE).build()}
        };
    }

    @Test(dataProvider = "userDataSupplier")
    public void createUserTest(CreateUserModel createUserJson) {

        given()
                .contentType(ContentType.JSON)
                .body(createUserJson.getJsonObject())
                .header("Authorization", API_ACCESS_TOKEN)
        .when()
                .post(USERS_ROUTE)
        .then()
                .body("code", equalTo(201))
                .body("data.name", equalTo(createUserJson.getName()))
                .body("data.email", equalTo(createUserJson.getEmailAddress()))
                .body("data.gender", equalTo(createUserJson.getGender()))
                .body("data.status", equalTo(createUserJson.getStatus()))
                .log().all();
    }
}
