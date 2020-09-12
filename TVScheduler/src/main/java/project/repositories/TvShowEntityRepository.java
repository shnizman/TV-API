package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.tables.TvShowEntity;

@Repository
public interface TvShowEntityRepository extends JpaRepository<TvShowEntity, Integer> {

}
