package project.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.mappers.EpisodeMapper;
import project.model.Episode;
import project.model.TvShow;
import project.repositories.EpisodeEntityRepository;
import project.tables.EpisodesEntity;
import project.util.ApiErrorHandler;

import java.util.*;
import java.util.stream.Collectors;

import static project.util.SharedConsts.*;


@Slf4j
@Service
public class EpisodeService {

    @Autowired
    private EpisodeMapper episodeMapper;
    @Autowired
    private EpisodeEntityRepository episodeEntityRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApiErrorHandler apiErrorHandler;


    public List<Episode> getEpisodesFromMazeApi(String tvShowUrl, int tvShowId) {

        String episodeUrl = tvShowUrl + EPISODE;
        ResponseEntity<Episode[]> response = restTemplate.getForEntity(episodeUrl, Episode[].class);
        Episode[] episodes = response.getBody();
        apiErrorHandler.validate(episodes != null, HttpStatus.NOT_FOUND, TV_SHOW_EPISODES_NOT_FOUND);
        apiErrorHandler.validate(episodes.length > 0, HttpStatus.NOT_FOUND, TV_SHOW_EPISODES_NOT_FOUND);
        List<Episode> episodeList = Arrays.asList(episodes);
        episodeList.forEach(episode -> episode.setTvShowId(tvShowId));
        return episodeList;
    }


    public void insertEpisodes(TvShow tvShow) {
        List<EpisodesEntity> episodesEntityList = new ArrayList<>();
        for (Episode episode : tvShow.getEpisodes()) {
            EpisodesEntity episodesEntity = episodeMapper.mapToEntity(episode);
            episodesEntityList.add(episodesEntity);
        }
        try {
            episodeEntityRepository.saveAll(episodesEntityList);
            log.info("Episodes were saved to DB");
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_SAVE_NEW_EPISODES_TO_DB, e);
        }
    }

    public void removeEpisodesRelatedTvShow(int tvShowId) {
        apiErrorHandler.validate(episodeEntityRepository.existsByTvShowId(tvShowId), HttpStatus.NOT_FOUND, EPISODES_NOT_FOUND);
        try {
            episodeEntityRepository.deleteAllByTvShowId(tvShowId);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_DELETE_EPISODES_FROM_DB, e);
        }
        log.info("Episodes with tvShowId:" + tvShowId + " was deleted from DB");
    }

    public List<Episode> getFirstUnwatchedEpisode(int firstUnwatchedEpisodeId) {
        Optional<EpisodesEntity> episodesEntity = episodeEntityRepository.findById(firstUnwatchedEpisodeId);
        apiErrorHandler.validate(episodesEntity.isPresent(), HttpStatus.INTERNAL_SERVER_ERROR, EPISODES_SHOULD_BE_FOUND_IN_DB);
        Episode episode = episodeMapper.mapToModel(episodesEntity.get());
        return new ArrayList<>(Collections.singletonList(episode));
    }

    public EpisodesEntity handleNextUnwatchedEpisode(int tvShowId) {

        EpisodesEntity nextUnwatchedEpisode = null;
        if (tvShowId != -1) {
            List<EpisodesEntity> episodesEntityList = findNextUnwatchedListEpisode(tvShowId);
            nextUnwatchedEpisode = findNextUnwatchedEpisode(tvShowId, episodesEntityList);
        }
        return nextUnwatchedEpisode;
    }

    public int markEpisode(int episodeId) {
        Optional<EpisodesEntity> episodesEntity = null;
        try {
            episodesEntity = episodeEntityRepository.findById(episodeId);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }
        apiErrorHandler.validate(episodesEntity.isPresent(), HttpStatus.NOT_FOUND, EPISODES_NOT_FOUND);
        try {
            if (episodesEntity.get().getUnwatched() == 0) {
                episodesEntity.get().setUnwatched(1);
                episodeEntityRepository.save(episodesEntity.get());
                log.info("Episode was marked as watched");
                return episodesEntity.get().getTvShowId();
            }
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }
        log.info("Already mark as watched");
        return -1;
    }

    public List<EpisodesEntity> findNextUnwatchedListEpisode(int TvShowId) {
        List<EpisodesEntity> episodesEntityList = null;
        try {
            episodesEntityList = episodeEntityRepository.findAllByTvShowId(TvShowId);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_CONNECT_TO_DB, e);
        }
        apiErrorHandler.validate(episodesEntityList != null, HttpStatus.INTERNAL_SERVER_ERROR, EPISODES_SHOULD_BE_FOUND_IN_DB);
        return episodesEntityList
                .stream()
                .filter(x -> x.getTvShowId() == TvShowId)
                .filter(x -> x.getUnwatched() == 0)
                .collect(Collectors.toList());
    }


    public EpisodesEntity findNextUnwatchedEpisode(int TvShowId, List<EpisodesEntity> episodesEntities) {

        EpisodesEntity episodesEntity = null;
        if (!episodesEntities.isEmpty()) {
            episodesEntity = episodesEntities
                    .stream()
                    .filter(x -> x.getTvShowId() == TvShowId)
                    .filter(x -> x.getUnwatched() == 0)
                    .min(Comparator
                            .comparingInt(EpisodesEntity::getSeason)
                            .thenComparing(EpisodesEntity::getEpisodeNumber)).get();
        }
        return episodesEntity;
    }


}
