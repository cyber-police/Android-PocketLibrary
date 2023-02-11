CREATE DATABASE MyLibrary;
USE MyLibrary;

CREATE TABLE file_type
(
type_file varchar(255),
is_downloadble bit,
size varchar(255),
file_format varchar(255),
number_of_pages int,
has_illustrations bit,
has_parts bit,
how_many_parts int,
this_part int,
time_took_to_read varchar(255)
);
ALTER TABLE file_type ADD file_type_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_type', RESEED, 0);

INSERT INTO file_type (type_file, is_downloadble, size, file_format, number_of_pages, has_illustrations, has_parts, how_many_parts, this_part, time_took_to_read) 
VALUES ('book', 'true', '250MB', 'PDF', 1345, 'true', 'true', 4, 1, '50 hours'), 
('encyclopedia', 'true', '330MB', 'EPUB', 3000, 'true', 'true', 3, 2, '145 hours'),
('journal', 'true', '450MB', 'FB2', 45, 'true', 'false', 0, 0, '25 minutes');

CREATE TABLE file_language
(
file_language varchar(255),
is_translated bit,
how_many_translated int,
who_translated varchar(255)
);
ALTER TABLE file_language ADD file_language_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_language', RESEED, 0);

INSERT INTO file_language (file_language, is_translated, how_many_translated, who_translated) 
VALUES ('Ukrainian', 'true', 4, 'Pavlo Stupka'), 
('English', 'true', 17, 'Jeremy Octins');

CREATE TABLE file_category
(
file_category varchar(255),
age_rating int,
collection_name varchar(255)
);
ALTER TABLE file_category ADD file_category_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_category', RESEED, 0);

INSERT INTO file_category (file_category, age_rating, collection_name) 
VALUES ('Children Literature', 6, 'Wild School'), 
('Science', 12, 'Animals'),
('Entertainment', 13, null);

CREATE TABLE library_file
(
file_name varchar(255),
is_available bit,
year_published int,
file_description varchar(255),
is_free bit,
price int,
how_many_times_read int,
rating float,
how_long_was_written varchar(255),
file_type_id int NOT NULL,
file_language_id int NOT NULL,
file_category_id int NOT NULL,
FOREIGN KEY (file_type_id) REFERENCES file_type(file_type_id),
FOREIGN KEY (file_language_id) REFERENCES file_language(file_language_id),
FOREIGN KEY (file_category_id) REFERENCES file_category(file_category_id)
);
ALTER TABLE library_file ADD file_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('library_file', RESEED, 0);

INSERT INTO library_file (file_name, is_available, year_published, file_description, is_free, price, how_many_times_read, rating, how_long_was_written, file_type_id, file_language_id, file_category_id) 
VALUES ('The Adventures In Wild School VOL.1', 'true', 2011, 'This time...', 'false', 300, 150000, 4.3, '1.5 years', 0, 0, 0), 
('Awesome Wild World', 'false', 2005, 'The most exciting...', 'true', null, 100500, 3.5, '2.5 years', 1, 1, 1),
('Nature Gather', 'true', 2011, 'Good to know', 'true', null, 1051, 4.1, '30 days', 2, 1, 2);

CREATE TABLE file_author
(
file_author varchar(255),
date_of_birth date,
education varchar(255),
gender varchar(255),
address varchar(255),
partner varchar(255),
children int,
avarage_salary int,
is_dead bit,
date_death date
);
ALTER TABLE file_author ADD file_author_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_author', RESEED, 0);

INSERT INTO file_author (file_author, date_of_birth, education, gender, address, partner, children, avarage_salary, is_dead, date_death) 
VALUES ('Vsevolod Nestajko', '1930-01-30', 'Phylologist', 'male', null, null, 0, null, 'true', '2014-08-16'), 
('Howard Przerz', '1965-07-10', 'Proffesor', 'male', 'Silent Hunter 3/4', 'Rebecca Schon', 1, 100500, 'false', null);

CREATE TABLE type_contact
(
type_contact varchar(255)
);
ALTER TABLE type_contact ADD type_contact_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('type_contact', RESEED, 0);

INSERT INTO type_contact (type_contact) VALUES
('Phone Number'), ('Skype');

CREATE TABLE contacts
(
contact_value varchar(255),
type_contact_id int NOT NULL,
file_author_id int NOT NULL,
FOREIGN KEY (file_author_id) REFERENCES file_author(file_author_id),
FOREIGN KEY (type_contact_id) REFERENCES type_contact(type_contact_id)
);
ALTER TABLE contacts ADD contacts_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('contacts', RESEED, 0);

INSERT INTO contacts (contact_value, type_contact_id, file_author_id) VALUES
('+100345476889', 0, 1), ('hunter.writer', 1, 1);

CREATE TABLE author_file_connection
(
file_id int NOT NULL,
file_author_id int NOT NULL,
FOREIGN KEY (file_id) REFERENCES library_file(file_id),
FOREIGN KEY (file_author_id) REFERENCES file_author(file_author_id)
);
ALTER TABLE author_file_connection ADD author_file_connection_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('author_file_connection', RESEED, 0);

INSERT INTO author_file_connection (file_id, file_author_id) VALUES
(0, 0), (1, 1), (2, 0), (2, 1);

DROP TABLE author_file_connection, contacts, file_author, file_category, file_language, file_type, library_file, type_contact;

SELECT *
FROM author_file_connection
INNER JOIN library_file ON library_file.file_id = author_file_connection.file_id
INNER JOIN file_author ON file_author.file_author_id = author_file_connection.file_author_id
INNER JOIN file_type ON library_file.file_type_id = file_type.file_type_id
INNER JOIN file_category ON library_file.file_category_id = file_category.file_category_id
INNER JOIN file_language ON library_file.file_language_id = file_language.file_language_id;