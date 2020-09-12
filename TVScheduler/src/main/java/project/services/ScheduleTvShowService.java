package project.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.controllers.controllerobjects.TvShowFullResponseObj;
import project.controllers.controllerobjects.TvShowResponseObj;
import project.mappers.TvShowMapper;
import project.model.Cast;
import project.model.Episode;
import project.model.TvShow;
import project.repositories.TvShowEntityRepository;
import project.tables.TvShowEntity;
import project.util.ApiErrorHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static project.util.SharedConsts.*;

@Service
@Slf4j
public class ScheduleTvShowService {

    @Autowired
    private TvShowMapper tvShowMapper;
    @Autowired
    private TvShowEntityRepository tvShowEntityRepository;
    @Autowired
    private CastService castService;
    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApiErrorHandler apiErrorHandler;

    public TvShow getTvShowFromMazeApi(int tvShowId) {

        String tvShowUrl = SHOW_URL + tvShowId;
        TvShow tvShow = null;

        try {
            tvShow = restTemplate.getForObject(tvShowUrl, TvShow.class);
            apiErrorHandler.validate(tvShow != null, HttpStatus.NOT_FOUND, TV_SHOW_NOT_FOUND);

            Cast cast = castService.getCastFromMazeApi(tvShowUrl, tvShow.getId());
            tvShow.setCast(cast);

            List<Episode> episodeList = episodeService.getEpisodesFromMazeApi(tvShowUrl, tvShow.getId());
            tvShow.setEpisodes(episodeList);

        } catch (RuntimeException e) {
            if (e instanceof HttpClientErrorException) {
                apiErrorHandler.throwError(((HttpClientErrorException) e).getStatusCode(), e.getMessage(), e);
            }
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_MAZE_API, e);
        }

        return tvShow;
    }

    public void insertTvShow(TvShow tvShow) {

        TvShowEntity tvShowEntity = tvShowMapper.mapToEntity(tvShow);
        try {
            tvShowEntityRepository.save(tvShowEntity);
            log.info("TvShow with id: " + tvShow.getId() + " was saved to DB");
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_SAVE_NEW_TV_SHOW_TO_DB, e);
        }
    }

    public boolean tvShowExistsInDb(int id) {
        boolean exists = false;
        try {
            exists = tvShowEntityRepository.existsById(id);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }
        return exists;
    }


    public void removeTvShow(int tvShowId) {
        try {
            tvShowEntityRepository.deleteById(tvShowId);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_DELETE_TV_SHOW_FROM_DB, e);
        }
        log.info("TvShow with id:" + tvShowId + " was deleted from DB");
    }

    public List<TvShowResponseObj> getScheduleTvShows() {
        List<TvShow> tvShows = new ArrayList<>();
        try {
            List<TvShowEntity> tvShowEntities = tvShowEntityRepository.findAll();
            for (TvShowEntity tvShowEntity : tvShowEntities) {
                TvShow tvShow = tvShowMapper.mapToModel(tvShowEntity);
                tvShow.setCast(castService.getCastForTvShow(tvShow.getId()));
                tvShows.add(tvShow);
            }
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }

        return tvShowMapper.mapToResponseObjList(tvShows);
    }


    public List<TvShowFullResponseObj> getScheduledTvShowsWithUnwatchedEpisode() {

        List<TvShow> tvShows = new ArrayList<>();
        try {
            List<TvShowEntity> tvShowEntities = tvShowEntityRepository.findAll();
            for (TvShowEntity tvShowEntity : tvShowEntities) {
                List<Episode> unwatchedEpisode = null;
                if (tvShowEntity.getFirstUnwatchedEpisodeId() != -1) {
                    unwatchedEpisode = episodeService.getFirstUnwatchedEpisode(tvShowEntity.getFirstUnwatchedEpisodeId());
                }
                TvShow tvShow = tvShowMapper.mapToModel(tvShowEntity);
                tvShow.setCast(castService.getCastForTvShow(tvShow.getId()));
                tvShow.setEpisodes(unwatchedEpisode);
                tvShows.add(tvShow);
            }
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }
        return tvShowMapper.mapToFullResponseObjList(tvShows);
    }

    public void updateFirstUnwatchedEpisode(int tvShowId, int nextUnwatchedEpisodeId) {
        try {
            Optional<TvShowEntity> tvShowEntity = tvShowEntityRepository.findById(tvShowId);
            if (tvShowEntity.isPresent() && tvShowEntity.get().getFirstUnwatchedEpisodeId() != nextUnwatchedEpisodeId) {
                tvShowEntity.get().setFirstUnwatchedEpisodeId(nextUnwatchedEpisodeId);
                tvShowEntityRepository.save(tvShowEntity.get());
                log.info("UnwatchedEpisode for tv show was updated");
            }
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }

    }

    public TvShowResponseObj convertTvShowObj(TvShow tvShow) {
        return tvShowMapper.mapToResponseObj(tvShow);
    }

}
