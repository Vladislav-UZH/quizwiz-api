CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Створення таблиці quiz_stack
-- Створення таблиці quiz
CREATE TABLE quiz (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL
);

-- Створення таблиці question
-- Кожне питання прив’язане до конкретного quiz через quiz_id
CREATE TABLE question (
                          id SERIAL PRIMARY KEY,
                          quiz_id INTEGER NOT NULL,
                          text VARCHAR(255) NOT NULL,
                          CONSTRAINT fk_question_quiz FOREIGN KEY (quiz_id)
                              REFERENCES quiz (id)
                              ON DELETE CASCADE
);

-- Створення таблиці options
-- Кожен варіант відповіді прив’язаний до питання через question_id
CREATE TABLE options (
                         id SERIAL PRIMARY KEY,
                         question_id INTEGER NOT NULL,
                         text VARCHAR(255) NOT NULL,
                         CONSTRAINT fk_options_question FOREIGN KEY (question_id)
                             REFERENCES question (id)
                             ON DELETE CASCADE
);
