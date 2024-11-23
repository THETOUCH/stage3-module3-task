package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;
    private final BaseService<TagDtoRequest, TagDtoResponse, Long> tagService;

    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService,
                          BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService,
                          BaseService<TagDtoRequest, TagDtoResponse, Long> tagService) {
        this.newsService = newsService;
        this.authorService = authorService;
        this.tagService = tagService;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    public NewsDtoResponse readById(Long id) {
        return newsService.readById(id);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return newsService.deleteById(id);
    }

}
