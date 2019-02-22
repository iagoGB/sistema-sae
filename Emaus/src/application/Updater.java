package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class Updater {
	public void change(Doacoes doador, String idDoacao, String nome) throws SQLException {
		//codigo e tutorial --> https://www.caelum.com.br/apostila-java-web/bancos-de-dados-e-jdbc/  
		String id = new Sender().getId(doador);
		System.out.println("ID: "+id);
		setDoador(doador,id,nome);
		setDoacao(doador,idDoacao);
    }
	
	private void setDoador(Doacoes doador, String id, String nome) throws SQLException {
        // conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "update doadores set " +
                  " nome ='" + nome
                + "',instituicao ='" + doador.getInstituicao()
                + "',email ='" + doador.getEmail() 
                + "',telefone ='" + doador.getTelefone() 
                + "',endereco ='" + doador.getEndereco() 
                + "',referencia ='" + doador.getReferencia() 
                + "',bairro ='" + doador.getBairro()
                + "',conhece ='" + doador.getConhece()  
                + "',complementares ='" + doador.getComplementares() 
                + "' WHERE id =" + id;
        
        Statement stmt = (Statement) con.createStatement();
        
        // executa
        stmt.executeUpdate(sql);
        stmt.close();

        System.out.println("Doador Update!");
	}
	
	private void setDoacao(Doacoes doador, String id) throws SQLException {
        // conectando
        Connection con = (Connection) new ConnectionFactory().getConnection();
        
        // cria um preparedStatement
        String sql = "update doacoes set " +
                "conteudo ='" + doador.getConteudo() +
        		"',contato='" + doador.getContato() + 
        		"',coleta='" + doador.getColeta() + 
        		"' WHERE id =" + id;
        Statement stmt = (Statement) con.createStatement();
       
        // executa
        stmt.executeUpdate(sql);
        stmt.close();

        System.out.println("Doacao Update!");
	}
}