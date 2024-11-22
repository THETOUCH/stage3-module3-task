package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final Validator validator;
    private ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public AuthorService(BaseRepository<AuthorModel, Long>  authorRepository, Validator validator) {
        this.authorRepository = authorRepository;
        this.validator = validator;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return mapper.modelListToAuthorDtoList(authorRepository.readAll());
    }

    @Override
    public AuthorDtoResponse readById(Long id) {
        validator.validateAuthorId(id); //123
        if (authorRepository.existById(id)) {
            AuthorModel authorModel = authorRepository.readById(id).get();
            return mapper.modelToAuthorDto(authorModel);
        } else {
            throw new NotFoundException(
                    String.format(String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), id) //123
            );
        }
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest dtoRequest) {
        validator.validateAuthorDto(dtoRequest);
        AuthorModel model = mapper.dtoToModel(dtoRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreateDate(date);
        model.setLastUpdateDate(date);
        AuthorModel authorModel = authorRepository.create(model);
        return mapper.modelToAuthorDto(authorModel);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest dtoRequest) {
        validator.validateAuthorId(dtoRequest.id());
        validator.validateAuthorDto(dtoRequest);
        if (authorRepository.existById(dtoRequest.id())) {
            AuthorModel model = mapper.dtoToModel(dtoRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdateDate(date);
            AuthorModel authorModel = authorRepository.update(model);
            return mapper.modelToAuthorDto(authorModel);
        } else {
            throw new NotFoundException(
                    String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), dtoRequest.id())
            );
        }
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateAuthorId(id);
        if (authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }
}
