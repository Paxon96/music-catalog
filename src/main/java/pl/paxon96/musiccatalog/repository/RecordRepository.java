package pl.paxon96.musiccatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.paxon96.musiccatalog.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
