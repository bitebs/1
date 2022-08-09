package org.itstep.helloworldspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookId(@PathVariable Integer id){
        return bookService.getBookById(id);
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book) throws IOException {
        bookService.addBook(book);
    }

    @PutMapping("/books/{id}")
    public void updateBook(@RequestBody Book book, @PathVariable Long id) throws IOException {
        book.setId(id);
        bookService.updateBook(book);
    }

    @DeleteMapping("/books/{id}")
    public void addBook(@PathVariable Integer id) throws IOException {
        bookService.deleteBook(id);
    }

    @GetMapping("/books/search")
    public List<Book> findBook(@RequestParam("title") String title) throws IOException {
       return bookService.findBook(title);
    }

    @GetMapping("/books/page")
    public List<Book> pageBook(@RequestParam("limit") Integer limit,
                               @RequestParam("offset") Integer offset) throws IOException {
        return bookService.getBookPage(limit, offset);
    }
}
