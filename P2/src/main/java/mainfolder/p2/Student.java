package mainfolder.p2;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Student {

    private Integer nia;
    private String name;
    private LocalDate bornDate;

    public Student (Integer nia, String name, LocalDate bornDate){

        this.nia = nia;
        this.name = name;
        this.bornDate = bornDate;

    }

}
