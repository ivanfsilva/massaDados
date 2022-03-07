package br.com.ivanfsilva.estrategia1;

import br.com.ivanfsilva.entidades.Usuario;
import br.com.ivanfsilva.service.UsuarioService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioServicoTest {

    private UsuarioService servico = new UsuarioService();
    private static Usuario usuarioGlobal;

    @Test
    public void test_01_inserir() throws Exception {
        Usuario usuario = new Usuario("Usuario estrategia #1", "user@email.com", "passwd");
        usuarioGlobal = servico.salvar(usuario);

        Assert.assertNotNull(usuarioGlobal.getId());
    }

    @Test
    public void test_02_consultar() throws Exception {
        Usuario usuario = servico.findById(usuarioGlobal.getId());
        Assert.assertEquals("Usuario estrategia #1", usuario.getNome());
    }

    @Test
    public void test_03_alterar() throws Exception {
        Usuario usuario = servico.findById(usuarioGlobal.getId());
        usuario.setNome("Usuário alterado");
        servico.salvar(usuario);

        Assert.assertEquals("Usuário alterado", usuario.getNome());
    }

    @Test
    public void test_04_excluir() throws Exception {
        servico.delete(usuarioGlobal);
        Usuario usuarioRemovido = servico.findById(usuarioGlobal.getId());

        Assert.assertNull(usuarioRemovido);
    }
}
