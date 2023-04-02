import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Account {
    public int noBorrowedBooks() {
        int numBorrowedBooks = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(bookID) FROM books WHERE statusType = 'Borrowed'");) {
//            preparedStatement.setString(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numBorrowedBooks = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numBorrowedBooks;
    }

    public static double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        final double fine = 14;


        if (dueDate.isBefore(returnDate) || dueDate.isEqual(returnDate)) {
            // Book returned on or before due date, no fine
            return 0.0;
        } else {
            // Book returned after due date, calculate fine

            return fine;
        }
    }

}
