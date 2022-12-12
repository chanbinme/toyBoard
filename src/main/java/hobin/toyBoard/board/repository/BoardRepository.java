package hobin.toyBoard.board.repository;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByMemberMemberId(Long memberId);
}
