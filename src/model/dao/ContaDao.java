package model.dao;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entities.Conta;

/**
 * Classe responsável por toda conexão com banco de dados a partir da entidade Conta
 * 
 * @author danilodsf
 */
public class ContaDao {
    
    private Connection conn;
    
    public ContaDao(Connection conn) {
        this.conn = conn;
    }
    
    public List<Conta> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement("SELECT * FROM contas");
            rs = st.executeQuery();
            
            List<Conta> list = new ArrayList<>();
            
            while(rs.next()) {
                Conta obj = instantiateConta(rs);
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
    
    public void insert(Conta obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                    "INSERT INTO contas "
                    + "(descricao) "
                    + "VALUES "
                    + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getDescricao());
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
    
    public void update(Conta obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "UPDATE contas "
                + "SET descricao = ? "
                + "WHERE id = ?");

            st.setString(1, obj.getDescricao());
            st.setInt(2, obj.getId());

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
            st = conn.prepareStatement("DELETE FROM contas WHERE id = ?");

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
    
    public boolean hasExistingMovements(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement("SELECT COUNT(1) AS number_of_movements FROM transacoes WHERE id_conta = ?");
            
            st.setInt(1, id);
            
            rs = st.executeQuery();
            
            rs.next();
            if (rs.getInt("number_of_movements") > 0)
                return true;
            
            return false;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    
    private Conta instantiateConta(ResultSet rs) throws SQLException {
        Conta obj = new Conta();
        obj.setId(rs.getInt("id"));
        obj.setDescricao(rs.getString("descricao"));
        return obj;
    }
}
