package com.railwai.ivc.ivcschemabot.reader;

import java.io.File;
import java.util.List;

public interface FileReader {

    List<File> readFiles(File path);
}
