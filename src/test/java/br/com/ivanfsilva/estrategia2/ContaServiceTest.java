package br.com.ivanfsilva.estrategia2;

import br.com.ivanfsilva.entidades.Conta;
import br.com.ivanfsilva.entidades.Usuario;
import br.com.ivanfsilva.service.ContaService;
import br.com.ivanfsilva.service.UsuarioService;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContaServiceTest {

    private static Faker faker = new Faker();

    private ContaService service = new ContaService();
    private UsuarioService usuarioService = new UsuarioService();
    private static Usuario usuarioGlobal;
    private Conta contaTeste;

    @BeforeClass
    public static void criarUsuario() {
        usuarioGlobal = new Usuario(faker.name().fullName(), faker.internet().emailAddress(), faker.internet().password());
    }

    @Before
    public void inserirConta() throws Exception {
        Usuario usuarioSalvo = usuarioService.salvar(usuarioGlobal);
        Conta conta = new Conta(faker.superhero().name(), usuarioSalvo);
        contaTeste = service.salvar(conta);
    }

    @Test
    public void inserir() throws Exception {
        Conta conta = new Conta(faker.superhero().name(), usuarioGlobal);
        Conta contaSalva = service.salvar(conta);

        Assert.assertNotNull(contaSalva.getId());
    }

    @Test
    public void alterar() throws Exception {

        String novoNome = faker.ancient().god();
        contaTeste.setNome(novoNome);
        Conta contaAlterada = service.salvar(contaTeste);
        Assert.assertEquals(novoNome, contaAlterada.getNome());
    }

    @Test
    public void consultar() throws Exception {
        Conta contaBuscada = service.findById((contaTeste.getId()));

        Assert.assertEquals(contaTeste.getNome(), contaBuscada.getNome());
    }

    @Test
    public void excluir() throws Exception {
        service.delete(contaTeste);
        Conta contaBuscada = service.findById((contaTeste.getId()));

        Assert.assertNull(contaBuscada);

    }
}
