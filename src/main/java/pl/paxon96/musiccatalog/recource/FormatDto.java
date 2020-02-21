package pl.paxon96.musiccatalog.recource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FormatDto {

    private Long id;

    private String name;
}
