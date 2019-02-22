package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.Statement;  

public class CreateBD {
    CreateBD() throws FileNotFoundException, IOException, SQLException {    	
    	try (BufferedReader br = new BufferedReader(new FileReader("C:\\wamp64\\www\\Repositorio\\enderecos\\enderecos.csv"))) {
        	Connection con = (Connection) new ConnectionFactory().getConnection();
        	String delete = "drop table if exists enderecos";
        	String db = "create table if not exists enderecos (" +
        		"LON varchar(60) NOT NULL," +
      			"LAT varchar(60) NOT NULL," +
      			"STREET varchar(60) NOT NULL" +
      			")";
        	Statement acao = (Statement) con.createStatement();
        	acao.executeUpdate(delete);
        	acao.executeUpdate(db);
        	acao.close();
            
        	String line;
        	while ((line = br.readLine()) != null) {
        		String[] split = line.split(",");
        		String sql = "insert into enderecos (LON,LAT,STREET) values (?,?,?)";
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
                stmt.setString(1, split[0].replaceAll("'", ""));
                stmt.setString(2, split[1].replaceAll("'", ""));
                stmt.setString(3, split[2].replaceAll("'", ""));
                stmt.execute();
                stmt.close();
        	}
    	}
    	try (BufferedReader br = new BufferedReader(new FileReader("C:\\wamp64\\www\\Repositorio\\enderecos\\bairros.csv"))) {
        	Connection con = (Connection) new ConnectionFactory().getConnection();
        	String delete = "drop table if exists bairros";
        	String db = "create table if not exists bairros (" +
        		"NOME varchar(60) NOT NULL" +
      			")";
        	Statement acao = (Statement) con.createStatement();
        	acao.executeUpdate(delete);
        	acao.executeUpdate(db);
        	acao.close();
            
        	String line;
        	while ((line = br.readLine()) != null) {
        		String[] split = line.split(",");
        		String sql = "insert into bairros (NOME) values (?)";
                PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
                stmt.setString(1, split[0].replaceAll("'", ""));
                stmt.execute();
                stmt.close();
        	}
    	}
    	try (BufferedReader br = new BufferedReader(new FileReader("C:\\wamp64\\www\\Repositorio\\enderecos\\bairros.csv"))) {
        	Connection con = (Connection) new ConnectionFactory().getConnection();
        	String db = "create table if not exists doacoes (" +
        		"nome varchar(60) NOT NULL," +
      			"instituicao varchar(60) NOT NULL," +
      			"email varchar(60) NOT NULL," +
      			"telefone varchar(60) NOT NULL," +
      			"endereco varchar(60) NOT NULL," +
      			"referencia varchar(60) NOT NULL," +
      			"bairro varchar(60) NOT NULL," +
      			"conteudo varchar(60) NOT NULL," +
      			"conhece varchar(60) NOT NULL," +
      			"complementares varchar(60) NOT NULL," +
      			"contato varchar(60) NOT NULL," +
      			"coleta varchar(60) NOT NULL" +
      			")";
        	Statement acao = (Statement) con.createStatement();
        	acao.executeUpdate(db);
        	acao.close();
    	}
    }
}