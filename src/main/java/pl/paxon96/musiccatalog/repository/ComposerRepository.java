package pl.paxon96.musiccatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.paxon96.musiccatalog.entity.Composer;

@Repository
public interface ComposerRepository extends JpaRepository<Composer, Long> {
}
