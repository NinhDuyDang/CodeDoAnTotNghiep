package com.example.doan_code_javaweb.service;

import com.example.doan_code_javaweb.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface BookService {
    List<Book> getListBook(String listBookId);
    Book createBook(Book book);

}
