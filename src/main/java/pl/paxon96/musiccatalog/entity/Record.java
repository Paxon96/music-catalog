package pl.paxon96.musiccatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String type;

    private Integer year;

    @Column(name = "is_reproduction")
    private Boolean isReproduction;

    @Column(name = "record_type")
    private String recordType;

    private String format;

    @Column(name = "record_amount")
    private Integer recordAmount;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "record_composer",
            joinColumns = {@JoinColumn(name = "record_id")},
            inverseJoinColumns = {@JoinColumn(name = "composer_id")}
    )
    private List<Composer> composers;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "record_performer",
            joinColumns = {@JoinColumn(name = "record_id")},
            inverseJoinColumns = {@JoinColumn(name = "performer_id")}
    )
    private List<Performer> performers;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", isReproduction=" + isReproduction +
                ", recordType='" + recordType + '\'' +
                ", format='" + format + '\'' +
                ", recordAmount=" + recordAmount +
                ", description='" + description + '\'' +
                ", composers=" + composers.stream().map(Composer::getName).collect(Collectors.toList()) +
                ", performers=" + performers.stream().map(Performer::getName).collect(Collectors.toList()) +
                '}';
    }
}
