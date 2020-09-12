package project.tables;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCHEDULE")
public class TvShowEntity implements java.io.Serializable {

    @Id
    private int id;
    private String name;
    private String image;
    private int firstUnwatchedEpisodeId;


    public TvShowEntity() {
    }

    public TvShowEntity(int id, String name, String image, int firstUnwatchedEpisodeId) {

        this.id = id;
        this.name = name;
        this.image = image;
        this.firstUnwatchedEpisodeId = firstUnwatchedEpisodeId;

    }

    @Column(name = "ID", nullable = false)
    public int getId() {
        return this.id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    @Column(name = "IMAGE", nullable = false)
    public String getImage() {
        return this.image;
    }

    @Column(name = "FIRST_UNWATCHED_EPISODEID", nullable = false)
    public int getFirstUnwatchedEpisodeId() {
        return this.firstUnwatchedEpisodeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setFirstUnwatchedEpisodeId(int firstUnwatchedEpisodeId) {
        this.firstUnwatchedEpisodeId = firstUnwatchedEpisodeId;
    }
}
