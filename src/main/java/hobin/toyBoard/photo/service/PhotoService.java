package hobin.toyBoard.photo.service;


import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
import hobin.toyBoard.photo.entity.Photo;
import hobin.toyBoard.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Photo findPhoto(Long photoId) {
        return photoRepository.findById(photoId).orElseThrow(()
                -> new BussinessLogicException(ExceptionCode.PHOTO_NOT_FOUND));
    }

    public List<Photo> findAllByBoard(Long boardId) {
        return photoRepository.findAllByBoardBoardId(boardId);
    }

    public void deletePhoto(Long photoId) {
        photoRepository.deleteById(photoId);
    }
}
