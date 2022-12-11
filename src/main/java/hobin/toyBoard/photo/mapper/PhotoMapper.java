package hobin.toyBoard.photo.mapper;

import hobin.toyBoard.photo.dto.PhotoDto;
import hobin.toyBoard.photo.entity.Photo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    Photo photoPostDtoToPhoto(PhotoDto.Post photoPostDto);
    PhotoDto.Response phtoToResponseDto(Photo photo);
}
