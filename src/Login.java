import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public boolean validateLogin(String username,String password){

        if(!username.isEmpty() || !password.isEmpty()){

                try {
                    Connection conn = DBConnection.getConnection();
                    String sql = "SELECT * FROM users WHERE username=? AND password=?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {

                        return true;
                    } else {
                        // Login failed
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }



    }
        return true;
}
}
