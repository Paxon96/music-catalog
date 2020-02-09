package pl.paxon96.musiccatalog.recource;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
public class RecordDto {

    private MultipartFile photo;
}
