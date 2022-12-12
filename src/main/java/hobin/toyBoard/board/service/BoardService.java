package hobin.toyBoard.board.service;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.board.repository.BoardRepository;
import hobin.toyBoard.exception.BussinessLogicException;
import hobin.toyBoard.exception.ExceptionCode;
import hobin.toyBoard.member.service.MemberService;
import hobin.toyBoard.photo.dto.PhotoDto;
import hobin.toyBoard.photo.entity.Photo;
import hobin.toyBoard.photo.handler.FileHandler;
import hobin.toyBoard.photo.repository.PhotoRepository;
import hobin.toyBoard.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final PhotoRepository photoRepository;
    private final MemberService memberService;
    private final FileHandler fileHandler;
    private final PhotoService photoService;

    @Transactional
    public Board createBoard(@Positive Long memberId, Board board, List<MultipartFile> files) throws Exception {
        board.addMember(memberService.findMember(memberId));
        List<Photo> photos = fileHandler.parseFileInfo(board, files);

        // 파일이 존재할 때에만 처리
        if (!photos.isEmpty()) {
            for (Photo photo : photos) {
                // 파일을 DB에 저장
                board.addPhoto(photoRepository.save(photo));
            }
        }
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Board board, List<MultipartFile> files) throws Exception {
        List<Photo> photoList = fileHandler.parseFileInfo(board, photoUpdate(board.getBoardId(), files));

        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) photoRepository.save(photo);
        }

        Board findBoard = findVerifiedBoard(board.getBoardId());
        findBoard.changeBoard(board.getTitle(), board.getContent());

        return boardRepository.save(findBoard);
    }

    public Board findBoard(Long boardId) {
        return findVerifiedBoard(boardId);
    }

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page - 1, size, Sort.by("boardId").descending()));
    }

    public List<Board> findAllByMember(Long memberId) {
        memberService.findVerifiedMember(memberId);
        List<Board> allByMemberMemberId = boardRepository.findAllByMemberMemberId(memberId);
        System.out.println(allByMemberMemberId.toString());
        return allByMemberMemberId;
    }

    @Transactional
    public void deleteBoard(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());
        boardRepository.delete(findBoard);
    }

    public Board findVerifiedBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BussinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public List<MultipartFile> photoUpdate(Long boardId, List<MultipartFile> files) {
        // DB에 저장되어있는 파일 불러오기
        List<Photo> findPhotos = photoService.findAllByBoard(boardId);
        // 새롭게 전달되어온 파일들의 목록을 저장할 List
        List<MultipartFile> addFileList = new ArrayList<>();

        // DB에 파일이 존재하지 않고
        if (CollectionUtils.isEmpty(findPhotos)) {
            // 전달되어온 파일이 있다면 저장할 파일 목록에 추가
            if (!CollectionUtils.isEmpty(files)) {
                for (MultipartFile multipartFile : files) addFileList.add(multipartFile);
            }
        } else {    // DB에 파일이 한 장 이상 존재하고
            // 전달되어온 파일이 없다면 db에 있던 파일 삭제
            if (CollectionUtils.isEmpty(files)) {
                for (Photo findPhoto : findPhotos) {
                    photoService.deletePhoto(findPhoto.getPhotoId());
                }
            } else {    // 전달되어 온 파일이 한 장 이상 존재한다면
                // DB에 저장되어있는 파일 원본명 정리
                List<String> dbFileOriginNames = new ArrayList<>();

                for (Photo findPhoto : findPhotos) {
                    Photo photo = photoService.findPhoto(findPhoto.getPhotoId());

                    // DB에 저장된 파일들 중 전달된 파일이 존재하지 않으면
                    if (!files.contains(photo.getOrigFileName()))
                        photoService.deletePhoto(findPhoto.getPhotoId());   // 삭제
                    else dbFileOriginNames.add(photo.getOrigFileName());    // 있으면 다시 저장
                }

                // 전달 받은 파일 하나씩 검사
                for (MultipartFile multipartFile : files) {
                    // 파일의 원본명 얻어오기
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    // DB에 없는 파일이면 DB에 추가
                    if (!dbFileOriginNames.contains(multipartOrigName)) {
                        addFileList.add(multipartFile);
                    }
                }
            }
        }
        return addFileList;
    }
}
