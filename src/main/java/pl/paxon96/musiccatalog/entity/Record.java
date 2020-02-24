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

    private Integer year;

    @Column(name = "is_reproduction")
    private Boolean isReproduction;

    @Column(name = "record_type")
    private String recordType;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "music_type_id")
    private MusicType musicType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "format_id")
    private Format format;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", isReproduction=" + isReproduction +
                ", recordType='" + recordType + '\'' +
                ", recordAmount=" + recordAmount +
                ", description='" + description + '\'' +
                ", composers=" + composers.stream().map(Composer::getName).collect(Collectors.toList()) +
                ", performers=" + performers.stream().map(Performer::getName).collect(Collectors.toList()) +
                ", photo=" + photo.getUrl() +
                ", musicType=" + musicType.getName() +
                ", format=" + format.getName() +
                '}';
    }

}
