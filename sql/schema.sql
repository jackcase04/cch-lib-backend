-- TODO: Add on delete rules for foreign keys

DROP TABLE IF EXISTS request CASCADE;
DROP TABLE IF EXISTS write CASCADE;
DROP TABLE IF EXISTS manage_equipment CASCADE;
DROP TABLE IF EXISTS manage_book CASCADE;
DROP TABLE IF EXISTS equipment CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS users CASCADE;

DROP TYPE IF EXISTS request_type;
DROP TYPE IF EXISTS medium;

CREATE TYPE medium AS enum ('physical', 'pdf');

CREATE TYPE request_type AS enum ('checkout', 'return');

CREATE TABLE users (
    user_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(80) NOT NULL,
    f_name VARCHAR(80),
    m_init VARCHAR(80),
    l_name VARCHAR(80),
    is_admin BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE author (
    author_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    f_name VARCHAR(80),
    m_init VARCHAR(80),
    l_name VARCHAR(80)
);

CREATE TABLE book (
    book_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(80) NOT NULL,
    course VARCHAR(80),
    book_edition VARCHAR(80),
    condition medium NOT NULL,
    isbn INTEGER,
    additional_info VARCHAR(80),
    checked_out BOOLEAN NOT NULL DEFAULT FALSE,
    pdf_id INTEGER,
    contact INTEGER NOT NULL REFERENCES users(user_id),
    -- Book can be checked out only by one users at a time
    checked_out_by INTEGER REFERENCES users(user_id)
);

CREATE TABLE equipment (
    equipment_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    equipment_name VARCHAR(80) NOT NULL,
    class_requirement VARCHAR(80),
    checked_out BOOLEAN DEFAULT FALSE,
    additional_info VARCHAR(80),
    contact INTEGER NOT NULL REFERENCES users(user_id),
    -- Equipment can be checked out only by one user at a time
    checked_out_by INTEGER REFERENCES users(user_id)
);

CREATE TABLE write (
    author_id INTEGER NOT NULL REFERENCES author(author_id),
    book_id INTEGER NOT NULL REFERENCES book(book_id)
);

CREATE TABLE request (
    book_id INTEGER REFERENCES book(book_id),
    equipment_id INTEGER REFERENCES equipment(equipment_id),
    -- TODO: Add constraint to ensure only 1 resource can be refrenced
    user_id INTEGER NOT NULL REFERENCES users(user_id),
    time_requested TIMESTAMP NOT NULL,
    approved BOOLEAN NOT NULL DEFAULT FALSE,
    specific_type request_type NOT NULL
);