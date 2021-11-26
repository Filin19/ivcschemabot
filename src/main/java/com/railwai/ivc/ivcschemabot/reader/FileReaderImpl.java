package com.railwai.ivc.ivcschemabot.reader;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class FileReaderImpl implements FileReader {

    @Override
    public List<File> getFileList(String path) {
        File startFolder = new File(path);
        return List.of(startFolder.listFiles());
    }
}
