package project.controllers.controllerobjects;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TvShowFullResponseObj extends AbstractTvShowResponseObj {

    private EpisodeResponseObj firstUnwatchedEpisode;

    public TvShowFullResponseObj() {
    }

    public TvShowFullResponseObj(int id, String name, String image, List<CastResponseObj> cast, EpisodeResponseObj firstUnwatchedEpisode) {
        super(id, name, image, cast);
        this.firstUnwatchedEpisode = firstUnwatchedEpisode;

    }

}
