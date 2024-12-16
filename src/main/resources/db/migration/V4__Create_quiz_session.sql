CREATE TABLE quiz_session (
                              id SERIAL PRIMARY KEY,
                              quiz_time INTEGER NOT NULL,
                              quiz_id INTEGER NOT NULL,
                              score INTEGER NOT NULL DEFAULT 0,
                              current_question_id INTEGER NOT NULL DEFAULT -1,
                              current_question_start_time INTEGER NOT NULL DEFAULT 0,
                              CONSTRAINT fk_quiz_session_quiz FOREIGN KEY (quiz_id)
                                  REFERENCES quiz (id)
                                  ON DELETE CASCADE
);