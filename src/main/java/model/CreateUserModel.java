package model;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;
import lombok.Getter;

@Getter
public class CreateUserModel {

    public String emailAddress, name, gender, status;
    private JSONObject jsonObject;

    public static final class UserBuilder {
        private String emailAddress, name, gender, status;

        public UserBuilder(UserPostFiled gender, UserPostFiled status) {
            Faker faker = new Faker();
            this.emailAddress = faker.internet().emailAddress();
            this.name = faker.name().fullName();
            this.gender = gender.getFieldName();
            this.status = status.getFieldName();
        }


        public CreateUserModel build() {
            CreateUserModel createUser = new CreateUserModel();
            createUser.emailAddress = emailAddress;
            createUser.name = name;
            createUser.gender = gender;
            createUser.status = status;

            JSONObject jsonBody = new JSONObject();
            jsonBody.put(UserPostFiled.EMAIL.getFieldName(), emailAddress);
            jsonBody.put(UserPostFiled.NAME.getFieldName(), name);
            jsonBody.put(UserPostFiled.GENDER.getFieldName(), gender);
            jsonBody.put(UserPostFiled.STATUS.getFieldName(), status);
            createUser.jsonObject = jsonBody;
            return createUser;
        }

    }

    public enum UserPostFiled {
        EMAIL("email"),
        NAME("name"),
        GENDER("gender"),
        STATUS("status"),
        STATUS_ACTIVE("Active"),
        STATUS_INACTIVE("Inactive"),
        GENDER_MALE("Male"),
        GENDER_FEMALE("Female");

        @Getter
        private final String fieldName;

        UserPostFiled(String userField) {
            this.fieldName = userField;
        }

    }
}
