package pl.paxon96.musiccatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.paxon96.musiccatalog.entity.MusicType;

@Repository
public interface MusicTypeRepository extends JpaRepository<MusicType, Long> {
}
