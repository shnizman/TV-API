package project.controllers.controllerobjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TvShowResponseObj extends AbstractTvShowResponseObj {

    public TvShowResponseObj() {
    }

    public TvShowResponseObj(int id, String name, String image, List<CastResponseObj> cast) {
        super();
    }


}
