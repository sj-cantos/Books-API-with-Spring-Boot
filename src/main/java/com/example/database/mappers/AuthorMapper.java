package com.example.database.mappers;

public interface AuthorMapper<A,B> {
    A mapTo(B b);

    B mapFrom(A a);
}
