import java.sql.*;
import java.time.LocalDate;

public class Book {

   private String  title;
    private String author;
    private String isbn;
    private String publication;
private StatusType status;
    enum StatusType{
        RESERVED,
        AVAILABLE,
        BORROWED
    };
    LocalDate date = LocalDate.now();
    String sql;


   //getters and setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatusType(StatusType status) {
        this.status = status;
    }

    public Book(String title, String author, String isbn, String publication, StatusType status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publication = publication;
        this.status = status;
        validate();
    }


    //method to validate parameters to ensure they are not empty or null
    private void validate(){
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title is missing or falsy");
        }
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author is missing or falsy");
        }
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN is missing or falsy");
        }
        if (publication == null || publication.isEmpty()) {
            throw new IllegalArgumentException("Publication is missing or falsy");
        }
        if (status == null ) {
            throw new IllegalArgumentException("Status is missing or falsy");
        }
    }

    //method that returns a duedate of a book
       public LocalDate showDueDt(){

        LocalDate dueDate = date.plusWeeks(2);
        return dueDate;
       }

    //method used to check the reservation status of a book
    public String Reservation_status(String title, String userID){
        String reservationStatus  = null;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "Select bookID, statusType from books where title = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,title);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString(2).equals("AVAILABLE")) {
                    String bookID=resultSet.getString(1);
                    reservationStatus="available";

                } else if (resultSet.getString(2).equals("BORROWED")) {
                    reservationStatus= "book borrowed";

                } else if (resultSet.getString(2).equals("RESERVED")) {
                    if (userID.equals(reservedBy(resultSet.getString(1)))) {
                        reservationStatus= "book reserved by you";

                    } else {
                        reservationStatus= "book reserved";
                    }

                }

            }
            resultSet.close();
            preparedStatement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();

        }

        return reservationStatus;
    }

    public String reservedBy(String bookID){

        String userID =null;
        try{
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT userID  FROM logs WHERE bookID = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bookID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userID = resultSet.getString(1);
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();

        }

        return userID;
    }
public boolean Borrow_book(String title, String userID){

        try{
            if (Reservation_status(title,userID).equals("book available")){
                Connection conn = DBConnection.getConnection();
                LocalDate dueDate = showDueDt();
                String sql = "BEGIN TRANSACTION\n" +
                        "INSERT INTO logs (userID, bookID, dateborrowed, duedate)\n" +
                        "VALUES (?,(SElECT bookID FROM books WHERE title = ? AND statusType = 'AVAILABLE'),?,?)\n" +
                        "UPDATE books SET statusType= 'BORROWED' WHERE bookID= (SELECT bookID FROM books WHERE title = ? AND statusType = 'AVAILABLE')\n" +
                        "COMMIT ";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,userID);
                preparedStatement.setString(2,title);
                preparedStatement.setDate(3, Date.valueOf(date));
                preparedStatement.setDate(4,Date.valueOf(dueDate));
                preparedStatement.setString(5,title);
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }

public String Reserve_book(String title, String userID){
        String status = Reservation_status(title,userID);
        String result = null;
        try{
            if (status.equals("available")){
                Connection conn = DBConnection.getConnection();
                String sql =  "BEGIN TRANSACTION\n" +
                        "INSERT INTO LOGS (userID, bookID, dateReserved)\n" +
                        "VALUES (?,(SELECT bookID FROM books WHERE title = ? AND statusType = 'AVAILABLE'),?)\n" +
                        "UPDATE books SET statusType='RESERVED' WHERE bookID=(SELECT bookID FROM books WHERE title = ? AND statusType = 'AVAILABLE')\n" +
                        "COMMIT ";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,userID);
                preparedStatement.setString(2,title);
                preparedStatement.setDate(3, Date.valueOf(date));
                preparedStatement.setString(4,title);
                int updateCount = preparedStatement.executeUpdate();
                if (updateCount > 0){
                     result= "Reserved successfully";
                } else {
                    throw new RuntimeException("Failed to reserve book ");
                }


            }else if (status.equals("book reserved by you")){
                result="Reserved by you";

            }else {

                result="Not available for reservation";
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    return result;
}

public String Return_book(String userID, String bookID){
    String result=null;
        try{
            Connection conn = DBConnection.getConnection();
            sql = "UPDATE logs, books\n" +
                    "SET logs.dateReturned = ? , books.statusType = ?\n" +
                    "FROM logs \n" +
                    "INNER JOIN books ON logs.bookID = books.bookID \n" +
                    "WHERE logs.bookID =? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, String.valueOf(StatusType.AVAILABLE));
            preparedStatement.setString(3, bookID);

            result ="Book returned";
            }catch (SQLException e) {
            e.printStackTrace();
        }
    return result;
}

    public String display_Book(String title){
        String result = "";
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM books WHERE title=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("publication"),
                        resultSet.getString("isbn"),
                        Book.StatusType.AVAILABLE
                );
                result = "Title: " + book.getTitle() + "\n"
                        + "Author: " + book.getAuthor() + "\n"
                        + "Publication: " + book.getPublication() + "\n"
                        + "ISBN: " + book.getIsbn() + "\n";
            } else {
                result = "Book not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }






}
