package pl.paxon96.musiccatalog.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordFilterParameters {

    private String title;
    private String composer;
    private String performer;
    private Boolean isReproduction;
    private String musicType;
    private String format;
    private String recordType;
    private String year;
    private String recordsAmount;
}
