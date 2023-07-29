package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataBaseHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.MainPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyWithCardCheckFieldName {
    MainPage mainPage;
    PaymentPage paymentPage;

    @BeforeEach
    void shouldOpenWeb() {
        DataBaseHelper.cleanDataBase();
        mainPage = open("http://localhost:8080", MainPage.class);
        paymentPage = mainPage.buyWithoutCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void checkingOwnerUnderLimit() { /*Покупка тура при вводе одной буквы в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getUnderLimitOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerOverLimit() { /*Покупка тура при вводе 20 букв слитно в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOverLimitOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithoutSpaces() { /*Покупка тура при вводе имени и фамилии владельца слитно*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getDataWithoutSpaces();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithNumbers() { /*Покупка тура при вводе цифр в поле владелец*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerNumbers();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerRus() { /*Покупка тура при вводе владельца на русском языке*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwnerRus();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerOnlySurname() { /*Покупка тура при вводе только фамилии в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerOnlySurname();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerLowerCase() { /*Покупка тура при вводе строчных букв в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerLowerCase();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerInvalid() { /*Покупка тура при вводе невалидного владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectEmptyOwner() { /*Покупка тура при отсутствии ввода владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptyValue();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitNecessaryFillOutField();
    }
}
