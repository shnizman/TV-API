package project.controllers.controllerobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AbstractTvShowResponseObj {

    private int id;
    private String name;
    private String image;
    private List<CastResponseObj> cast;


    public AbstractTvShowResponseObj() {
    }

    public AbstractTvShowResponseObj(int id, String name, String image, List<CastResponseObj> cast) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.cast = cast;
    }

}
