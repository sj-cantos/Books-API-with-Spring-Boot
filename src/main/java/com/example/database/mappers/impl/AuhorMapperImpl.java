package com.example.database.mappers.impl;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.mappers.AuthorMapper;

public class AuhorMapperImpl implements AuthorMapper<AuthorEntity, AuthorDto> {
    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return null;
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return null;
    }
}
