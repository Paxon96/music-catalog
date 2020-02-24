package pl.paxon96.musiccatalog.recource;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecordDto {

    private MultipartFile photo;

    private Long[] composerIdArray;

    private Long[] performerIdArray;

    private Long musicTypeId;

    private Long formatId;

    private String title;

    private Boolean isReproduction;

    private String description;

    private String year;

    private String recordType;

    private String recordAmount;

    public RecordDto() {
        //TODO temporary solution, need idea to change it
        this.performerIdArray = new Long[90];
        this.composerIdArray = new Long[500];
    }
}
