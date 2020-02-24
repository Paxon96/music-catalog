package pl.paxon96.musiccatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "composers", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Record> records;

    public void addRecord(Record record){
        records.add(record);
        record.getComposers().add(this);
    }

    public void removeRecord(Record record){
        records.remove(record);
        record.getComposers().remove(this);
    }
}
