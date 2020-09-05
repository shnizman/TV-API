package project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class Episode {

    private int id;
    private String name;
    private int season;
    private int number;
    private Date airDate;
    private boolean unwatched;
    private int tvShowId;


    public Episode() {

    }

    @JsonCreator
    public Episode(int id, String name, int season, int number, @JsonProperty("airdate") Date airDate, int tvShowId) {
        this.id = id;
        this.name = name;
        this.season = season;
        this.number = number;
        this.airDate = airDate;
        this.tvShowId = tvShowId;
    }


}
