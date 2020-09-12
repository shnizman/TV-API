package project.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    private int id;
    private String name;
    private Image image;

    public Person() {

    }

    public Person(int id, String name, Image image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

}
