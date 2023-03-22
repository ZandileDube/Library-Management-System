import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DBConnection {
    static Connection con =null;


    public static  Connection getConnection(){
        try{

            //establish connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management_system", "root", "");

        }catch (Exception e) {
            e.printStackTrace();

        }

            return con;
    }

}
