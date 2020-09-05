package project.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.controllers.controllerobjects.TvShowFullResponseObj;
import project.controllers.controllerobjects.TvShowResponseObj;
import project.model.TvShow;
import project.services.CastService;
import project.services.EpisodeService;
import project.services.ScheduleTvShowService;
import project.tables.EpisodesEntity;
import project.util.ApiErrorHandler;

import java.util.List;


@RestController
@RequestMapping(path = "/api/tvSchedule")
@Slf4j
public class TvScheduleController {

    @Autowired
    private ScheduleTvShowService scheduleTvShowService;
    @Autowired
    private EpisodeService episodeService;
    @Autowired
    private CastService castService;
    @Autowired
    private ApiErrorHandler apiErrorHandler;


    @PostMapping(path = "/tvShow", produces = "application/json")
    @ResponseBody
    public TvShowResponseObj addTvShow(@RequestParam(value = "id") int tvShowId) {

        TvShow tvShow = scheduleTvShowService.getTvShowFromMazeApi(tvShowId);

        if (!scheduleTvShowService.tvShowExistsInDb(tvShow.getId())) {
            scheduleTvShowService.insertTvShow(tvShow);
            castService.insertCast(tvShow.getCast());
            episodeService.insertEpisodes(tvShow);
        } else {
            log.info("tv Show already exists in Schedule");
        }

        return scheduleTvShowService.convertTvShowObj(tvShow);
    }

    @DeleteMapping(path = "/tvShow", produces = "application/json")
    @ResponseBody
    public void removeTvShow(@RequestParam(value = "id") int tvShowId) {

        apiErrorHandler.validate(scheduleTvShowService.tvShowExistsInDb(tvShowId), HttpStatus.NOT_FOUND, "Tv Show Not found");
        scheduleTvShowService.removeTvShow(tvShowId);
        episodeService.removeEpisodesRelatedTvShow(tvShowId);
        castService.removeCastRelatedTvShow(tvShowId);
    }

    @GetMapping(path = "/tvShows", produces = "application/json")
    @ResponseBody
    public List<TvShowResponseObj> getAllScheduledTvShows() {
        return scheduleTvShowService.getScheduleTvShows();
    }

    @GetMapping(path = "/tvShows/unwatched", produces = "application/json")
    @ResponseBody
    public List<TvShowFullResponseObj> getAllScheduledTvShowsWithUnwatchedEpisode() {
        return scheduleTvShowService.getScheduledTvShowsWithUnwatchedEpisode();
    }

    @PutMapping(path = "/episode", produces = "application/json")
    @ResponseBody
    public void markEpisode(@RequestParam(value = "id") int episodeId) {
        int tvShowId = episodeService.markEpisode(episodeId);
        EpisodesEntity nextUnwatchedEpisode = episodeService.handleNextUnwatchedEpisode(tvShowId);
        if (nextUnwatchedEpisode == null) {
            scheduleTvShowService.updateFirstUnwatchedEpisode(tvShowId, -1);
        } else {
            scheduleTvShowService.updateFirstUnwatchedEpisode(nextUnwatchedEpisode.getTvShowId(), nextUnwatchedEpisode.getId());
        }
    }
}
