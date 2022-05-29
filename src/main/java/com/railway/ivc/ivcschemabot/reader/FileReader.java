package com.railway.ivc.ivcschemabot.reader;

import java.io.File;
import java.util.List;

/**
 * Interface for reader class.
 * <p>
 * @author Viktor Zaitsev.
 */
public interface FileReader {

    /**
     * Method to read file from operating system.
     * <p>
     * @param path String path to file.
     * @return List with reading files.
     */
    List<File> getFileList(String path);
}
