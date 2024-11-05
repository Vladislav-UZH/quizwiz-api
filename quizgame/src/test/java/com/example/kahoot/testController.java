package com.example.kahoot;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

public class testController {
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateQuiz_AdminAccess() throws Exception {
        // ваш тест-кейс
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateQuiz_NonAdminAccess() throws Exception {
        // тест, который ожидает 403
    }
}
