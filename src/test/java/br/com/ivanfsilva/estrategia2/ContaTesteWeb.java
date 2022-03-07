package br.com.ivanfsilva.estrategia2;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContaTesteWeb {

    private ChromeDriver driver;

    @Before
    public void login() {
        System.setProperty("webdriver.chrome.driver", "D:\\Users\\ivanf\\drivers-selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://seubarriga.wcaquino.me");

        driver.findElement(By.id("email")).sendKeys("a@a");
        driver.findElement(By.id("senha")).sendKeys("a");
        driver.findElement(By.tagName("button")).click();

    }

    @Test
    public void teste_01_inserir() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Adicionar").click();
        driver.findElement(By.id("nome")).sendKeys("Conta estratégia #1");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void teste_02_listar() {
        login();
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta estratégia #1')]/..//a").click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");

        Assert.assertEquals("Conta estratégia #1", nomeConta);

    }

    @Test
    public void teste_03_alterar() {
        login();
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta estratégia #1')]/..//a").click();
        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys("Conta estratégia #1 alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta alterada com sucesso!", msg);

    }

    @Test
    public void teste_04_excluir() {
        login();
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), 'Conta estratégia #1')]/..//a[2]").click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta removida com sucesso!", msg);

    }

    @After
    public void fechar() {
        driver.quit();
    }
}
