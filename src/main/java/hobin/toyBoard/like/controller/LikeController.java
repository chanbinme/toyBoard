package hobin.toyBoard.like.controller;

import hobin.toyBoard.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity postLike(@RequestParam @Positive Long memberId,
                                   @RequestParam @Positive Long boardId) {
        likeService.createLike(memberId, boardId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{like-id}")
    public ResponseEntity deleteLike(@PathVariable @Positive Long likeId) {
        likeService.deleteLike(likeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
