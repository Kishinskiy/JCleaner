package com.kishinskiy;

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
    private static int maxDiskUsage;
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
            maxDiskUsage = Integer.parseInt(property.getProperty("maxDiskUsage"));  // disk usage in percent
            path = property.getProperty("path"); // Folder for search and remove files
            fileType = property.getProperty("fileType");  // Type of file for remove
            diskName = property.getProperty("diskName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        long currTime = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        System.out.println("disk Used at " + diskUsage() + " %");  // Debug used disk space

        if (Check.diskUsage(diskName) > maxDiskUsage) {
            Files.walk(Paths.get(path))
                    .filter(file -> file.toString().endsWith(fileType))
                    .map(Path::toFile)
                    .filter(file -> file.length() > maxFileSize && file.lastModified() < currTime)
                    .forEach(x -> {
                        System.out.println(x.getName() + " " + x.length() + " " + sdf.format(x.lastModified()));
//                        x.delete(); // delete founded files
//                        System.out.println("File removed: " + x.getName());
                    });
        }
    }
}
