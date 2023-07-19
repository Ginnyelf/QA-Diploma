package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataBaseHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.MainPage;
import page.PaymentPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyWithCard {
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
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
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
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
        var expected = DataHelper.getSecondCardExpectedStatus();
        var actual = DataBaseHelper.getStatusPaymentWithoutCredit();
        assertEquals(expected, actual);
    }

    @Test
    void сheckingIncompleteData() { /*Покупка тура при вводе неполных данных*/
        var cardNumber = DataHelper.getCardNumberIncomplete();
        var month = DataHelper.getIncompleteMonth();
        var year = DataHelper.getIncompleteYear();
        var owner = DataHelper.getIncompleteOwner();
        var cvс = DataHelper.getIncompleteCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void сheckingСardNumberRequestedData() { /*Покупка тура при вводе одинаковых цифр номера карты*/
        var cardNumber = DataHelper.getCardNumberRequestedData();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
    }

    @Test
    void сheckingСardNumberZero() { /*Покупка тура при вводе нулей в поле номер карты */
        var cardNumber = DataHelper.getCardNumberZero();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingСardNumberUnderLimit() { /*Покупка тура при вводе 4 цифр в поле номер карты */
        var cardNumber = DataHelper.getCardNumberUnderLimit();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingСardNumberOverLimit() { /*Покупка тура при вводе 17 цифр в поле номер карты */
        var cardNumber = DataHelper.getCardNumberOverLimit();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectApprovalFromBank();
    }

    @Test
    void сheckingСardWithText() { /*Покупка тура при вводе текста в поле номер карты */
        var cardNumber = DataHelper.getValueText();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectInvalidMonth() { /*Покупка тура при вводе невалидного месяца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getInvalidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void checkingMonthWithText() { /*Покупка тура при вводе букв в поле месяца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValueText();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingMonthOverLimit() { /*Покупка тура при вводе трех цифр в поле месяца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getDurationOverLimit();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectZeroMonth() { /*Покупка тура при вводе нулевого месяца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getZeroValue();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }


    @Test
    void shouldRejectPastMonth() {  /*Покупка тура по карте с истекшим сроком годности*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getPastMonth();
        var year = DataHelper.getThisYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldRejectInvalidYear() { /*Покупка тура при вводе невалидного года*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldRejectZeroYear() { /*Покупка тура при вводе нулевого года*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getZeroValue();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingYearWithText() { /*Покупка тура при вводе букв в поле года*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValueText();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingYearOverLimit() { /*Покупка тура при вводе трех цифр в поле года*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getDurationOverLimit();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerUnderLimit() { /*Покупка тура при вводе одной буквы в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getUnderLimitOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerOverLimit() { /*Покупка тура при вводе 20 букв слитно в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOverLimitOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithoutSpaces() { /*Покупка тура при вводе имени и фамилии владельца слитно*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getDataWithoutSpaces();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithNumbers() { /*Покупка тура при вводе цифр в поле владелец*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerNumbers();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerRus() { /*Покупка тура при вводе владельца на русском языке*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwnerRus();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerOnlySurname() { /*Покупка тура при вводе только фамилии в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerOnlySurname();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerLowerCase() { /*Покупка тура при вводе строчных букв в поле владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getOwnerLowerCase();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerInvalid() { /*Покупка тура при вводе невалидного владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getInvalidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectInvalidCvс() { /*Покупка тура при вводе невалидного cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getInvalidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectZeroCvc() { /*Покупка тура при вводе нулевого cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getZeroCvv();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVOverLimit() { /*Покупка тура при вводе cvc сверх лимита*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getCvcOverLimit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVWithText() { /*Покупка тура при вводе текста в поле cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValueText();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectEmptyNumberCard() { /*Покупка тура при отсутствии ввода номера карты*/
        var cardNumber = DataHelper.getEmptyValue();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyMonth() { /*Покупка тура при отсутствии ввода месяца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getEmptyValue();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyYear() { /*Покупка тура при отсутствии ввода года*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptyValue();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyCvс() { /*Покупка тура при отсутствии ввода cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvс = DataHelper.getEmptyValue();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyOwner() { /*Покупка тура при отсутствии ввода владельца*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptyValue();
        var cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }
}
