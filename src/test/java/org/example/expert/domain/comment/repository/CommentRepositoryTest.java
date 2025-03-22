package org.example.expert.domain.comment.repository;

import org.example.expert.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    
    @Test
    public void 댓글이_정상적으로_저장된다() {
        // given
//        User user = new User("test@naver.com", )
        // when
    
        // then
    }

}