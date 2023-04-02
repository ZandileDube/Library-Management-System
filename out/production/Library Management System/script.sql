CREATE TABLE books ( bookID INT NOT NULL AUTO_INCREMENT, reserveStatus varchar(10), title varchar(30), author varchar(30),  isbn varchar(13) , publication varchar(30), status ENUM('BORROWED','AVAILABLE', 'RESERVED'), PRIMARY KEY (bookID));

CREATE TABLE User ( userID INT NOT NULL AUTO_INCREMENT, name varchar(30), surname varchar(30) email varchar(30), phone varchar(10), userType ENUM('LIBRARIAN','STUDENT'), password varchar(100) , PRIMARY KEY (userID) );

CREATE TABLE Status ( statusID int NOT NULL AUTO_INCREMENT,notBorrowed varchar(),   no_reserved_books int, no_lost_books int, fine_amount int, PRIMARY KEY(accountID), FOREIGN KEY (loanID) REFERENCES borrowBook(loanID));

CREATE TABLE logs (logID int NOT NULL AUTO_INCREMENT, userID int ,bookID int, dateborrowed date, dateReturned date, PRIMARY KEY (logID), FOREIGN KEY (userID) REFERENCES user(userID), FOREIGN KEY (bookID) REFERENCES books(bookID));

