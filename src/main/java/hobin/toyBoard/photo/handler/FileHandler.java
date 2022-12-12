package hobin.toyBoard.photo.handler;

import hobin.toyBoard.board.entity.Board;
import hobin.toyBoard.photo.dto.PhotoDto;
import hobin.toyBoard.photo.entity.Photo;
import hobin.toyBoard.photo.mapper.PhotoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class FileHandler {

    private final PhotoMapper mapper;

    public List<Photo> parseFileInfo(Board board, List<MultipartFile> multipartFiles) throws Exception {
        // 반환할 파일 리스트
        List<Photo> fileList = new ArrayList<>();

        // 전달되어 온 파일이 존재할 경우
        if (!CollectionUtils.isEmpty(multipartFiles)) {
            // 파일명을 업로드 한 날짜로 변환하여 저장
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉토리에 저장을 위한 절대 경로 설정
            // 경로 구분자 File.separator 사용
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일을 저장할 세부 경로 설정
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // 디렉토리가 존재하지 않는 경우
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdir();

                // 디렉토리 생성에 실패했을 경우
                if (!wasSuccessful) {
                    System.out.println("file: was not successful");
                }
            }

            // 다중 파일 처리
            for (MultipartFile multipartFile : multipartFiles) {

                // 파일 확장자 추출
                String originalFileExtenstion;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if (contentType.contains("image/jpeg"))
                        originalFileExtenstion = ".jpg";
                    else if (contentType.contains("image/png")) {
                        originalFileExtenstion = ".png";
                    } else break;  // 다른 확장자일 경우 처리 X
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtenstion;

                // 파일 DTO 생성
                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                // 파일 DTO 이용하여 Photo 엔티티 생성
                Photo photo = mapper.photoDtoToPhoto(photoDto);

//                // 게시글에 존재하지 않으면 -> 게시글에 사진 정보 저장
//                if (board.getBoardId() != null) {
//                    photo.setBoard(board);
//                }

                // 생성 후 리스트에 추가
                fileList.add(photo);

                // 업로드 한 파일 데이터를 파일에 저장
                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return fileList;
    }
}
