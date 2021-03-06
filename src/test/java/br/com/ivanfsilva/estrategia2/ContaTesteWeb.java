package br.com.ivanfsilva.estrategia2;

import br.com.ivanfsilva.estrategia3.GeradorMassas;
import br.com.ivanfsilva.estrategia3.MassaDAOImpl;
import com.github.javafaker.Faker;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ContaTesteWeb {

    private ChromeDriver driver;
    private Faker faker = new Faker();

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
    public void inserir() {
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Adicionar").click();
        driver.findElement(By.id("nome")).sendKeys(faker.harryPotter().character());
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta adicionada com sucesso!", msg);
    }

    @Test
    public void listar() throws SQLException, ClassNotFoundException {
        String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);
        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), '"+ conta + "')]/..//a").click();
        String nomeConta = driver.findElement(By.id("nome")).getAttribute("value");

        Assert.assertEquals(conta, nomeConta);

    }

    @Test
    public void alterar() throws SQLException, ClassNotFoundException {
        String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);

        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a").click();
        driver.findElement(By.id("nome")).sendKeys(" alterada");
        driver.findElement(By.tagName("button")).click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta alterada com sucesso!", msg);

    }

    @Test
    public void excluir() throws SQLException, ClassNotFoundException {
        String conta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA_SB);

        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Listar").click();
        driver.findElementByXPath("//td[contains(text(), '" + conta + "')]/..//a[2]").click();
        String msg = driver.findElementByXPath("//div[@class='alert alert-success']").getText();

        Assert.assertEquals("Conta removida com sucesso!", msg);

    }

    @After
    public void fechar() {
        driver.quit();
    }
}
