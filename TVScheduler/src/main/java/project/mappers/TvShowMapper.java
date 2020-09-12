package project.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.controllers.controllerobjects.TvShowFullResponseObj;
import project.controllers.controllerobjects.TvShowResponseObj;
import project.model.Image;
import project.model.TvShow;
import project.tables.TvShowEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class TvShowMapper implements EntityMapper<TvShowEntity, TvShow> {

    @Autowired
    private CastMapper castMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Override
    public TvShowEntity mapToEntity(TvShow tvShow) {
        TvShowEntity tvShowEntity = new TvShowEntity();
        tvShowEntity.setId(tvShow.getId());
        tvShowEntity.setName(tvShow.getName());
        tvShowEntity.setImage(tvShow.getImage().getOriginal());
        tvShowEntity.setFirstUnwatchedEpisodeId(tvShow.getEpisodes().get(0).getId());
        return tvShowEntity;
    }

    @Override
    public TvShow mapToModel(TvShowEntity tvShowEntity) {
        TvShow tvShow = new TvShow();
        tvShow.setId(tvShowEntity.getId());
        tvShow.setName(tvShowEntity.getName());
        Image image = new Image(tvShowEntity.getImage());
        tvShow.setImage(image);
        return tvShow;
    }


    public List<TvShowResponseObj> mapToResponseObjList(List<TvShow> tvShows) {

        List<TvShowResponseObj> tvShowResponseObjs = new ArrayList<>();
        for (TvShow tvShow : tvShows) {
            TvShowResponseObj tvShowResponseObj = new TvShowResponseObj();
            tvShowResponseObj.setId(tvShow.getId());
            tvShowResponseObj.setName(tvShow.getName());
            tvShowResponseObj.setImage(tvShow.getImage().getOriginal());
            tvShowResponseObj.setCast(castMapper.mapToResponseObjList(tvShow.getCast()));
            tvShowResponseObjs.add(tvShowResponseObj);
        }
        return tvShowResponseObjs;
    }

    public List<TvShowFullResponseObj> mapToFullResponseObjList(List<TvShow> tvShows) {

        List<TvShowFullResponseObj> tvShowFullResponseObjs = new ArrayList<>();
        for (TvShow tvShow : tvShows) {
            TvShowFullResponseObj tvShowFullResponseObj = new TvShowFullResponseObj();
            tvShowFullResponseObj.setId(tvShow.getId());
            tvShowFullResponseObj.setName(tvShow.getName());
            tvShowFullResponseObj.setImage(tvShow.getImage().getOriginal());
            tvShowFullResponseObj.setFirstUnwatchedEpisode(episodeMapper.mapToResponseObj(tvShow.getEpisodes()));
            tvShowFullResponseObj.setCast(castMapper.mapToResponseObjList(tvShow.getCast()));
            tvShowFullResponseObjs.add(tvShowFullResponseObj);
        }
        return tvShowFullResponseObjs;
    }

    public TvShowResponseObj mapToResponseObj(TvShow tvShow) {

        TvShowResponseObj tvShowResponseObj = new TvShowResponseObj();
        tvShowResponseObj.setId(tvShow.getId());
        tvShowResponseObj.setName(tvShow.getName());
        tvShowResponseObj.setImage(tvShow.getImage().getOriginal());
        tvShowResponseObj.setCast(castMapper.mapToResponseObjList(tvShow.getCast()));
        return tvShowResponseObj;
    }
}
