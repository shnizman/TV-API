package project.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TvShow {

    private int id;
    private String name;
    private Image image;
    private Cast cast;
    private Integer firstUnwatchedEpisodeId;
    private List<Episode> episodes;

    public TvShow() {

    }

    public TvShow(int id, String name, Image image, Cast cast) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.cast = cast;
        this.firstUnwatchedEpisodeId = null;
        this.episodes = new ArrayList<>();
    }

}
