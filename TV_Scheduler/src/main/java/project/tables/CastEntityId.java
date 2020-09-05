package project.tables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CastEntityId implements java.io.Serializable {

    private int id;
    private int tvShowId;

    public CastEntityId() {
    }

    public CastEntityId(int id, int tvShowId) {

        this.id = id;
        this.tvShowId = tvShowId;
    }

    @Column(name = "ID", nullable = false)
    public int getId() {
        return this.id;
    }

    @Column(name = "TV_SHOW_ID", nullable = false)
    public int getTvShowId() {
        return this.tvShowId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }
}
