package br.com.ivanfsilva.estrategia3;

import br.com.ivanfsilva.entidades.Conta;
import br.com.ivanfsilva.entidades.Usuario;
import br.com.ivanfsilva.service.ContaService;
import br.com.ivanfsilva.service.UsuarioService;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Test;

public class ContaServiceTest {

    private static Faker faker = new Faker();

    private ContaService service = new ContaService();
    private UsuarioService usuarioService = new UsuarioService();

    @Test
    public void inserir() throws Exception {
        Usuario usuario = new Usuario(faker.name().fullName(), faker.internet().emailAddress(), faker.internet().password());
        usuarioService.salvar(usuario);
        Conta conta = new Conta(faker.superhero().name(), usuario);
        Conta contaSalva = service.salvar(conta);

        Assert.assertNotNull(contaSalva.getId());
    }

    @Test
    public void alterar() throws Exception {
        Conta contaTeste = service.findByName(new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA));
        String novoNome = faker.ancient().god() + " " + faker.ancient().titan();
        contaTeste.setNome(novoNome);
        Conta contaAlterada = service.salvar(contaTeste);
        Assert.assertEquals(novoNome, contaAlterada.getNome());
    }

    @Test
    public void consultar() throws Exception {
        String nomeConta = new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA);
        Conta contaTeste = service.findByName(nomeConta);
        Conta contaBuscada = service.findById((contaTeste.getId()));

        Assert.assertEquals(contaTeste.getNome(), contaBuscada.getNome());
    }

    @Test
    public void excluir() throws Exception {
        Conta contaTeste = service.findByName(new MassaDAOImpl().obterMassa(GeradorMassas.CHAVE_CONTA));

        service.delete(contaTeste);
        Conta contaBuscada = service.findById((contaTeste.getId()));

        Assert.assertNull(contaBuscada);

    }
}
