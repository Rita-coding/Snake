package snake;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
     private static Connection conn;
    
    
    private ConnectionFactory(){}
    
    public static Connection getConnection() 
        throws ClassNotFoundException, SQLException{
        
       Class.forName("com.mysql.cj.jdbc.Driver");
            
       conn = DriverManager.getConnection("jdbc:mysql://localhost/snake?" +    
                    "serverTimezone=UTC&user=root&password=oreocsoki");
        
      return conn;
    }
}


