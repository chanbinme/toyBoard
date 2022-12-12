package hobin.toyBoard.photo.repository;

import hobin.toyBoard.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByBoardBoardId(Long boardId);
}
