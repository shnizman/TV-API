package project.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cast {

    private List<CastMember> castMembers;
    private int tvShowId;

    public Cast(List<CastMember> castMembers, int tvShowId) {
        this.castMembers = castMembers;
        this.tvShowId = tvShowId;
    }

    public Cast(List<CastMember> castMembers) {
        this.castMembers = castMembers;
    }
}
