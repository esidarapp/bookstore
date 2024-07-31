INSERT INTO books (title, author, isbn, price, description, cover_image)
VALUES
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 10.99, 'A novel about the serious issues of rape and racial inequality.', 'http://example.com/mockingbird-cover.jpg');

INSERT INTO books (title, author, isbn, price, description, cover_image)
VALUES
('Pride and Prejudice', 'Jane Austen', '978-1-85326-000-1', 8.99, 'A novel that charts the development of the protagonist.', 'http://example.com/pride-cover.jpg');

INSERT INTO books (title, author, isbn, price, description, cover_image)
VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 12.99, 'A story about the Jazz Age in the United States.', 'http://example.com/gatsby-cover.jpg');

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'To Kill a Mockingbird'), 1);

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'To Kill a Mockingbird'), 2);

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'Pride and Prejudice'), 1);

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'Pride and Prejudice'), 2);

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'The Great Gatsby'), 1);

INSERT INTO books_categories (book_id, category_id)
VALUES ((SELECT id FROM books WHERE title = 'The Great Gatsby'), 2);
