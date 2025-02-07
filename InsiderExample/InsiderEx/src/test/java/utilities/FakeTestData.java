package utilities;

import com.github.javafaker.Faker;

public class FakeTestData {
    private static final Faker faker = new Faker();

    public static final String FAKE_USERNAME = faker.name().username();
    public static final String FAKE_FIRST_NAME = faker.name().firstName();
    public static final String FAKE_LAST_NAME = faker.name().lastName();
    public static final String FAKE_EMAIL = faker.internet().emailAddress();
    public static final String FAKE_PASSWORD = faker.internet().password();
    public static final String FAKE_ADDRESS = faker.address().streetAddress();
    public static final String FAKE_ADDRESS_2 = faker.address().secondaryAddress();
    public static final String FAKE_MOBILE_PHONE = faker.phoneNumber().cellPhone();
    public static final String FAKE_COMPANY_NAME = faker.company().name();
    public static final String FAKE_COUNTRY = faker.address().country();
    public static final String FAKE_STATE = faker.address().state();
    public static final String FAKE_CITY = faker.address().city();
    public static final String FAKE_ZIP_CODE = faker.address().zipCode();

}
