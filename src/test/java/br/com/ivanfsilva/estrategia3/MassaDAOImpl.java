package br.com.ivanfsilva.estrategia3;

import br.com.ivanfsilva.dao.utils.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MassaDAOImpl {

    public void inserirMassa(String tipo, String valor) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(
                "INSERT INTO massas ( tipo, valor ) VALUES (?, ?)");

        stmt.setString(1, tipo);
        stmt.setString(2, valor);
        stmt.executeUpdate();
        stmt.close();
    }

    public String obterMassa(String tipo) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(
                "WITH M AS ( SELECT id, valor FROM massas WHERE tipo = ? AND usada = false " +
                        " ORDER BY id LIMIT 1 ) UPDATE massas M2 SET usada = true FROM M WHERE m.id = m2.id RETURNING m.valor ");

        stmt.setString(1, tipo);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) { return null; }

        return rs.getString(1);
    }

    public Integer obterEstoque(String tipo) throws SQLException, ClassNotFoundException {
        PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(
            " SELECT COUNT(*) FROM massas WHERE tipo = ? AND usada = FALSE ");

        stmt.setString(1, tipo);
        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) { return null; }

        return rs.getInt(1);
    }
}
