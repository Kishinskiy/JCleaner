package com.kishinskiy;

import com.kishinskiy.utils.FileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Main {
    private static FileInputStream stream;
    private static String diskName;
    private static double maxDiskUsage;
    private static String path;
    private static long maxFileSize;
    private static String fileType;

    //////// Main Logic /////////


    public static void main(String[] args) throws IOException {
        try {
            stream = new FileInputStream("src/main/resources/config.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        try {
            Properties property = new Properties();
            property.load(stream);

            maxFileSize = Long.parseLong(property.getProperty("maxFileSize")); // Maximum file size in bites. If file size more this value it will  be removed
            maxDiskUsage = Double.parseDouble(property.getProperty("maxDiskUsage"));  // disk usage in percent
            path = property.getProperty("path"); // Folder for search and remove files
            fileType = property.getProperty("fileType");  // Type of file for remove
            diskName = property.getProperty("diskName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        long currTime = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        double dskusage = Check.diskUsage(diskName);

        System.out.println("disk Used at " + dskusage + " %");  // Debug used disk space


        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**"+fileType);


        if (dskusage > maxDiskUsage) {
            Files.walk(Paths.get(path))
                    .filter(matcher::matches)
                    .map(Path::toFile)
                    .filter(file -> file.length() > maxFileSize && file.lastModified() < currTime)
                    .forEach(FileUtil::remove);
        }
    }
}
