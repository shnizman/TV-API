package project.controllers.controllerobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EpisodeResponseObj {

    private int id;
    private String name;
    private int season;
    private int number;
    private Date airDate;

    public EpisodeResponseObj() {

    }

}
