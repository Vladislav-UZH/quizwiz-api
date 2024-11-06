package com.example.kahoot;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

class TestController {
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateQuiz_AdminAccess() throws Exception {
        // ваш тест-кейс
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testCreateQuiz_NonAdminAccess() throws Exception {
        // тест, который ожидает 403
    }
}
