package pl.paxon96.musiccatalog.recource;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PerformerDto{

    private Long id;

    private String name;
}
