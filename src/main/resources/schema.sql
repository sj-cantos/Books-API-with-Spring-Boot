DROP TABLE IF EXISTS "authors";
DROP TABLE IF EXISTS "books";

CREATE TABLE "authors" (
    "id" bigint DEFAULT nextval('author_id_seq') NOT NULL,
    "name" text,
    "age" integer,
    CONSTRAINT "author_pkey" PRIMARY KEY ("id")
);

CREATE TABLE "books"(
  "isbn" text NOT NULL,
   "title" text,
   "author_id" bigint,
   CONSTRAINT "books_pkey" PRIMARY KEY ("isbn"),
   CONSTRAINT "fk_author" FOREIGN KEY(author_id)
   REFERENCES authors(id)

);

