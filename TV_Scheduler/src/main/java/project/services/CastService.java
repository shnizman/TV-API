package project.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.mappers.CastMapper;
import project.model.Cast;
import project.model.CastMember;
import project.repositories.CastEntityRepository;
import project.tables.CastEntity;
import project.util.ApiErrorHandler;

import java.util.Arrays;
import java.util.List;

import static project.util.SharedConsts.*;

@Slf4j
@Service
public class CastService {


    @Autowired
    private CastMapper castMapper;
    @Autowired
    private CastEntityRepository castEntityRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ApiErrorHandler apiErrorHandler;


    public Cast getCastFromMazeApi(String tvShowUrl, int tvShowId) {

        String castUrl = tvShowUrl + CAST;
        ResponseEntity<CastMember[]> response = restTemplate.getForEntity(castUrl, CastMember[].class);
        CastMember[] castMembers = response.getBody();
        apiErrorHandler.validate(castMembers != null, HttpStatus.NOT_FOUND, TV_SHOW_CAST_NOT_FOUND);
        apiErrorHandler.validate(castMembers.length > 0, HttpStatus.NOT_FOUND, TV_SHOW_CAST_NOT_FOUND);
        return new Cast(Arrays.asList(castMembers), tvShowId);
    }

    public void insertCast(Cast cast) {

        List<CastEntity> castEntityList = castMapper.mapToEntityList(cast);
        try {
            castEntityRepository.saveAll(castEntityList);
            log.info("Cast was saved to DB");
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_SAVE_NEW_CAST_TO_DB, e);
        }
    }

    public void removeCastRelatedTvShow(int tvShowId) {
        apiErrorHandler.validate(castEntityRepository.existsById_TvShowId(tvShowId), HttpStatus.NOT_FOUND, "Cast Not found");
        try {
            castEntityRepository.deleteAllById_TvShowId(tvShowId);
        } catch (Exception e) {
            apiErrorHandler.throwError(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_TRYING_TO_DELETE_CAST_FROM_DB, e);
        }
        log.info("Cast with tvShowId:" + tvShowId + " was deleted from DB");
    }

    public Cast getCastForTvShow(int tvShowId) {
        List<CastEntity> castEntityList = castEntityRepository.findAllById_TvShowId(tvShowId);
        apiErrorHandler.validate(castEntityList != null, HttpStatus.NOT_FOUND, TV_SHOW_CAST_NOT_FOUND);
        apiErrorHandler.validate(castEntityList.size() > 0, HttpStatus.NOT_FOUND, TV_SHOW_CAST_NOT_FOUND);
        return castMapper.mapToModelList(castEntityList);
    }


}
