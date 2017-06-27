--
-- File generated with SQLiteStudio v3.1.1 on Mon Jun 26 23:49:33 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: COMMENT
CREATE TABLE COMMENT (id INTEGER PRIMARY KEY AUTOINCREMENT, post_id INTEGER REFERENCES POST (id) NOT NULL, author_id INTEGER REFERENCES USER (id), date DATE, parent_id INTEGER REFERENCES COMMENT (id), text TEXT, like_count INTEGER, dislike_count INTEGER, changed BOOLEAN);

-- Table: LIKE_COMMENT
CREATE TABLE LIKE_COMMENT (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER REFERENCES USER (id) NOT NULL, comment_id INTEGER REFERENCES COMMENT (id) NOT NULL, liked BOOLEAN NOT NULL);

-- Table: LIKE_POST
CREATE TABLE LIKE_POST (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER REFERENCES USER (id) NOT NULL, post_id INTEGER REFERENCES POST (id) NOT NULL, liked BOOLEAN NOT NULL);

-- Table: MESSAGE
CREATE TABLE MESSAGE (id INTEGER PRIMARY KEY AUTOINCREMENT, sender_id INTEGER REFERENCES USER (id) NOT NULL, reciever_id INTEGER REFERENCES USER (id) NOT NULL, text TEXT, is_read BOOLEAN);

-- Table: POST
CREATE TABLE POST (id INTEGER PRIMARY KEY AUTOINCREMENT, subforum_id INTEGER REFERENCES SUBFORUM (id), title STRING (70), type STRING (10) NOT NULL, author_id INTEGER REFERENCES USER (id), content_picture BLOB, content_text TEXT, content_link STRING (255), date DATE, like_count INTEGER, dislike_count INTEGER);

-- Table: SUBFORUM
CREATE TABLE SUBFORUM (id INTEGER PRIMARY KEY AUTOINCREMENT, name STRING (30) UNIQUE NOT NULL, description STRING (200), icon BLOB, rules TEXT, main_moderator INTEGER REFERENCES USER (id));

-- Table: SUBFORUM_MODERATOR
CREATE TABLE SUBFORUM_MODERATOR (id INTEGER PRIMARY KEY AUTOINCREMENT, moderator_id INTEGER REFERENCES USER (id) NOT NULL, subforum_id INTEGER REFERENCES SUBFORUM (id) NOT NULL);

-- Table: USER
CREATE TABLE USER (id INTEGER PRIMARY KEY AUTOINCREMENT, username STRING (30) UNIQUE NOT NULL, name STRING (30) NOT NULL, surname STRING (30), password STRING (30), role STRING (10) DEFAULT user, phone_number STRING (20), email STRING (50), date DATE);
INSERT INTO USER (id, username, name, surname, password, role, phone_number, email, date) VALUES (1, 'baki123', 'Bane', 'Miletic', 'bakica', 'user', 62548764, 'baki@gmail.com', '21-07-2017');

-- Table: USER_SUBFORUM
CREATE TABLE USER_SUBFORUM (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER REFERENCES USER (id) NOT NULL, subforum_id INTEGER REFERENCES SUBFORUM (id) NOT NULL);

-- Index: idx_moderator_subforum
CREATE UNIQUE INDEX idx_moderator_subforum ON SUBFORUM_MODERATOR (moderator_id, subforum_id);

-- Index: idx_subforum_title
CREATE UNIQUE INDEX idx_subforum_title ON POST (subforum_id, title);

-- Index: idx_user_comment
CREATE UNIQUE INDEX idx_user_comment ON LIKE_COMMENT (user_id, comment_id);

-- Index: idx_user_post
CREATE UNIQUE INDEX idx_user_post ON LIKE_POST (user_id, post_id);

-- Index: idx_user_subforum
CREATE UNIQUE INDEX idx_user_subforum ON USER_SUBFORUM (user_id, subforum_id);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
