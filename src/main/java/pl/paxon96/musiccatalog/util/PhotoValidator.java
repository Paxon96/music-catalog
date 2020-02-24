package pl.paxon96.musiccatalog.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoValidator {

    public boolean validatePhoto(MultipartFile file){
        if(file.getOriginalFilename().trim().equalsIgnoreCase("")){
            return false;
        }else if(!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')).equalsIgnoreCase(".jpg") &&
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')).equalsIgnoreCase(".png") &&
                !file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')).equalsIgnoreCase(".jpeg")){
            return false;
        }else if(file.getSize() == 0){
            return false;
        }else
            return true;
    }

}
