package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final BaseRepository<TagModel, Long> tagRepository;
    private final Validator validator;
    private ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public NewsService(BaseRepository<NewsModel, Long> newsRepository,
                       Validator validator,
                       BaseRepository<AuthorModel, Long> authorRepository,
                       BaseRepository<TagModel, Long> tagRepository) {
        this.newsRepository = newsRepository;
        this.validator = validator;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return newsRepository.readAll().stream().map(mapper::modelToDto).toList();
    }

    @Override
    public NewsDtoResponse readById(Long newsId) {
        validator.validateNewsId(newsId);
        if (newsRepository.existById(newsId)) {
            NewsModel newsModel = newsRepository.readById(newsId).get();
            return mapper.modelToDto(newsModel);
        } else {
            throw new NotFoundException(
                    String.format(String.valueOf(NEWS_ID_DOES_NOT_EXIST.getMessage()), newsId)
            );
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest dtoRequest) {
        validator.validateNewsDto(dtoRequest);
        if (!authorRepository.existById(dtoRequest.authorId())) {
            throw new NotFoundException(
                    String.format(String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), dtoRequest.authorId())
            );
        }
        NewsModel model = mapper.dtoToModel(dtoRequest);
        NewsModel newsModel = newsRepository.create(model);
        return mapper.modelToDto(newsModel);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest dtoRequest) {
        validator.validateNewsId(dtoRequest.id());
        validator.validateNewsDto(dtoRequest);
        if (!authorRepository.existById(dtoRequest.authorId())) {
            throw new NotFoundException(
                    String.format(String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), dtoRequest.authorId())
            );
        }
        if (newsRepository.existById(dtoRequest.id())) {
            NewsModel model = mapper.dtoToModel(dtoRequest);
            NewsModel newsModel = newsRepository.update(model);
            return mapper.modelToDto(newsModel);
        } else {
            throw new NotFoundException(
                    String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), dtoRequest.id())
            );
        }
    }

    @Override
    public boolean deleteById(Long newsId) {
        validator.validateNewsId(newsId);
        if (newsRepository.existById(newsId)) {
            return newsRepository.deleteById(newsId);
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId));
        }
    }

    public AuthorDtoResponse getAuthorById(Long newsId) {
        {
            NewsModel news = newsRepository.readById(newsId)
                    .orElseThrow(() -> new EntityNotFoundException("News not found for id: " + newsId));
            AuthorModel result = authorRepository.readById(news.getAuthor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found for id: " + news.getAuthor().getId()));
            return mapper.modelToAuthorDto(result);
        }
    }

    public Set<TagDtoResponse> getTagsByNewsId(Long newsId){
        NewsDtoResponse newsDtoResponse = mapper.modelToDto(newsRepository.readById(newsId).get());

        Set<Long> tagIds = new HashSet<>(newsDtoResponse.tagsId());


        Set<TagDtoResponse> tags = tagIds.stream()
                .map(tagId -> tagRepository.readById(tagId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(tagModel -> mapper.modelToTagDto(tagModel))
                .collect(Collectors.toSet());

        return tags;
    }
}
