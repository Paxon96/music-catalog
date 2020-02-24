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

import javax.transaction.Transactional;
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

    public void sendImage(MultipartFile fileToUpload, Long id) throws IOException {
        log.info("uploading photo");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
        Map uploadResults = cloudinary.uploader().upload(fileToUpload.getBytes(), ObjectUtils.emptyMap());
        log.info(uploadResults);
        Optional<Record> record = recordRepository.findById(id);
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

    @Transactional
    public void deleteImage(Long recordId) throws IOException {
        log.info("Deleting photo " + recordId);
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));

        Optional<Photo> photoOptional = photoRepository.findById(recordId);
        if(photoOptional.isPresent()){
            Photo photo = photoOptional.get();
            Map deleteResults = cloudinary.uploader().destroy(photo.getPublicId(), ObjectUtils.emptyMap());
            log.info(deleteResults);
            photo.setSignature(null);
            photo.setResourceType(null);
            photo.setSecureUrl(null);
            photo.setBackupUrl(null);
            photo.setUrl(null);
            photo.setPublicId(null);
            photo.setWidth(null);
            photo.setHeight(null);
            photoRepository.save(photo);
        }

    }
}
