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
        LocalDate date = LocalDate.now();
        LocalDate dueDate = date.plusWeeks(2);
        return dueDate;
       }

    //method used to check the reservation status of a book
    public String Reservation_status(String title, String userID){
        String status = null;
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "Select bookID, statusType from books where title = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,title);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString(2).equals("AVAILABLE")){
                return "book available";
                } else if (resultSet.getString(2).equals("BORROWED")) {
                return "book borrowed";
                } else if (resultSet.getString(2).equals("RESERVED")) {
                    if (resultSet.getString(1).equals(userID)){
                        return "book reserved by you";

                    } else  {
                        return "book reserved";
                    }

                }
            }
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }catch (SQLException e){

            e.printStackTrace();
        }

    }

    public String reservedBy(String bookID){

        try{
            Connection conn = DBConnection.getConnection();
            String sql = "Select userID  from logs bookID = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bookID);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            String userID=resultSet.getString(1);
            return userID;


        }catch (SQLException e){
            e.printStackTrace();
        }





    }

    public boolean Borrow_book(String title, String userID){
//this won't work fix it babe , well figure out why you think it won't work

        try{

            if (Reservation_status(title).equals("AVAILABLE")){
                Connection conn = DBConnection.getConnection();
                LocalDate dueDate = showDueDt();
                LocalDate dateborrowed = LocalDate.now();
                String sql = "BEGIN TRANSACTION\n" +
                        "INSERT INTO LOGS (userID, bookID, dateborrowed, duedate)\n" +
                        "values (?,?,?,?)\n" +
                        "UPDATE books SET statusType=\"BORROWED\" where bookID=?\n" +
                        "COMMIT ";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,userID);
                preparedStatement.setString(2,result[2]);
                preparedStatement.setDate(3, Date.valueOf(dateborrowed));
                preparedStatement.setDate(4,Date.valueOf(dueDate));
                preparedStatement.setString(5,result[2]);
                return true;
            };
                return true;


        }catch (SQLException e) {

            e.printStackTrace();
            return false;
        }

    }




}
