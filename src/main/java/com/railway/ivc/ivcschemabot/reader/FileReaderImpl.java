package com.railway.ivc.ivcschemabot.reader;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * Class to read file in operating system.
 * <p>
 * @author Viktor Zaitsev.
 */
@Component
public class FileReaderImpl implements FileReader {

    /**
     * Method to read file, using getting path.
     * <p>
     * @param path String with path.
     * @return List with file.
     */
    @Override
    public List<File> getFileList(String path) {
        File startFolder = new File(path);
        return List.of(Objects.requireNonNull(startFolder.listFiles()));
    }
}
