package com.example.database.mappers.impl;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.mappers.AuthorMapper;
import org.modelmapper.ModelMapper;

public class AuhorMapperImpl implements AuthorMapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;
    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity,AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
