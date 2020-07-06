package model.dao;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Conta;
import model.entities.Resumo;

/**
 *
 * @author danilodsf
 */
public class ResumoDao {

    private Connection conn;

    public ResumoDao(Connection conn) {
        this.conn = conn;
    }

    public List<Resumo> getSummary() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT "
                    + "	sub.*, "
                    + " sub.entrada - sub.saida AS total "
                    + " FROM ( "
                    + "	SELECT "
                    + "		c.id AS id_conta, "
                    + "		c.descricao AS descricao_conta, "
                    + "		SUM(CASE WHEN t.natureza = 'E' THEN t.valor ELSE 0 END) AS entrada, "
                    + "		SUM(CASE WHEN t.natureza = 'S' THEN t.valor ELSE 0 END) AS saida "
                    + "	FROM transacoes t "
                    + "	LEFT JOIN contas c ON c.id = t.id_conta "
                    + "	GROUP BY c.id, c.descricao "
                    + ") sub;");
            
            rs = st.executeQuery();
            
            List<Resumo> list = new ArrayList<>();
            Map<Integer, Conta> map = new HashMap<>();
            
            while (rs.next()) {
                Conta conta = map.get(rs.getInt("id_conta"));
                
                if (conta == null) {
                    conta = instantiateConta(rs);
                    map.put(rs.getInt("id_conta"), conta);
                }
                Resumo obj = instantiateResumo(rs, conta);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    private Resumo instantiateResumo(ResultSet rs, Conta conta) throws SQLException {
        Resumo obj = new Resumo();
        obj.setConta(conta);
        obj.setEntrada(rs.getDouble("entrada"));
        obj.setSaida(rs.getDouble("saida"));
        obj.setSaldo(rs.getDouble("total"));
        return obj;
    }
    
    private Conta instantiateConta(ResultSet rs) throws SQLException {
        Conta obj = new Conta();
        obj.setId(rs.getInt("id_conta"));
        obj.setDescricao(rs.getString("descricao_conta"));
        return obj;
    }
}
