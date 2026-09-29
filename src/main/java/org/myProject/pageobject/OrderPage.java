package org.myProject.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {

    private WebDriver driver;

    private By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath(".//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath(".//button[text()='Далее']");
    private By deliveryDateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-control");
    private By blackColour = By.id("black");
    private By greyColour = By.id("grey");
    private By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");
    private By confirmOrderButton = By.xpath(".//div[contains(@class, 'Order_Modal')]//button[text()='Да']");
    private By successfulOrderModal = By.xpath(".//div[contains(@class, 'Order_ModalHeader') and contains(., 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void setMetro(String metro) {
        WebElement metroInput = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(metroField));
        metroInput.click();
        metroInput.sendKeys(metro);
        By metroOption = By.xpath(".//div[contains(@class, 'select-search__select')]//div[text()='" + metro + "']");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(metroOption)).click();
    }

    public void setPhone(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    public void fillCustomerData(String firstName, String lastName, String address, String metro, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setMetro(metro);
        setPhone(phone);
        clickNextButton();
    }

    public void setDeliveryDate(String deliveryDate) {
        WebElement dateField = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deliveryDateField));
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.ENTER);
    }

    public void setRentalPeriod(String rentalPeriod) {
        WebElement rentalField = new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        rentalField.click();
        By rentalPeriodOption = By.xpath(".//div[contains(@class, 'Dropdown-option') and text()='" + rentalPeriod + "']");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(rentalPeriodOption)).click();
    }

    public void setColour(String colour) {
        if ("black".equals(colour)) {
            driver.findElement(blackColour).click();
        }

        if ("grey".equals(colour)) {
            driver.findElement(greyColour).click();
        }
    }

    public void setComment(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void fillRentalData(String deliveryDate, String rentalPeriod, String colour, String comment) {
        setDeliveryDate(deliveryDate);
        setRentalPeriod(rentalPeriod);
        setColour(colour);
        setComment(comment);
    }

    public void clickOrderButton() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void confirmOrder() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public boolean isOrderCreated() {
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(successfulOrderModal));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}