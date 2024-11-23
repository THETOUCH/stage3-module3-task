package com.mjc.school.service.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {
    private final BaseRepository<TagModel, Long> tagRepository;
    private final Validator validator;
    private ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

    public TagService(BaseRepository<TagModel, Long> tagRepository
    , Validator validator) {
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Override
    public List<TagDtoResponse> readAll() {
        return tagRepository.readAll().stream().map(modelMapper::modelToTagDto).toList();
    }

    @Override
    public TagDtoResponse readById(Long id) {
        validator.validateTagId(id);
        if (tagRepository.existById(id)) {
            TagModel tagModel = tagRepository.readById(id).get();
            return modelMapper.modelToTagDto(tagModel);
        } else {
            throw new NotFoundException(
                    String.format("Tag with id %s not found", id)
            );
        }
    }

    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        validator.validateTagDto(createRequest);
        TagModel tagModel = modelMapper.dtoToModel(createRequest);
        TagModel createdTag = tagRepository.create(tagModel);
        return modelMapper.modelToTagDto(createdTag);
    }

    @Override
    public TagDtoResponse update(TagDtoRequest dtoRequest) {
        validator.validateTagId(dtoRequest.id());
        validator.validateTagDto(dtoRequest);
        if (tagRepository.existById(dtoRequest.id())) {
            TagModel tagModel = modelMapper.dtoToModel(dtoRequest);
            TagModel updatedTag = tagRepository.update(tagModel);
            return modelMapper.modelToTagDto(updatedTag);
        } else {
            throw new NotFoundException(
                    String.format("Tag with id %s not found", dtoRequest.id())
            );
        }
    }

    @Override
    public boolean deleteById(Long id) {
        validator.validateTagId(id);
        if (tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        } else {
            throw new NotFoundException("Tag with id " + id + " not found");
        }
    }
}
