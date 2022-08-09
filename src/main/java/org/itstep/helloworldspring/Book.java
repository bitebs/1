package org.itstep.helloworldspring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;



@Data //Get, Set
//@Entity //Hibernate
@AllArgsConstructor //инициализирующий конструктор
@NoArgsConstructor //конструктор по умолчанию
//@Table(name="books")
public class Book {
    //@Id
    //@Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private int year;
    private String[] authors;

}
