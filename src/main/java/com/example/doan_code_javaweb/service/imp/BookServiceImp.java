package com.example.doan_code_javaweb.service.imp;

import com.example.doan_code_javaweb.entity.Book;
import com.example.doan_code_javaweb.repository.BookRepository;
import com.example.doan_code_javaweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepository bookRepository;


    @Override
    public List<Book> getListBook(String listBookId) {
        List<String> listBook = Arrays.asList(listBookId.split("-"));
        String str = listBookId.replace("-", ",");

        List<Book> listBookRes = bookRepository.getListBook(listBook, str);
        System.out.println("so luong book chua tuong tac: " + listBook.size());
        System.out.println(str);
        return listBookRes;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}
