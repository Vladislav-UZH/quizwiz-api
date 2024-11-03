package com.example.kahoot;

import com.example.kahoot.model.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;

@SpringBootTest
class QuizgameApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testQuizTitle() {
		Quiz quiz = new Quiz("Sample Quiz", new ArrayList<>());
		assertEquals("Sample Quiz", quiz.getTitle());
	}

}
