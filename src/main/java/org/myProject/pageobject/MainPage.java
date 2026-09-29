package org.myProject.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    private By topOrderButton = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");
    private By bottomOrderButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");
    private By cookieButton = By.className("App_CookieButton__3cvqF");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookies() {
        if (!driver.findElements(cookieButton).isEmpty()) {
            WebElement button = driver.findElement(cookieButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    public void clickBottomOrderButton() {
        WebElement button = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }

    public void clickQuestion(int questionNumber) {
        By question = By.id("accordion__heading-" + questionNumber);
        WebElement questionElement = driver.findElement(question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", questionElement);
        questionElement.click();
    }

    public String getAnswerText(int questionNumber) {
        By answer = By.id("accordion__panel-" + questionNumber);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(answer));
        return driver.findElement(answer).getText();
    }
}