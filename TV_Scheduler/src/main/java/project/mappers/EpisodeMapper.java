package project.mappers;

import org.springframework.stereotype.Service;
import project.controllers.controllerobjects.EpisodeResponseObj;
import project.model.Episode;
import project.tables.EpisodesEntity;

import java.util.List;

@Service
public class EpisodeMapper implements EntityMapper<EpisodesEntity, Episode> {


    @Override
    public EpisodesEntity mapToEntity(Episode episode) {

        EpisodesEntity episodesEntity = new EpisodesEntity();
        episodesEntity.setId(episode.getId());
        episodesEntity.setName(episode.getName());
        episodesEntity.setSeason(episode.getSeason());
        episodesEntity.setEpisodeNumber(episode.getNumber());
        episodesEntity.setEpisodeAirDate(episode.getAirDate());
        episodesEntity.setUnwatched(mapBooleanUnwatchedToInteger(episode.isUnwatched()));
        episodesEntity.setTvShowId(episode.getTvShowId());
        return episodesEntity;
    }

    @Override
    public Episode mapToModel(EpisodesEntity episodesEntity) {
        Episode episode = new Episode();
        episode.setId(episodesEntity.getId());
        episode.setName(episodesEntity.getName());
        episode.setSeason(episodesEntity.getSeason());
        episode.setNumber(episodesEntity.getEpisodeNumber());
        episode.setAirDate(episodesEntity.getEpisodeAirDate());
        episode.setUnwatched(mapIntegerUnwatchedToBoolean(episodesEntity.getUnwatched()));
        episode.setTvShowId(episodesEntity.getTvShowId());
        return episode;
    }

    public EpisodeResponseObj mapToResponseObj(List<Episode> episodes) {
        EpisodeResponseObj episodeResponseObj = null;
        if (episodes != null) {
            episodeResponseObj = new EpisodeResponseObj();
            Episode episode = episodes.get(0);
            episodeResponseObj.setId(episode.getId());
            episodeResponseObj.setName(episode.getName());
            episodeResponseObj.setSeason(episode.getSeason());
            episodeResponseObj.setNumber(episode.getNumber());
            episodeResponseObj.setAirDate(episode.getAirDate());
        }

        return episodeResponseObj;
    }

    private int mapBooleanUnwatchedToInteger(boolean unwatched) {
        return unwatched ? 1 : 0;
    }

    private boolean mapIntegerUnwatchedToBoolean(int unwatched) {
        return unwatched == 1;
    }

}
