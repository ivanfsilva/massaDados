package br.com.ivanfsilva.estrategia5;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class ContaTesteWeb {

    private static ChromeDriver driver;

    @BeforeClass
    public static void reset() {
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\ivanf\\drivers-selenium\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://seubarriga.wcaquino.me");

        driver.findElement(By.id("email")).sendKeys("a@a");
        driver.findElement(By.id("senha")).sendKeys("a");
        driver.findElement(By.tagName("button")).click();

        driver.findElement(By.linkText("reset")).click();
    }

    @Test
    public void inserir() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Adicionar").click();
        driver.findElement(By.id("nome")).sendKeys("Conta estrategia #5");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void listar() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta para saldo')]/..//a").click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");

        Assert.assertEquals("Conta para saldo", nomeConta);
    }

    @Test
    public void alterar() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta para alterar')]/..//a").click();
        driver.findElement(By.id("nome")).sendKeys(" alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta alterada com sucesso!", msg);
    }

    @Test
    public void excluir() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta mesmo nome')]/..//a[2]").click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta removida com sucesso!", msg);
    }

    @AfterClass
    public static void fechar() {
        driver.quit();
    }
}
