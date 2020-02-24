package pl.paxon96.musiccatalog.filter;

import org.springframework.data.jpa.domain.Specification;
import pl.paxon96.musiccatalog.entity.*;

import javax.persistence.criteria.*;

public class RecordSpecification implements Specification<Record> {

    private RecordFilterParameters recordFilterParameters;

    public RecordSpecification(RecordFilterParameters recordFilterParameters) {
        super();
        this.recordFilterParameters = recordFilterParameters;
    }

    @Override
    public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if(recordFilterParameters.getComposer() != null && !recordFilterParameters.getComposer().trim().equalsIgnoreCase("")){
            Join<Record, Composer> join = root.join("composers");
            predicate.getExpressions().add(cb.like(join.get("name"), "%"+recordFilterParameters.getComposer()+"%"));
        }
        if(recordFilterParameters.getTitle() != null && !recordFilterParameters.getTitle().trim().equalsIgnoreCase("")){
            predicate.getExpressions().add(cb.like(root.get("title"), "%"+recordFilterParameters.getTitle()+"%"));
        }
        if(recordFilterParameters.getIsReproduction() != null){
            predicate.getExpressions().add(cb.equal(root.get("isReproduction"), recordFilterParameters.getIsReproduction()));
        }
        if(recordFilterParameters.getPerformer() != null && !recordFilterParameters.getPerformer().trim().equalsIgnoreCase("")){
            Join<Record, Performer> join = root.join("performers");
            predicate.getExpressions().add(cb.like(join.get("name"), "%"+recordFilterParameters.getPerformer()+"%"));
        }
        if(recordFilterParameters.getMusicType() != null && !recordFilterParameters.getMusicType().trim().equalsIgnoreCase("")){
            Join<Record, MusicType> join = root.join("musicType");
            predicate.getExpressions().add(cb.like(join.get("name"), "%"+recordFilterParameters.getMusicType()+"%"));
        }
        if(recordFilterParameters.getFormat() != null && !recordFilterParameters.getFormat().trim().equalsIgnoreCase("")){
            Join<Record, Format> join = root.join("format");
            predicate.getExpressions().add(cb.like(join.get("name"), "%"+recordFilterParameters.getFormat()+"%"));
        }
        if(recordFilterParameters.getYear() != null && !recordFilterParameters.getYear().trim().equalsIgnoreCase("")){
            predicate.getExpressions().add(cb.equal(root.get("year"), recordFilterParameters.getYear()));
        }
        if(recordFilterParameters.getRecordsAmount() != null && !recordFilterParameters.getRecordsAmount().trim().equalsIgnoreCase("")){
            predicate.getExpressions().add(cb.equal(root.get("recordAmount"), recordFilterParameters.getRecordsAmount()));
        }
        if(recordFilterParameters.getRecordType() != null && !recordFilterParameters.getRecordType().trim().equalsIgnoreCase("")){
            predicate.getExpressions().add(cb.equal(root.get("recordType"), recordFilterParameters.getRecordType()));
        }

        return predicate;
    }
}
