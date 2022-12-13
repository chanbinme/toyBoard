package hobin.toyBoard.comment.repository;

import hobin.toyBoard.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMemberMemberId(Long memberId, Pageable pageable);

    Page<Comment> findAllByBoardBoardId(Long boardId, Pageable pageable);
}
