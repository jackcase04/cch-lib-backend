-- TODO: Add on delete rules for foreign keys

DROP TYPE IF EXISTS medium;
CREATE TYPE medium AS enum ('physical', 'pdf');

DROP TYPE IF EXISTS request_type;
CREATE TYPE request_type AS enum ('checkout', 'return');

DROP TABLE IF EXISTS user CASCADE;
CREATE TABLE user (
    user_id INTEGER NOT NULL PRIMARY KEY,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(80) NOT NULL,
    name VARCHAR(80) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS author CASCADE;
CREATE TABLE author (
    author_id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(80) NOT NULL
    -- TODO: figure out how to have first middle and last
);

DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book (
    book_id INTEGER NOT NULL PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    course VARCHAR(80),
    book_edition VARCHAR(80),
    condition medium NOT NULL,
    isbn INTEGER,
    additional_info VARCHAR(80),
    checked_out BOOLEAN NOT NULL DEFAULT 0,
    pdf_id INTEGER, 
    -- Book can be checked out only by one user at a time
    checked_out_by INTEGER REFERENCES user(user_id)
);

DROP TABLE IF EXISTS equipment CASCADE;
CREATE TABLE equipment (
    equipment_id INTEGER NOT NULL PRIMARY KEY,
    equipment_name VARCHAR(80) NOT NULL,
    class_requirement VARCHAR(80),
    checked_out BOOLEAN DEFAULT 0,
    additional_info VARCHAR(80),
    contact INTEGER NOT NULL REFERENCES user(user_id),
    -- Equipment can be checked out only by one user at a time
    checked_out_by INTEGER REFERENCES user(user_id)
);

-- Composite tables
-- TODO: Do we need the next 2?
DROP TABLE IF EXISTS manage_book CASCADE;
CREATE TABLE manage_book (
    user_id INTEGER NOT NULL REFERENCES user(user_id),
    book_id INTEGER NOT NULL REFERENCES book(book_id)
);

DROP TABLE IF EXISTS manage_equipment CASCADE;
CREATE TABLE manage_equipment (
    user_id INTEGER NOT NULL REFERENCES user(user_id),
    equipment_id INTEGER NOT NULL REFERENCES equipment(equipment_id)
);

DROP TABLE IF EXISTS write CASCADE;
CREATE TABLE write (
    author_id INTEGER NOT NULL REFERENCES author(author_id),
    book_id INTEGER NOT NULL REFERENCES book(book_id)
);

DROP TABLE IF EXISTS request CASCADE;
CREATE TABLE request (
    book_id INTEGER REFERENCES book(book_id),
    equipment_id INTEGER REFERENCES equipment(equipment_id),
    -- TODO: Add constraint to ensure only 1 resource can be refrenced
    user_id INTEGER NOT NULL REFERENCES user(user_id),
    time_requested TIMESTAMP NOT NULL,
    approved BOOLEAN NOT NULL DEFAULT 0,
    specific_type request_type NOT NULL
);