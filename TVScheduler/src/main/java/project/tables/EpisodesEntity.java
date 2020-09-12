package project.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "EPISODES")
public class EpisodesEntity implements java.io.Serializable {

    @Id
    private int id;
    private String name;
    private int season;
    private int episodeNumber;
    private Date episodeAirDate;
    private int unwatched;
    private int tvShowId;

    public EpisodesEntity() {
    }

    public EpisodesEntity(int id, String name, int season, int episodeNumber, Date episodeAirDate, int unwatched, int tvShowId) {

        this.id = id;
        this.name = name;
        this.season = season;
        this.episodeNumber = episodeNumber;
        this.episodeAirDate = episodeAirDate;
        this.unwatched = unwatched;
        this.tvShowId = tvShowId;

    }

    @Column(name = "ID", nullable = false)
    public int getId() {
        return this.id;
    }

    @Column(name = "NAME", nullable = false)
    public String getName() {
        return this.name;
    }

    @Column(name = "SEASON", nullable = false)
    public int getSeason() {
        return this.season;
    }

    @Column(name = "EPISODE_NUMBER", nullable = false)
    public int getEpisodeNumber() {
        return this.episodeNumber;
    }

    @Column(name = "EPISODE_AIR_DATE", nullable = false)
    public Date getEpisodeAirDate() {
        return this.episodeAirDate;
    }

    @Column(name = "UNWATCHED", nullable = false)
    public int getUnwatched() {
        return this.unwatched;
    }

    @Column(name = "TV_SHOW_ID", nullable = false)
    public int getTvShowId() {
        return this.tvShowId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public void setEpisodeAirDate(Date episodeAirDate) {
        this.episodeAirDate = episodeAirDate;
    }

    public void setUnwatched(int unwatched) {
        this.unwatched = unwatched;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }
}
