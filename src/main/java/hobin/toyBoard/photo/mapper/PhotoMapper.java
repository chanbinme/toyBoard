package hobin.toyBoard.photo.mapper;

import hobin.toyBoard.photo.dto.PhotoDto;
import hobin.toyBoard.photo.entity.Photo;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PhotoMapper {
    Photo photoDtoToPhoto(PhotoDto photoPostDto);
    PhotoDto PhotoToPhotoDto(Photo photo);
}
