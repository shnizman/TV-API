package project.tables;


import javax.persistence.*;

@Entity
@Table(name = "SHOWCAST")
public class CastEntity implements java.io.Serializable {


    private CastEntityId id;
    private String name;
    private String image;


    public CastEntity() {
    }

    public CastEntity(CastEntityId id, String name, String image) {

        this.id = id;
        this.name = name;
        this.image = image;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false)),
            @AttributeOverride(name = "tvShowId", column = @Column(name = "TVSHOWID", nullable = false))})
    public CastEntityId getId() {
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

    public void setId(CastEntityId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
