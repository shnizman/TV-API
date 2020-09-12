package project.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Image {

    private String original;

    public Image() {
    }

    public Image(String original) {
        this.original = original;
    }

}
