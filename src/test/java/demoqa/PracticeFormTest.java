package demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static utils.Randomizer.*;

public class PracticeFormTest {
    private final Faker faker = new Faker();
    private final SelenideElement tableResponsive = $(".table-responsive");

    private static final String BASE_URL = "https://demoqa.com/automation-practice-form";

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browserSize = "1540x1080";
        Configuration.browser = System.getProperty("browser", "chrome");
        setEnvironmentAllure("task", System.getProperty("task", "test"));
        setEnvironmentAllure("browser", Configuration.browser);

        if (System.getProperty("remote_driver") != null) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);
            Configuration.browserCapabilities = capabilities;
            Configuration.remote = System.getProperty("remote_driver");
            setEnvironmentAllure("selenoid", System.getProperty("selenoid"));
        }
    }

    @AfterEach
    void afterEach() {
        attachVideo();
        attachPageSource();
        attachScreenshot("Last screenshot");
        closeWebDriver();
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(CRITICAL)
    @Link(name = "Base URL", value = BASE_URL)
    @Tag("positive")
    @Feature("Registration form")
    @Story("Успешная регистрация")
    @DisplayName("Успешная регистрация рандомного студента.")
    void checkStudentRegistrationForm() {
        Calendar date = randomDate(-2208985139000L);
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = faker.internet().emailAddress();
        String gender = randomValueFromVariant("Male", "Female", "Other");
        String userNumber = faker.phoneNumber().subscriberNumber(10);
        String dayOfBirth = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        String monthOfBirth = String.valueOf(new DateFormatSymbols(Locale.ENGLISH).getMonths()[date.get(Calendar.MONTH)]);
        String yearOfBirth = String.valueOf(date.get(Calendar.YEAR));
        List<String> subjects = randomValuesFromVariant("Physics", "History", "Economics");
        List<String> hobbies = randomValuesFromVariant("Sports", "Reading", "Music");
        String picture = "photo.jpg";
        String currentAddress = faker.address().streetAddress();
        String state = "Rajasthan";
        String city = randomValueFromVariant("Jaipur", "Jaiselmer");

        //act
        step("Открываем страницу регистрации", () -> {
            open(BASE_URL);
        });

        step("Заполнение имени " + firstName, () -> {
            $("#firstName").val(firstName);
        });

        step("Заполнение фамилии " + lastName, () -> {
            $("#lastName").setValue(lastName);
        });

        step("Заполнение почты " + userEmail, () -> {
            $("#userEmail").setValue(userEmail);
        });

        step("Выбор пола '" + gender + "'", () -> {
            $(byText(gender)).click();
        });

        step("Заполнение телефона " + userNumber, () -> {
            $("#userNumber").setValue(userNumber);
        });

        step("Заполнение даты рождения " + dayOfBirth + " " + monthOfBirth + " " + yearOfBirth, () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").selectOption(yearOfBirth);
            $(".react-datepicker__month-select").selectOption(monthOfBirth);
            $$(".react-datepicker__day--0" + String.format("%02d", date.get(Calendar.DAY_OF_MONTH)))
                    .filter(not(cssClass(".react-datepicker__day--outside-month"))).find(text(dayOfBirth)).click();
        });

        step("Выбор предметов " + subjects, () -> {
            setSubjects(subjects);
        });

        step("Выбор хобби " + hobbies, () -> {
            setHobbies(hobbies);
        });

        step("Загрузка изображения '" + picture + "'", () -> {
            $("#uploadPicture").uploadFromClasspath(picture);
        });

        step("Заполнение адреса", () -> {
            $("#currentAddress").val(currentAddress);
        });

        step("Выбор штата " + state, () -> {
            $("#state").click();
            $(byText(state)).click();
        });

        step("Выбор города " + city, () -> {
            $("#city").click();
            $(byText(city)).click();
        });

        step("Отправка формы", () -> {
            $("#submit").click();
        });

        step("Проверка заполненной формы", () -> {
            tableResponsive.shouldHave(text("Student Name " + firstName + " " + lastName));
            tableResponsive.shouldHave(text("Student Email " + userEmail));
            tableResponsive.shouldHave(text("Gender " + gender));
            tableResponsive.shouldHave(text("Mobile " + userNumber));
            tableResponsive.shouldHave(text("Date of Birth " + String.format("%02d", date.get(Calendar.DAY_OF_MONTH)) + " " + monthOfBirth + "," + yearOfBirth));
            tableResponsive.shouldHave(text("Picture " + picture));
            tableResponsive.shouldHave(text("Address " + currentAddress));
            tableResponsive.shouldHave(text("State and City " + state + " " + city));
            checkByList(subjects);
            checkByList(hobbies);
        });
    }

    @Test
    @Owner("GorbatenkoVA")
    @Link(name = "Base URL", value = BASE_URL)
    @Tag("negative")
    @Feature("Registration form")
    @Story("Неуспешная регистрация")
    @DisplayName("Негативный тест.")
    void checkNegativeTest() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        step("Открываем страницу регистрации", () -> {
            open(BASE_URL);
        });

        step("Заполнение имени " + firstName, () -> {
            $("#firstName").val(firstName);
        });

        step("Заполнение фамилии " + lastName, () -> {
            $("#lastName").setValue(lastName);
        });

        step("Отправка формы", () -> {
            $("#submit").click();
        });

        step("Проверка заполненной формы", () -> {
            tableResponsive.shouldHave(
                    text("Student Name " + firstName + " " + lastName));
        });
    }

    private void setSubjects(List<String> subjects) {
        for (String subject : subjects) {
            $("#subjectsContainer input").val(subject).pressEnter();
        }
    }

    private void setHobbies(List<String> hobbies) {
        for (String hobby : hobbies) {
            $(byText(hobby)).click();
        }
    }

    private void checkByList(List<String> subjects) {
        for (String subject : subjects) {
            tableResponsive.shouldHave(text(subject));
        }
    }

}
