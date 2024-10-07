package com.example.doan_code_javaweb.repository;

import com.example.doan_code_javaweb.entity.InterativeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface InterativeBookRepository extends JpaRepository<InterativeBook, Integer> {
    List<InterativeBook> findDistinctByBookId(Integer bookId);
}
