package pl.paxon96.musiccatalog.recource;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class RecordDto {

    private MultipartFile photo;

    private Long composerId;

    private Long performerId;

    private String title;

    private Boolean isReproduction;

    private String description;

    private Integer year;

    private Integer recordAmount;
}
