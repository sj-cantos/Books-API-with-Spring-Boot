package com.example.database.mappers;

public interface AuthorMapper<A,B> {
    B mapTo(A a);

    A mapFrom(B b);
}
