package project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tables.CastEntity;
import project.tables.CastEntityId;

import java.util.List;

@Repository
public interface CastEntityRepository extends JpaRepository<CastEntity, CastEntityId> {

    boolean existsById_TvShowId(int tvShowId);

    @Transactional
    void deleteAllById_TvShowId(int tvShowId);

    List<CastEntity> findAllById_TvShowId(int tvShowId);
}
