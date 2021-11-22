package com.railwai.ivc.ivcschemabot.reader;

import java.io.File;
import java.util.List;

public class FileReaderImpl implements FileReader {

    @Override
    public List<File> readFiles(File path) {
        for(File entryFile : path.listFiles()) {

        }
        return null;
    }
}
