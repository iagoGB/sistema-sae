package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conecta ao banco de dados
public class ConnectionFactory{
    public Connection getConnection(){
        try{
            return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/mydb", "sa", "");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
