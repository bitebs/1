package org.itstep.helloworldspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {
    //private static final String fileName = "src/main/resources/static/book100.json";
    private static String fileName = null;
    private ObjectMapper mapper = new ObjectMapper(); // create object mapper instance
    @Getter
    private List<Book> books = null;

    public BookRepository(String fileName){
        this.fileName = fileName;
    }

    //Read books from file
    public void readFile(){
        try {
            // map = mapper.readValue(Paths.get(fileName).toFile(), Map.class);  // convert JSON file to map
            books = mapper.readValue(Paths.get(fileName).toFile(), new TypeReference<List<Book>>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Write books to file
    public void writeFile() throws IOException {
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        //writer.writeValue(Paths.get("src/main/resources/static/book2.json").toFile(), books);
        writer.writeValue(Paths.get(fileName).toFile(), books);
    }

    //Get all books
    public List<Book> getAllBooks(){
        readFile();
        return books;
    }

    //Get book by id
    public Book getBookById(int id){
        readFile();
        return books.stream()
                .filter(b->b.getId()==id)
                .findAny()
                .orElse(null);
    }

    //Create a new book
    public void addBook(Book book) throws IOException {
        readFile();
        /*
        Integer maxId =books.stream()
                .mapToInt(b->b.getId())
                .max().orElseThrow(NoSuchElementException::new);
        */
        books.add(book);
        writeFile();
    }

    //Update a book
    public void updateBook(Book newBook) throws IOException {
        readFile();
        Book book = books.stream()
                .filter(b->b.getId()==newBook.getId())
                .findAny()
                .orElse(null);
        books.set(books.indexOf(book), newBook);
        writeFile();
    }

    public void deleteBook(int id) throws IOException {
        readFile();
        Book book = books.stream()
                .filter(b->b.getId()==id)
                .findAny()
                .orElse(null);
        books.remove(book);
        writeFile();
    }

    @SneakyThrows
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        books.forEach(book-> {
            try {
                String json = objectWriter.writeValueAsString(book);
                sb.append(json+",\n");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        sb.delete(sb.length()-2,sb.length()-1);
        sb.append(']');
        return sb.toString();
    }

    public List<Book> getBookPage(int limit, int offset) throws IOException {
        readFile();
        List<Book> page = books.subList(offset, offset + limit);
        return page;
    }

    public List<Book> findBook(String title) throws IOException {
        readFile();
        List<Book> finded =
                books.stream
                        ()
                        .filter(book -> book.getTitle().toUpperCase().contains(title.toUpperCase()))
                        .collect(Collectors.toList());
        return finded;
    }
}
