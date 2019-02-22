package application;

import java.util.Calendar;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.Statement;
import java.sql.ResultSet;

public class Sender {
	public void send(Doacoes doador) throws SQLException {
		//codigo e tutorial --> https://www.caelum.com.br/apostila-java-web/bancos-de-dados-e-jdbc/  
		String id = getId(doador);
		System.out.println("ID: "+id);
		if(id == null) { 
			setDoador(doador);
			id = getId(doador);
			setDoacao(doador,id);
		}
		else {
			setDoacao(doador,id);
		}
    }
	
	public String getId(Doacoes doador) throws SQLException {
		String id = null;
		System.out.println("Até aqui foi");
		Connection con = (Connection) new ConnectionFactory().getConnection();
        // cria um preparedStatement
	    String sql = "SELECT * FROM doadores WHERE " +
	    		"nome = '"                 + doador.getNome() + "'";
//	    		"' AND instituicao = '"    + doador.getInstituicao() +
//	    		"' AND email = '"          + doador.getEmail() +
//	    		"' AND telefone = '"       + doador.getTelefone() +
//	    		"' AND endereco = '"       + doador.getEndereco() +
//	    		"' AND referencia = '"     + doador.getReferencia() +
//	    		"' AND bairro = '"         + doador.getBairro() +
//	    		"' AND conhece = '"        + doador.getConhece() +
//	    		"' AND complementares = '" + doador.getComplementares() + "'"; 	
	    System.out.println(sql);
	    Statement stmt = (Statement) con.createStatement();
	    ResultSet resultado = stmt.executeQuery(sql);
	    
		while(resultado.next()) {
	    	//pega o valor da coluna nome, de cada linha:
	    	id = resultado.getString("id");
	    	//imprime no console:
	    	System.out.println(id);
	    }
	    
		stmt.close();
        resultado.close();
        
        return id;
	}
	
	private void setDoador(Doacoes doador) throws SQLException {
        // conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "insert into doadores" +
                " (nome,instituicao,email,telefone,endereco,referencia,bairro,conhece,complementares)"+ //,contato,coleta)" +
                " values (?,?,?,?,?,?,?,?,?)"; //?,?)";
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
        
        // preenche os valores
        stmt.setString(1, doador.getNome());
        stmt.setString(2, doador.getInstituicao());
        stmt.setString(3, doador.getEmail());
        stmt.setString(4, doador.getTelefone());
        stmt.setString(5, doador.getEndereco());
        stmt.setString(6, doador.getReferencia());
        stmt.setString(7, doador.getBairro());
        stmt.setString(8, doador.getConhece());
        stmt.setString(9, doador.getComplementares());
        
        // executa
        stmt.execute();
        stmt.close();

        System.out.println("Doador Gravado!");
	}
	
	private void setDoacao(Doacoes doador, String id) throws SQLException {
        // conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "insert into doacoes" +
                " (iddoador,conteudo,contato,coleta)" +
                " values (?,?,?,?)"; //?,?)";
        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
        
        // preenche os valores
        stmt.setString(1, id);
        stmt.setString(2, doador.getConteudo());
        stmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        stmt.setString(4, doador.getColeta());
        
        // executa
        stmt.execute();
        stmt.close();

        System.out.println("Doacao Gravado!");
	}
}
