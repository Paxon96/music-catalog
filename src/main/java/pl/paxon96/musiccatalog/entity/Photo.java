package pl.paxon96.musiccatalog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {

    @Id
    private Long id;

    private String signature;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "secure_url")
    private String secureUrl;

    @Column(name = "backup_url")
    private String backupUrl;

    private String url;

    @Column(name = "public_id")
    private String publicId;

    private Integer width;

    private Integer height;

    @OneToOne(mappedBy = "photo")
    private Record record;
}
