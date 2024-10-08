package com.example.doan_code_javaweb.controller;
import com.example.doan_code_javaweb.config.MyUserDetails;
import com.example.doan_code_javaweb.dto.UserDto;
import com.example.doan_code_javaweb.entity.Book;
import com.example.doan_code_javaweb.entity.InterativeBook;
import com.example.doan_code_javaweb.repository.BookRepository;
import com.example.doan_code_javaweb.repository.InterativeBookRepository;
import com.example.doan_code_javaweb.repository.PublisherBookRepository;
import com.example.doan_code_javaweb.repository.PublisherRepository;
import com.example.doan_code_javaweb.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Controller
public class BookController {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private PublisherBookRepository publisherBookRepository;
    @Autowired
    private InterativeBookRepository interativeBookRepository;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("viewBookDetail")
    String getBookDetail(ModelMap modelMap, @RequestParam("bookId") Integer bookId){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = myUserDetails.getUser().getUserId();
        modelMap.addAttribute("userId", userId);
        InterativeBook interativeBook = new InterativeBook();
        interativeBook.setBookId(bookId);
        interativeBook.setUserId(userId);
        interativeBook.setRating(1);
        System.out.println(interativeBookRepository.save(interativeBook));
        List<String> summaryList = new ArrayList<>();
        String summary = "";
        String publisher = publisherRepository.getPublisher(bookId);
        try {
            String url = "https://www.goodreads.com/book/show/"+bookId;
            Document doc = Jsoup.connect(url).get();
            Elements element = doc.select("span");
            for (int i = 0; i < element.size(); i++) {
                if(i>27 && i<35)
                    summaryList.add(element.get(i).text());
                if(i>40)
                    break;
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
        for (int i=0; i < summaryList.size(); i++){
            if(summaryList.get(i).length()>summary.length())
                summary = summaryList.get(i);
        }

        System.out.println(summary);
        Book book = bookRepository.getById(bookId);
        modelMap.addAttribute("titleBook", book.getTitle());
        modelMap.addAttribute("imgBook", book.getImageUrl());
        modelMap.addAttribute("summary", summary);
        modelMap.addAttribute("publicationYear", book.getOriginalPublicationYear().substring(0, book.getOriginalPublicationYear().length()-2));
        modelMap.addAttribute("ratingAvg", book.getAverageRating());
        modelMap.addAttribute("publisher", publisher);
        modelMap.addAttribute("author", book.getAuthors());
        System.out.println(book.getOriginalPublicationYear().substring(0, book.getOriginalPublicationYear().length()-2));
        List<InterativeBook> interativeBooks = interativeBookRepository.findDistinctByBookId(bookId);
        List<Book> bookAnathorRecommendList = new ArrayList<>();
        for (int i=0; i<6; i++){
            System.out.println(interativeBooks.get(i).getUserId());
            System.out.println(i);
            UserDto userDto = new UserDto();
            userDto.setUserId(interativeBooks.get(i).getUserId());
            String grades = restTemplate.postForObject("/predict", userDto, String.class);
            List<Book> listBook = bookService.getListBook(grades);
            List<Book> bookAnathorRecommend = listBook.stream().limit(1).collect(Collectors.toList());
            bookAnathorRecommendList.add(bookAnathorRecommend.get(0));
        }
        modelMap.addAttribute("bookAnathorRecommendList", bookAnathorRecommendList);
        return "viewBookDetail";
    }

}
