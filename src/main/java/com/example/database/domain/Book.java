package com.example.database.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")

public class Book {
    @Id
    private String isbn;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;
    private String title;
}
