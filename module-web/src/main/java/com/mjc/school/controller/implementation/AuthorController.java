package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService;
    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService;

    public AuthorController(BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> authorService,
                            BaseService<NewsDtoRequest, NewsDtoResponse, Long> newsService) {
        this.authorService = authorService;
        this.newsService = newsService;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return authorService.readAll();
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        return authorService.readById(id);
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        return authorService.create(createRequest);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        return authorService.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return authorService.deleteById(id);
    }

    public AuthorDtoResponse readByNewsId(Long id) {
        NewsDtoResponse newsDtoResponse = newsService.readById(id);
        return authorService.readById(newsDtoResponse.authorId());
    }
}
