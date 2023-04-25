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

CREATE TABLE file_language
(
file_language varchar(255),
is_translated bit,
how_many_translated int,
who_translated varchar(255)
);
ALTER TABLE file_language ADD file_language_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_language', RESEED, 0);

CREATE TABLE file_category
(
file_category varchar(255),
age_rating int,
collection_name varchar(255)
);
ALTER TABLE file_category ADD file_category_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_category', RESEED, 0);

CREATE TABLE library_file
(
file_name varchar(255),
is_available bit,
year_published int,
file_description varchar(3000),
is_free bit,
price int,
how_many_times_read int,
rating float,
file_image varchar(3000),
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

CREATE TABLE file_author
(
file_author varchar(255),
date_of_birth varchar(10),
education varchar(255),
gender varchar(255),
address varchar(255),
partner varchar(255),
biography varchar(3000),
children int,
photo varchar(3000),
avarage_salary int,
is_dead bit,
date_death varchar(10)
);
ALTER TABLE file_author ADD file_author_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('file_author', RESEED, 0);

CREATE TABLE type_contact
(
type_contact varchar(255)
);
ALTER TABLE type_contact ADD type_contact_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('type_contact', RESEED, 0);

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

CREATE TABLE author_file_connection
(
file_id int NOT NULL,
file_author_id int NOT NULL,
FOREIGN KEY (file_id) REFERENCES library_file(file_id),
FOREIGN KEY (file_author_id) REFERENCES file_author(file_author_id)
);
ALTER TABLE author_file_connection ADD author_file_connection_id INT IDENTITY PRIMARY KEY;
DBCC CHECKIDENT ('author_file_connection', RESEED, 0);



INSERT INTO file_type (type_file, is_downloadble, size, file_format, number_of_pages, has_illustrations, has_parts, how_many_parts, this_part, time_took_to_read) 
VALUES ('book', 'true', '250MB', 'PDF', 208, 'true', 'true', 4, 1, '50 hours'), 
('encyclopedia', 'true', '330MB', 'EPUB', 365, 'true', 'true', 13, 4, '145 hours'),
('book', 'true', '15MB', 'FB2', 45, 'true', 'false', 0, 0, '25 minutes'),
('book', 'true', '400MB', 'FB2', 423, 'true', 'true', 13, 2, '45 hours');

INSERT INTO file_language (file_language, is_translated, how_many_translated, who_translated) 
VALUES ('Ukrainian', 'true', 4, 'Vsevolod Nestajko'),
('English', 'true', 17, 'Jeremy Octins');

INSERT INTO file_category (file_category, age_rating, collection_name) 
VALUES ('Children Literature', 6, 'Unusual adventures in the forest school'), 
('Fantasy', 12, 'Middle-earth'),
('Detective', 13, null);

INSERT INTO library_file (file_name, is_available, year_published, file_description, is_free, price, how_many_times_read, rating, file_image, how_long_was_written, file_type_id, file_language_id, file_category_id) 
VALUES ('The sun in the middle of the night. Adventures in the web', 'true', 1981, '	Light, cheerful, full of good humor, the book tells about the funny, witty adventures of a bunny, a hedgehog and their classmates, students of a specialized musical forest school with a bear language of teaching. For children of junior and middle school age.', 'false', 300, 150000, 4.3, 'https://cdn.27.ua/799/ad/60/372064_9.jpeg', '1.5 years', 0, 0, 0), 
('The Silmarillion', 'false', 1977, '	The events described in The Silmarillion, as in J. R. R. Tolkiens other Middle-earth writings, were meant to have taken place at some time in Earths past. In keeping with this idea, The Silmarillion was supposedly translated from Bilbos three-volume Translations from the Elvish, which he wrote while at Rivendell. The book covers the history of the world, Arda, up to the Third Age, in its five sections.', 'true', null, 100500, 3.5, 'https://static.yakaboo.ua/media/cloudflare/product/webp/600x840/8/1/81vx-br7nil.jpg', '2.5 years', 1, 1, 1),
('The Hound of the Baskervilles', 'true', 1902, '	The Hound of the Baskervilles is the third of the four crime novels by British writer Arthur Conan Doyle featuring the detective Sherlock Holmes. Originally serialized in The Strand Magazine from August 1901 to April 1902, it is set in 1889 largely on Dartmoor in Devon in Englands West Country and tells the story of an attempted murder inspired by the legend of a fearful, diabolical hound of supernatural origin. Holmes and Watson investigate the case. This was the first appearance of Holmes since his apparent death in "The Final Problem", and the success of The Hound of the Baskervilles led to the characters eventual revival. 
	One of the most famous stories ever written, in 2003, the book was listed as number 128 out of 200 on the BBCs The Big Read poll of the UKs "best-loved novel". In 1999, a poll of "Sherlockians" ranked it as the best of the four Holmes novels.', 'true', null, 1051, 4.1, 'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Cover_%28Hound_of_Baskervilles%2C_1902%29.jpg/800px-Cover_%28Hound_of_Baskervilles%2C_1902%29.jpg', '30 days', 2, 1, 2),
('The Fellowship of the Ring', 'true', 1954, '	The Fellowship of the Ring is the first of three volumes of the epic novel The Lord of the Rings by the English author J. R. R. Tolkien. It is followed by The Two Towers and The Return of the King. The action takes place in the fictional universe of Middle-earth. The book was first published on July 29, 1954 in the United Kingdom.
	The volume consists of a foreword, in which the author discusses his writing of The Lord of the Rings, a prologue titled "Concerning Hobbits, and other matters", and the main narrative in Book I and Book II.', 'false', 250, 110000, 4.4, 'https://m.media-amazon.com/images/I/71GVMfGUk+L.jpg', '13 years', 3, 0, 1);

INSERT INTO file_author (file_author, date_of_birth, education, gender, address, partner, biography, children, photo, avarage_salary, is_dead, date_death) 
VALUES ('Vsevolod Nestajko', '30.01.1930', 'Phylologist', 'male', null, null, '	Vsevolod Zinoviyovych Nestayko is Ukrainian prose writer, a classic of modern Ukrainian childrens literature. He is best known for the story "Toreadors from Vasyukivka". His works are characterized by humanity, kindness and a bright outlook on life, as well as an extraordinary sense of humor, which affects your own from childhood and then does not leave you throughout your life.', 0, 'https://upload.wikimedia.org/wikipedia/uk/3/3f/%D0%9D%D0%B5%D1%81%D1%82%D0%B0%D0%B9%D0%BA%D0%BE_%D0%92%D1%81%D0%B5%D0%B2%D0%BE%D0%BB%D0%BE%D0%B4_%D0%97%D1%96%D0%BD%D0%BE%D0%B2%D1%96%D0%B9%D0%BE%D0%B2%D0%B8%D1%87.jpg', null, 'true', '16.08.2014'), 
('J. R. R. Tolkien', '03.01.1892', 'Proffesor', 'male', null, null, '	John Ronald Reuel Tolkien was an English writer and philologist. He was the author of the high fantasy works The Hobbit and The Lord of the Rings. 
	From 1925 to 1945, Tolkien was the Rawlinson and Bosworth Professor of Anglo-Saxon and a Fellow of Pembroke College, both at the University of Oxford. He then moved within the same university to become the Merton Professor of English Language and Literature and Fellow of Merton College, and held these positions from 1945 until his retirement in 1959. 
		Tolkien was a close friend of C. S. Lewis, a co-member of the informal literary discussion group The Inklings. He was appointed a Commander of the Order of the British Empire by Queen Elizabeth II on March 28, 1972. 
			After Tolkiens death, his son Christopher published a series of works based on his fathers extensive notes and unpublished manuscripts, including The Silmarillion. These, together with The Hobbit and The Lord of the Rings, form a connected body of tales, poems, fictional histories, invented languages, and literary essays about a fantasy world called Arda and, within it, Middle-earth. 
				Between 1951 and 1955, Tolkien applied the term legendarium to the larger part of these writings.', 1, 'https://cdn.britannica.com/65/66765-050-63A945A7/JRR-Tolkien.jpg', 100500, 'true', '02.09.1973'),
('Christopher Tolkien', '21.11.1924', 'Proffesor', 'male', null, null, '	Christopher John Reuel Tolkien was an English and naturalised French academic editor. The son of author and academic J. R. R. Tolkien, Christopher Tolkien edited much of his fathers posthumously published work, including The Silmarillion and the 12-volume (plus one volume of indexes) series The History of Middle-Earth. Tolkien also drew the original maps for his fathers The Lord of the Rings. Outside his fathers unfinished works, Christopher Tolkien edited three tales by Geoffrey Chaucer (with Nevill Coghill) and his fathers translation of Sir Gawain and the Green Knight.', 1, 'https://cdna.artstation.com/p/assets/images/images/025/645/816/4k/fran-fdez-christopher-tolkien.jpg?1586459214', 100500, 'true', '16.01.2020'),
('Arthur Conan Doyle', '22.05.1859', 'Physician', 'male', null, null, '	Sir Arthur Ignatius Conan Doyle was a British writer and physician. He created the character Sherlock Holmes in 1887 for A Study in Scarlet, the first of four novels and fifty-six short stories about Holmes and Dr. Watson. The Sherlock Holmes stories are milestones in the field of crime fiction.
	Doyle was a prolific writer; other than Holmes stories, his works include fantasy and science fiction stories about Professor Challenger, and humorous stories about the Napoleonic soldier Brigadier Gerard, as well as plays, romances, poetry, non-fiction, and historical novels. One of Doyles early short stories, "J. Habakuk Jephsons Statement" (1884), helped to popularise the mystery of the Mary Celeste.', 1, 'https://cdn.britannica.com/51/12251-050-D5F09630/Arthur-Conan-Doyle-detail-portrait-HL-Gates-1927.jpg', 100500, 'true', '07.07.1930');

INSERT INTO type_contact (type_contact) VALUES
('Phone Number'), ('Skype');

INSERT INTO contacts (contact_value, type_contact_id, file_author_id) VALUES
('+100345476889', 0, 1), ('hunter.writer', 1, 1);

INSERT INTO author_file_connection (file_id, file_author_id) VALUES
(0, 0), (1, 1), (1, 2), (2, 3), (3, 1);




DROP TABLE author_file_connection, contacts, file_author, file_category, file_language, file_type, library_file, type_contact;

SELECT *
FROM author_file_connection
INNER JOIN library_file ON library_file.file_id = author_file_connection.file_id
INNER JOIN file_author ON file_author.file_author_id = author_file_connection.file_author_id
INNER JOIN file_type ON library_file.file_type_id = file_type.file_type_id
INNER JOIN file_category ON library_file.file_category_id = file_category.file_category_id
INNER JOIN file_language ON library_file.file_language_id = file_language.file_language_id
WHERE library_file.rating > 4.0
ORDER BY library_file.rating DESC;