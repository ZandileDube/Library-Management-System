CREATE TABLE books ( bookID INT NOT NULL, reserveStatus varchar(10), title varchar(30), author varchar(30),  isbn varchar(13) , publication varchar(30), PRIMARY KEY (bookID)
);
CREATE TABLE borrowBook ( loanID int NOT NULL, bookID int NOT NULL,  dateBorrowed Date, dueDate Date, dateReturned Date, loanStatus ENUM('Overdue','Returned','Renewed') PRIMARY KEY (loanID), FOREIGN KEY (bookID) REFERENCES books(bookID), FOREIGN KEY (userID) REFERENCES user(userID));
CREATE TABLE reservedBook (reserveID INT NOT NULL, bookID int NOT NULL, userID int NOT NULL, dateReserved Date, reserveStatus ENUM('Available', 'Reserved'),PRIMARY KEY (reserveID), FOREIGN KEY (bookID) REFERENCES books(bookID), FOREIGN KEY (userID) REFERENCES user(userID));
CREATE TABLE user ( userID INT NOT NULL, name varchar(30), email varchar(30), phone varchar(10), address varchar(30),userType ENUM('LIBRARIAN','STUDENT'), password varchar(100) , PRIMARY KEY (userID) );
CREATE TABLE account ( accountID int NOT NULL, loanID int NOT NULL,  no_borrowed_books int,   no_reserved_books int, no_lost_books int, fine_amount int, PRIMARY KEY(accountID), FOREIGN KEY (loanID) REFERENCES borrowBook(loanID));

