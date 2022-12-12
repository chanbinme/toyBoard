package hobin.toyBoard.photo.controller;

import hobin.toyBoard.photo.mapper.PhotoMapper;
import hobin.toyBoard.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    private final PhotoMapper mapper;

    @GetMapping(value = "/{photo-id}",
                produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> findImage(@PathVariable("photo-id") @Positive Long photoId) throws IOException {
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photoService.findPhoto(photoId).getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
