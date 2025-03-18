package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void Todo와_작성자를_함께_조회한다() {
        // given
        String email = "yn@abc.com";
        User user = new User(email, "password", UserRole.USER);
        userRepository.save(user);

        String weather = "맑음";

        Todo todo = new Todo(
                "할일 제목",
                "할일 내용",
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(todo);

        // when
        Todo foundTodo = todoRepository.findByIdWithUser(savedTodo.getId()).orElse(null);

        // then
        assertNotNull(foundTodo);
        assertEquals("할일 제목", foundTodo.getTitle());
        assertEquals("할일 내용", foundTodo.getContents());
        assertEquals("yn@abc.com", foundTodo.getUser().getEmail());
    }
}