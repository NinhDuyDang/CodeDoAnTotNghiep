package com.example.doan_code_javaweb.repository;

import com.example.doan_code_javaweb.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM book WHERE book_id IN :x ORDER BY FIND_IN_SET(book_id,:y)", nativeQuery = true)
    List<Book> getListBook(@Param("x") List<String> listBook,
                           @Param("y") String y);
}
