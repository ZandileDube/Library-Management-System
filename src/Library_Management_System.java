import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Library_Management_System {
    ArrayList<String>book = new ArrayList<String>();


    public String add_Book(String title, String author, String isbn, String publication, String status){
        String result=null;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO books (title, author, isbn, publication, status) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,author);
            preparedStatement.setString(3,isbn);
            preparedStatement.setString(4,publication);
            preparedStatement.setString(5,"AVAILABLE");
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount > 0){
               result ="book add to database";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public String delete_Book(String bookID){
        String result=null;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE * FROM books WHERE bookID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,bookID);
            int updateCount = preparedStatement.executeUpdate();
            if (updateCount > 0){
                result ="book add to database";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }



}
