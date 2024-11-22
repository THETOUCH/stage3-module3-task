package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final BaseRepository<NewsModel, Long> newsRepository;
    private final Validator validator;
    private ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public NewsService(BaseRepository<NewsModel, Long> newsRepository,
                       Validator validator) {
        this.newsRepository = newsRepository;
        this.validator = validator;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return mapper.modelListToDtoList(newsRepository.readAll());
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
        NewsModel model = mapper.dtoToModel(dtoRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreateDate(date);
        model.setLastUpdatedDate(date);
        NewsModel newsModel = newsRepository.create(model);
        return mapper.modelToDto(newsModel);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest dtoRequest) {
        validator.validateNewsId(dtoRequest.id());
        validator.validateNewsDto(dtoRequest);
        if (newsRepository.existById(dtoRequest.id())) {
            NewsModel model = mapper.dtoToModel(dtoRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdatedDate(date);
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
}
