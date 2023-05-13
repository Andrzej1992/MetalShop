import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    static WebDriver driver;

    @BeforeAll
    public static void mainSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
    }
    @BeforeEach
    public void getUrl() {
        driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).click();
    }

    public void login() {
        driver.findElement(By.xpath("//a[text() = 'Moje konto']")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("JanK");
        driver.findElement(By.cssSelector("#password")).sendKeys("asdasd");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class = 'woocommerce-MyAccount-content']//a")).isDisplayed());

    }


}