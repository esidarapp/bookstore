INSERT INTO categories (id, name, description)
VALUES (1, 'Fiction', 'Fictional category');

INSERT INTO books (id, title, author, isbn, price, description, cover_image)
VALUES (1, 'Kobzar', 'Taras Shevchenko', '978-3-16-148410-0', 250.00, 'A classic Ukrainian book', 'http://example.com/kobzar.jpg');

INSERT INTO books_categories (book_id, category_id)
VALUES (1, 1);