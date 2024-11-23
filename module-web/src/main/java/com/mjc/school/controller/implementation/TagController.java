package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TagController implements BaseController<TagDtoRequest, TagDtoResponse, Long> {
    private final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService;
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> tagService,
                         BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
        this.tagService = tagService;
        this.newsService = newsService;
    }
    @Override
    public List<TagDtoResponse> readAll() {
        return tagService.readAll();
    }

    @Override
    public TagDtoResponse readById(Long id) {
        return tagService.readById(id);
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return tagService.deleteById(id);
    }

    public Set<TagDtoResponse> getTagsByNewsId(Long newsId){
        NewsDtoResponse newsDtoResponse = newsService.readById(newsId);
        return newsDtoResponse.tagsId().stream().map(tagService::readById).collect(Collectors.toSet());
    }
}
