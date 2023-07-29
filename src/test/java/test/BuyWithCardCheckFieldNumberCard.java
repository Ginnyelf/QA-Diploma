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

public class BuyWithCardCheckFieldNumberCard {
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
    void shouldApproveFirstCard() {  /* Покупка тура при вводе валидных данных карты*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.expectApprovalFromBank();
        var expected = DataHelper.getFirstCardExpectedStatus();
        var actual = DataBaseHelper.getStatusPaymentWithoutCredit();
        assertEquals(expected, actual);

    }

    @Test
    void shouldRejectSecondCard() { /*Покупка тура при вводе невалидных данных карты*/
        var cardNumber = DataHelper.getSecondCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.expectRejectionFromBank();
        var expected = DataHelper.getSecondCardExpectedStatus();
        var actual = DataBaseHelper.getStatusPaymentWithoutCredit();
        assertEquals(expected, actual);
    }

    @Test
    void checkingIncompleteData() { /*Покупка тура при вводе неполных данных*/
        var cardNumber = DataHelper.getCardNumberIncomplete();
        var month = DataHelper.getIncompleteMonth();
        var year = DataHelper.getIncompleteYear();
        var owner = DataHelper.getIncompleteOwner();
        var cvc = DataHelper.getIncompleteCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void checkingCardNumberRequestedData() { /*Покупка тура при вводе одинаковых цифр номера карты*/
        var cardNumber = DataHelper.getCardNumberNotExisting();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.expectRejectionFromBank();
    }

    @Test
    void checkingCardNumberZero() { /*Покупка тура при вводе нулей в поле номер карты */
        var cardNumber = DataHelper.getCardNumberZero();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCardNumberUnderLimit() { /*Покупка тура при вводе 4 цифр в поле номер карты */
        var cardNumber = DataHelper.getCardNumberUnderLimit();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCardNumberOverLimit() { /*Покупка тура при вводе 17 цифр в поле номер карты */
        var cardNumber = DataHelper.getCardNumberOverLimit();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.expectApprovalFromBank();
    }

    @Test
    void checkingCardWithText() { /*Покупка тура при вводе текста в поле номер карты */
        var cardNumber = DataHelper.getValueText();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectEmptyNumberCard() { /*Покупка тура при отсутствии ввода номера карты*/
        var cardNumber = DataHelper.getEmptyValue();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValidCvc();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvc);
        paymentPage.waitNecessaryFillOutField();
    }

}
