package project.mappers;

import org.springframework.stereotype.Service;
import project.controllers.controllerobjects.CastResponseObj;
import project.model.Cast;
import project.model.CastMember;
import project.model.Image;
import project.model.Person;
import project.tables.CastEntity;
import project.tables.CastEntityId;

import java.util.ArrayList;
import java.util.List;

@Service
public class CastMapper implements EntityMapper<CastEntity, Cast> {

    public List<CastEntity> mapToEntityList(Cast cast) {

        List<CastEntity> castEntityList = new ArrayList<>();

        for (CastMember castMember : cast.getCastMembers()) {
            Person person = castMember.getPerson();

            CastEntityId castEntityId = new CastEntityId();
            castEntityId.setId(person.getId());
            castEntityId.setTvShowId(cast.getTvShowId());

            CastEntity castEntity = new CastEntity();
            castEntity.setId(castEntityId);
            castEntity.setName(person.getName());
            castEntity.setImage(person.getImage().getOriginal());

            castEntityList.add(castEntity);
        }

        return castEntityList;
    }

    public Cast mapToModelList(List<CastEntity> castEntityList) {

        List<CastMember> castMembers = new ArrayList<>();
        for (CastEntity castEntity : castEntityList) {
            Person person = new Person();
            person.setId(castEntity.getId().getId());
            person.setName(castEntity.getName());
            Image image = new Image(castEntity.getImage());
            person.setImage(image);
            CastMember castMember = new CastMember(person);
            castMembers.add(castMember);
        }
        return new Cast(castMembers);
    }


    public List<CastResponseObj> mapToResponseObjList(Cast cast) {
        List<CastResponseObj> castResponseObjs = new ArrayList<>();

        for (CastMember castMember : cast.getCastMembers()) {
            Person person = castMember.getPerson();
            CastResponseObj castResponseObj = new CastResponseObj();
            castResponseObj.setId(person.getId());
            castResponseObj.setName(person.getName());
            castResponseObj.setImage(person.getImage().getOriginal());
            castResponseObjs.add(castResponseObj);
        }

        return castResponseObjs;
    }

    @Override
    public CastEntity mapToEntity(Cast object) {
        return null;
    }

    @Override
    public Cast mapToModel(CastEntity object) {
        return null;
    }
}
