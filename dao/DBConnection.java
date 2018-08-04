package br.com.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
*
* @author CMarcelo
*/
public abstract class DBConnection {

   public Connection getMyDBConnection(String login,char[] password) throws SQLException, ClassNotFoundException{
       
	   Class.forName("com.mysql.jdbc.Driver");
     
       return DriverManager.getConnection("jdbc:mysql://localhost:3306/projeto_final",login,String.valueOf(password));
   }

}
