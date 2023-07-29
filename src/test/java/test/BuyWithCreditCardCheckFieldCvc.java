package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataBaseHelper;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.CreditPage;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyWithCreditCardCheckFieldCvc {
    MainPage mainPage;
    CreditPage creditPage;

    @BeforeEach
    void shouldCleanDataBaseAndOpenWeb() {
        DataBaseHelper.cleanDataBase();
        mainPage = open("http://localhost:8080", MainPage.class);
        creditPage = mainPage.buyWithCredit();
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
    void shouldRejectInvalidCvc() { /*Покупка тура при вводе невалидного cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getInvalidCvc();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvc);
        creditPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectZeroCvc() { /*Покупка тура при вводе нулевого cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getZeroCvv();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvc);
        creditPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVOverLimit() { /*Покупка тура при вводе cvc сверх лимита*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getCvcOverLimit();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvc);
        creditPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVWithText() { /*Покупка тура при вводе текста в поле cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getValueText();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvc);
        creditPage.waitInvalidFormat();
    }


    @Test
    void shouldRejectEmptyCvc() { /*Покупка тура при отсутствии ввода cvc*/
        var cardNumber = DataHelper.getFirstCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getValidOwner();
        var cvc = DataHelper.getEmptyValue();
        creditPage.fillOutFields(cardNumber, month, year, owner, cvc);
        creditPage.waitNecessaryFillOutField();
    }


}
