import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MetalShopTest extends BaseTest {

    @Test
    @Order(2)
    public void emptyPasswordTest() {
        driver.findElement(By.xpath("//a[text() = 'Moje konto']")).click();
        driver.findElement(By.cssSelector("#password")).sendKeys("asdasd");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.",
                driver.findElement(By.cssSelector(".woocommerce-error")).getText());

    }

    @Test
    @Order(3)
    public void emptyUsernameTest() {
        driver.findElement(By.xpath("//a[text() = 'Moje konto']")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("JanK");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", driver.findElement(By.cssSelector(".woocommerce-error")).getText());

    }

    @Test
    public void checkLogoAndSearchOnHomePage() {
        Assertions.assertTrue(driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).isDisplayed()
                && driver.findElement(By.cssSelector(".search-field")).isDisplayed());

    }

    @Test
    public void checkLogoAndSearchOnLoginPage() {
        driver.findElement(By.xpath("//a[text() = 'Moje konto']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).isDisplayed()
                && driver.findElement(By.cssSelector(".search-field")).isDisplayed());
    }

    @Test
    public void createNewCustomer() {
        driver.findElement(By.xpath("//a[text() = 'register']")).click();

        Faker faker = new Faker();
        String username = faker.name().username();
        String email = username + faker.random().nextInt(1000) + "@gmail.com";
        String password = "asdasd";

        driver.findElement(By.cssSelector("#user_login")).sendKeys(username);
        driver.findElement(By.id("user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[text() = 'User successfully registered.']")));
        Assertions.assertTrue(driver.findElement(By.xpath("//ul[text() = 'User successfully registered.']")).isDisplayed());
        Actions actions = new Actions(driver);
        actions.doubleClick(driver.findElement(By.cssSelector(".ur-submit-button"))).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[text() = 'User successfully registered.']")));
        Assertions.assertTrue(driver.findElement(By.xpath("//ul[text() = 'User successfully registered.']")).isDisplayed());

    }

    @Test
    public void movingFromMainPageToKontakt() {
        driver.findElement(By.xpath("//a[text() = 'Kontakt']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//h1[@class = 'entry-title']")).isDisplayed());
    }

    @Test
    public void movingFromLoginPageToMainPage() {
        driver.findElement(By.xpath("//a[text() = 'Moje konto']")).click();
        driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).isDisplayed());

    }

    @Test
    public void sendMessageTestFailed() {

        Faker faker = new Faker();
        String message = faker.regexify("[a-z]{25}");

        driver.findElement(By.xpath("//a[text() = 'Kontakt']")).click();
        driver.findElement(By.xpath("//input[@name = 'your-name']")).sendKeys("Jan Krowa");
        driver.findElement(By.xpath("//input[@name = 'your-email']")).sendKeys("JanKrowa@gmail.com");
        driver.findElement(By.xpath("//input[@name = 'your-subject']")).sendKeys("Problem z wysyłką.");
        driver.findElement(By.xpath("//textarea[@name = 'your-message']")).sendKeys(message);
        driver.findElement(By.xpath("//input[@type = 'submit']")).click();
        Wait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class= 'wpcf7-response-output']")));
        Assertions.assertEquals("Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.",
                driver.findElement(By.xpath("//div[@class= 'wpcf7-response-output']")).getText());

    }

    @Test
    public void checkCartAddToCartAndVerifyIfAdded() {

        driver.findElement(By.className("cart-contents")).click();
        Assertions.assertTrue(driver.findElement(By.className("cart-empty")).isDisplayed());
        driver.navigate().back();
        driver.findElement(By.xpath("//a[@data-product_id = '24']")).click();
        driver.findElement(By.xpath("//a[@class= 'added_to_cart wc-forward']")).click();
        Assertions.assertEquals("Podsumowanie koszyka", driver.findElement(By.cssSelector(".cart_totals > h2")).getText());
        List<WebElement> rowsList = driver.findElements(By.xpath("//table[@class = 'shop_table shop_table_responsive']//tr"));
        Assertions.assertEquals(3, rowsList.size());
        String value = driver.findElement(By.xpath("//table[@class = 'shop_table shop_table_responsive']//th[1]")).getText();
        Assertions.assertEquals("Kwota", value);

    }

    @Test
    public void removeFromCartTest() {
        driver.findElement(By.xpath("//a[@data-product_id = '24']")).click();
        driver.findElement(By.xpath("//a[@class= 'added_to_cart wc-forward']")).click();
        driver.findElement(By.xpath("//a[@class = 'remove']")).click();
        Assertions.assertEquals("Usunięto: „Srebrna moneta 5g - UK 1980”. Cofnij?",
                driver.findElement(By.xpath("//div[@class = 'woocommerce-message']")).getText());

    }

    @Test
    @Order(1)
    public void loginMethod() {
        login();
    }

    @AfterAll
    public static void closeBrowser() {
        driver.quit();
    }
}


