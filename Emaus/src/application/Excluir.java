package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.Statement;  

public class Excluir {
	public void execute(String id) throws SQLException {
		// conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "DELETE FROM doacoes WHERE id=" + id;
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
       
        // executa
        stmt.execute();
        stmt.close();
	}
	
	public void limpar(String query) throws SQLException {
		// conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "DELETE FROM doacoes " + query;
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
       
        // executa
        stmt.execute();
        stmt.close();
	}
}
