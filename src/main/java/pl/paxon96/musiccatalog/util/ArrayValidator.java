package pl.paxon96.musiccatalog.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class ArrayValidator {

    public boolean isArrayEmpty(Long[] array){
        return Arrays.stream(array).filter(Objects::nonNull).toArray(Long[]::new).length <= 0;
    }

    public Long[] getNonNullIndexes(Long[] array){
        return Arrays.stream(array).filter(Objects::nonNull).toArray(Long[]::new);
    }

}
