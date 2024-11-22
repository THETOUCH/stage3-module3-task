package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface ModelMapper {
    List<NewsDtoResponse> modelListToDtoList(List<NewsModel> newsModelList);

    NewsDtoResponse modelToDto(NewsModel newsModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true)
    })
    NewsModel dtoToModel(NewsDtoRequest newsModelRequest);

    List<AuthorDtoResponse> modelListToAuthorDtoList(List<AuthorModel> newsModelList);

    AuthorDtoResponse modelToAuthorDto(AuthorModel newsModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)
    })
    AuthorModel dtoToModel(AuthorDtoRequest authorModelRequest);
}
