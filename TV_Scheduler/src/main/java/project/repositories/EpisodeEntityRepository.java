package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.tables.EpisodesEntity;

import java.util.List;

@Repository
public interface EpisodeEntityRepository extends JpaRepository<EpisodesEntity, Integer> {

    boolean existsByTvShowId(int tvShowId);

    @Transactional
    void deleteAllByTvShowId(int tvShowId);

    List<EpisodesEntity> findAllByTvShowId(int tvShowId);

}
