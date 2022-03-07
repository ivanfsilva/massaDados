package massaDados;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class InicioSelenium {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\ivanf\\drivers-selenium\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://seubarriga.wcaquino.me");

        driver.findElement(By.id("email")).sendKeys("a@a");
        driver.findElement(By.id("senha")).sendKeys("a");
        driver.findElement(By.tagName("button")).click();

        driver.quit();
    }
}
