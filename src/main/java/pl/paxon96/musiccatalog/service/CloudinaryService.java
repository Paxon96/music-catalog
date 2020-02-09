package pl.paxon96.musiccatalog.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.paxon96.musiccatalog.entity.Photo;
import pl.paxon96.musiccatalog.entity.Record;
import pl.paxon96.musiccatalog.repository.PhotoRepository;
import pl.paxon96.musiccatalog.repository.RecordRepository;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class CloudinaryService {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private PhotoRepository photoRepository;

    public void sendImage(MultipartFile fileToUpload) throws IOException {
        log.info("uploading photo");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
        Map uploadResults = cloudinary.uploader().upload(fileToUpload.getBytes(), ObjectUtils.emptyMap());
        System.out.println(uploadResults);
        Optional<Record> record = recordRepository.findById(1L);
        Record r = record.get();
        Photo photo = Photo.builder()
                .signature(String.valueOf(uploadResults.get("signature")))
                .resourceType(String.valueOf(uploadResults.get("resource_type")))
                .secureUrl(String.valueOf(uploadResults.get("secure_url")))
                .backupUrl(String.valueOf(uploadResults.get("backup_url")))
                .url(String.valueOf(uploadResults.get("url")))
                .publicId(String.valueOf(uploadResults.get("public_id")))
                .width((Integer) uploadResults.get("width"))
                .height((Integer) uploadResults.get("height"))
                .id(r.getId())
                .build();
        photoRepository.save(photo);
    }
}
