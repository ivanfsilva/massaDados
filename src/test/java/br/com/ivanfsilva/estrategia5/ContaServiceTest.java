package br.com.ivanfsilva.estrategia5;

import br.com.ivanfsilva.dao.utils.ConnectionFactory;
import br.com.ivanfsilva.entidades.Conta;
import br.com.ivanfsilva.entidades.Usuario;
import br.com.ivanfsilva.service.ContaService;
import br.com.ivanfsilva.service.UsuarioService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

public class ContaServiceTest {

    private ContaService service = new ContaService();
    private UsuarioService usuarioService = new UsuarioService();

    @BeforeClass
    public static void inserirConta() throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        conn.createStatement().executeUpdate("DELETE FROM transacoes");
        conn.createStatement().executeUpdate("DELETE FROM contas");
        conn.createStatement().executeUpdate("DELETE FROM usuarios");
        conn.createStatement().executeUpdate("INSERT INTO usuarios (id, nome, email, senha) VALUES (1, 'Usuario de controle', 'usuario@email.com', 'passwd')");
        conn.createStatement().executeUpdate("INSERT INTO contas (id, nome, usuario_id) VALUES (1, 'Conta para testes', 1)");
        conn.createStatement().executeUpdate("INSERT INTO contas (id, nome, usuario_id) VALUES (2, 'Conta CT005 alteracao', 1)");
        conn.createStatement().executeUpdate("INSERT INTO contas (id, nome, usuario_id) VALUES (3, 'Conta para deletar', 1)");
    }

    @Test
    public void inserir() throws Exception {
        Usuario usuario = usuarioService.findById(1L);
        Conta conta = new Conta("Conta salva", usuario);
        Conta contaSalva = service.salvar(conta);

        Assert.assertNotNull(contaSalva.getId());
    }

    @Test
    public void alterar() throws Exception {
        Conta contaTeste = service.findByName("Conta CT005 alteracao");
        contaTeste.setNome("Conta alterada");
        Conta contaAlterada = service.salvar(contaTeste);
        Assert.assertEquals("Conta alterada", contaAlterada.getNome());
    }

    @Test
    public void consultar() throws Exception {
        Conta contaBuscada = service.findById(1L);
        Assert.assertEquals("Conta para testes", contaBuscada.getNome());
    }

    @Test
    public void excluir() throws Exception {
        Conta contaTeste = service.findByName("Conta para deletar");
        service.delete(contaTeste);
        Conta contaBuscada = service.findById((contaTeste.getId()));

        Assert.assertNull(contaBuscada);

    }
}

