package pl.paxon96.musiccatalog.recource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecordDto {

    @Value("${music.catalog.default_array_size}:100")
    private int arraysSize;

    private MultipartFile photo;

    private Long[] composerIdArray;

    private Long[] performerIdArray;

    private Long[] musicTypeIdArray;

    private Long formatId;

    private String title;

    private Boolean isReproduction;

    private String description;

    private Integer year;

    private Integer recordAmount;

    public RecordDto() {
        //TODO temporary solution, need idea to change it
        this.performerIdArray = new Long[90];
        this.composerIdArray = new Long[500];
        this.musicTypeIdArray = new Long[90];
    }
}
