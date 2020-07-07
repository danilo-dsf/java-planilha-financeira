package model.dao;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entities.Conta;
import model.entities.Transacao;

/**
 * Classe responsável por toda conexão com banco de dados a partir da entidade Transação
 *
 * @author danilodsf
 */
public class TransacaoDao {

    private Connection conn;

    public TransacaoDao(Connection conn) {
        this.conn = conn;
    }

    public List<Transacao> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT t.*, c.descricao as nome_conta  FROM transacoes t "
                            + "LEFT JOIN contas c ON c.id = t.id_conta");
            rs = st.executeQuery();

            List<Transacao> list = new ArrayList<>();
            Map<Integer, Conta> map = new HashMap<>();

            while (rs.next()) {
                Conta conta = map.get(rs.getInt("id_conta"));
                
                if (conta == null) {
                    conta = instantiateConta(rs);
                    map.put(rs.getInt("id_conta"), conta);
                }
                
                Transacao obj = instantiateTransacao(rs, conta);
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

    public void insert(Transacao obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                    "INSERT INTO transacoes "
                    + "(natureza, descricao, data_movimento, valor, id_conta) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNatureza());
            st.setString(2, obj.getDescricao());
            st.setDate(3, new java.sql.Date(obj.getData().getTime()));
            st.setDouble(4, obj.getValor());
            st.setInt(5, obj.getConta().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Erro inesperado! Nenhum registro afetado!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
    
    public void update(Transacao obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "UPDATE transacoes "
                + "SET natureza = ?, descricao = ?, data_movimento = ?, valor = ?, id_conta = ? "
                + "WHERE id = ?");

            st.setString(1, obj.getNatureza());
            st.setString(2, obj.getDescricao());
            st.setDate(3, new java.sql.Date(obj.getData().getTime()));
            st.setDouble(4, obj.getValor());
            st.setInt(5, obj.getConta().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
    
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM transacoes WHERE id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
    
    public void deleteByContaId(Integer contaId) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM transacoes WHERE id_conta = ?");

            st.setInt(1, contaId);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
    
    public double verifyContaSaldo(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement("SELECT "
                    + " COALESCE(sub.entrada - sub.saida, 0) AS total "
                    + " FROM ( "
                    + "	SELECT "
                    + "		SUM(CASE WHEN t.natureza = 'E' THEN t.valor ELSE 0 END) AS entrada, "
                    + "		SUM(CASE WHEN t.natureza = 'S' THEN t.valor ELSE 0 END) AS saida "
                    + "	FROM transacoes t "
                    + " WHERE t.id_conta = ?"
                    + ") sub;");
            st.setInt(1, id);
            
            rs = st.executeQuery();
            
            rs.next();
            return rs.getDouble("total");
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    public double verifyContaSaldo(Integer idConta, Integer idTransacao) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement("SELECT "
                    + " COALESCE(sub.entrada - sub.saida, 0) AS total "
                    + " FROM ( "
                    + "	SELECT "
                    + "		SUM(CASE WHEN t.natureza = 'E' THEN t.valor ELSE 0 END) AS entrada, "
                    + "		SUM(CASE WHEN t.natureza = 'S' THEN t.valor ELSE 0 END) AS saida "
                    + "	FROM transacoes t "
                    + " WHERE t.id_conta = ? AND t.id <> ?"
                    + ") sub;");
            st.setInt(1, idConta);
            st.setInt(2, idTransacao);
            
            rs = st.executeQuery();
            
            rs.next();
            return rs.getDouble("total");
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    private Transacao instantiateTransacao(ResultSet rs, Conta conta) throws SQLException {
        Transacao obj = new Transacao();
        obj.setId(rs.getInt("id"));
        obj.setNatureza(rs.getString("natureza"));
        obj.setDescricao(rs.getString("descricao"));
        obj.setData(new java.util.Date(rs.getTimestamp("data_movimento").getTime()));
        obj.setValor(rs.getDouble("valor"));
        obj.setConta(conta);
        return obj;
    }

    private Conta instantiateConta(ResultSet rs) throws SQLException {
        Conta obj = new Conta();
        obj.setId(rs.getInt("id_conta"));
        obj.setDescricao(rs.getString("nome_conta"));
        return obj;
    }
}
