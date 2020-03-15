#create PROCEDURE BooksCount (OUT cnt INT)
#BEGIN
#    select count(*) into cnt from books;
#END

#create PROCEDURE getBooks (bookId int)
#BEGIN
#    select * from books where id = bookId;
#END

create PROCEDURE  getCount ()
BEGIN
    select count(*) from Users;
    select count(*) from Books;
END


