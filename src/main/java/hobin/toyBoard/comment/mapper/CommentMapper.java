package hobin.toyBoard.comment.mapper;

import hobin.toyBoard.comment.dto.CommentDto;
import hobin.toyBoard.comment.entity.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment CommentPostDtoToComment(CommentDto.Post commentPostDto);

    Comment CommentPatchDtoToComment(CommentDto.Patch commentPatchDto);

    CommentDto.Response CommentToCommentResponseDto(Comment comment);

    List<CommentDto.Response> CommentsToCommentResponseDtos(List<Comment> comments);
}
