import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.myProject.pageobject.MainPage;
import org.myProject.pageobject.OrderPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {

    private WebDriver driver;

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final int daysUntilDelivery;
    private final String rentalPeriod;
    private final String colour;
    private final String comment;
    private final boolean useTopOrderButton;

    public OrderTest(String name, String surname, String address, String metro, String phone, int daysUntilDelivery, String rentalPeriod, String colour, String comment, boolean useTopOrderButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.daysUntilDelivery = daysUntilDelivery;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
        this.useTopOrderButton = useTopOrderButton;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва, улица Ленина, 10", "Сокольники", "+79991234567", 1, "сутки", "black", "Позвонить перед доставкой", true},
                {"Анна", "Петрова", "Москва, улица Тверская, 15", "Черкизовская", "+79997654321", 2, "двое суток", "grey", "Позвонить за десять минут", false}
        };
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void successfulOrderTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        if (useTopOrderButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillCustomerData(name, surname, address, metro, phone);

        LocalDate date = LocalDate.now().plusDays(daysUntilDelivery);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String deliveryDate = date.format(formatter);

        orderPage.fillRentalData(deliveryDate, rentalPeriod, colour, comment);
        orderPage.clickOrderButton();
        orderPage.confirmOrder();

        assertTrue("После оформления заказа должно появиться сообщение «Заказ оформлен»", orderPage.isOrderCreated());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}