package br.com.ivanfsilva.estrategia3;

import br.com.ivanfsilva.entidades.Conta;
import br.com.ivanfsilva.entidades.Usuario;
import br.com.ivanfsilva.service.ContaService;
import br.com.ivanfsilva.service.UsuarioService;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class GeradorMassas {

    public static final String CHAVE_CONTA_SB = "CONTA_SB";
    public static final String CHAVE_CONTA = "CONTA";

    public void gerarContaSeuBarriga() throws SQLException, ClassNotFoundException {
        ChromeDriver driver;

        System.setProperty("webdriver.chrome.driver", "D:\\Users\\ivanf\\drivers-selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://seubarriga.wcaquino.me");

        driver.findElement(By.id("email")).sendKeys("a@a");
        driver.findElement(By.id("senha")).sendKeys("a");
        driver.findElement(By.tagName("button")).click();

        Faker faker = new Faker();
        String registro = faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();

        driver.findElementByLinkText("Contas").click();
        driver.findElementByLinkText("Adicionar").click();
        driver.findElement(By.id("nome")).sendKeys(registro);
        driver.findElement(By.tagName("button")).click();
        driver.quit();

        new MassaDAOImpl().inserirMassa(CHAVE_CONTA_SB, registro);
    }

    public void gerarConta() throws Exception {
        Faker faker = new Faker();

        ContaService service = new ContaService();
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuarioGlobal = new Usuario(faker.name().fullName(), faker.internet().emailAddress(), faker.internet().password());
        Usuario usuarioSalvo = usuarioService.salvar(usuarioGlobal);
        Conta conta = new Conta(faker.superhero().name(), usuarioSalvo);
        service.salvar(conta);
        new MassaDAOImpl().inserirMassa(CHAVE_CONTA, conta.getNome());
    }

    public static void main(String[] args) throws Exception {
        GeradorMassas gerador = new GeradorMassas();
        for (int i = 0; i < 10; i++) {
            gerador.gerarConta();
        }
    }
}
