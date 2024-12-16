CREATE TABLE quiz_session (
                              id SERIAL PRIMARY KEY,
                              quiz_time INTEGER NOT NULL,            -- Загальний час на тест
                              quiz_stack_id INTEGER NOT NULL,        -- Айдішник на стек питань
                              score INTEGER NOT NULL DEFAULT 0,      -- Кількість балів, дефолт 0
                              current_question_id INTEGER,           -- Айдішник поточного питання, може бути NULL якщо немає активного питання
                              current_question_start_time INTEGER,   -- Час початку відповіді на поточне питання в мілісекундах (epoch time)
                              CONSTRAINT fk_quiz_session_quiz_stack FOREIGN KEY (quiz_stack_id)
                                  REFERENCES quiz (id)
                                  ON DELETE CASCADE,
                              CONSTRAINT fk_quiz_session_current_question FOREIGN KEY (current_question_id)
                                  REFERENCES question (id)
                                  ON DELETE SET NULL
);
