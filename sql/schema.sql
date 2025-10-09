-- TODO: is this needed for PostgreSQL?
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY,
    email TEXT,
    password TEXT,
    name TEXT,
    is_admin BOOLEAN DEFAULT 0
);

CREATE TABLE IF NOT EXISTS authors (
    author_id INTEGER PRIMARY KEY,
    name TEXT -- TODO: figure out how to have first middle and last
    -- TODO: add relations between authors and books
);

-- TODO: How can multiple admins be the contact for something?
CREATE TABLE IF NOT EXISTS books (
    book_id INTEGER PRIMARY KEY,
    title TEXT,
    edition TEXT,
    condition TEXT,
    additional_info TEXT,
    checked_out BOOLEAN DEFAULT 0,
    FOREIGN KEY (contact) REFERENCES users(user_id),
    -- Book can be checked out only by one user at a time
    FOREIGN KEY (checked_out_by) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS equipment (
    equipment_id INTEGER PRIMARY KEY,
    name TEXT,
    class_requirement TEXT,
    checked_out BOOLEAN DEFAULT 0,
    additional_info TEXT,
    FOREIGN KEY (contact) REFERENCES users(user_id),
    -- Equipment can be checked out only by one user at a time
    FOREIGN KEY (checked_out_by) REFERENCES users(user_id)
)