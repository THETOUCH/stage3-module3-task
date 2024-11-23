package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ModelMapper {
    List<NewsDtoResponse> modelListToDtoList(List<NewsModel> newsModelList);

    @Mappings({
            @Mapping(target = "author.id", source = "authorId"),
            @Mapping(target = "tags", source = "tagsId", qualifiedByName = "tagModelToTagId")
    })
    NewsDtoResponse modelToDto(NewsModel newsModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true),
            @Mapping(target = "author.id", source = "authorId"),
            @Mapping(target = "tags", source = "tagsId", qualifiedByName = "tagModelFromTagId")
    })
    NewsModel dtoToModel(NewsDtoRequest newsModelRequest);

    @Named("tagModelFromTagId")
    default List<TagModel> tagModelFromTagId(List<Long> tagIdList) {
        if (tagIdList == null) return new ArrayList<>();
        return tagIdList.stream()
                .map(id -> {
                    TagModel tag = new TagModel();
                    tag.setId(id);
                    return tag;
                })
                .collect(Collectors.toList());
    }

    @Named("tagModelToTagId")
    default List<Long> tagModelToTagId(List<TagModel> tags) {
        if (tags == null) return new ArrayList<>();
        return tags.stream()
                .map(TagModel::getId)
                .collect(Collectors.toList());
    }

    List<AuthorDtoResponse> modelListToAuthorDtoList(List<AuthorModel> newsModelList);

    AuthorDtoResponse modelToAuthorDto(AuthorModel newsModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "news", ignore = true)
    })
    AuthorModel dtoToModel(AuthorDtoRequest authorModelRequest);

    List<TagDtoResponse> modelListToTagDtoList(List<TagModel> tagModelList);

    TagDtoResponse modelToTagDto(TagModel tagModel);

    @Mapping(target = "news", ignore = true)
    TagModel dtoToModel(TagDtoRequest tagModelRequest);
}
