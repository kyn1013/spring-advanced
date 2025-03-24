package org.example.expert.domain.comment.repository;

import org.assertj.core.api.Assertions;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;
    
    @Test
    public void 댓글이_정상적으로_저장된다() {
        // given
        User user = new User("yn1113@naver.com", "password", UserRole.USER);
        Todo todo = new Todo("title", "contents", "sunny", user);
        Comment comment = new Comment("comments", user, todo);
        userRepository.save(user);
        todoRepository.save(todo);

        // when
        Comment savedComment = commentRepository.save(comment);
    
        // then
        assertThat(comment).isSameAs(savedComment);
        assertThat(comment.getId()).isSameAs(savedComment.getId());
        assertThat(savedComment.getId()).isNotNull();
        assertThat(commentRepository.count()).isEqualTo(1);
    }

}