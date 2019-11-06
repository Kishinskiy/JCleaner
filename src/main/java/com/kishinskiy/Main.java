package com.kishinskiy;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static final long maxSize = 4163500L; // Maximum file size in bites. If file size more this value it will  be removed
    private static final int maxDiskUsage = 20;  // disk usage in percent
    private static final String path = "/Users/kishinskiy/Downloads"; // Folder for search and remove files
    private static final String fileType = ".pdf";  // Type of file for remove
    private static final String diskName = "/"; // Name of Disk for check used space size

    private static int diskUsage() {
        File file = new File(diskName);
        long totalSpace = file.getTotalSpace() ;
        long usedSpace = file.getUsableSpace() ;

        return (int) (((double)usedSpace/(double)totalSpace) * 100); // return percent of used disk space
    }

    public static void main(String[] args) throws IOException {
        long currTime = new Date().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        System.out.println("disk Used at " + diskUsage() + " %");  // Debug used disk space

        if (diskUsage() > maxDiskUsage) {
            Files.walk(Paths.get(path))
                    .filter(file -> file.toString().endsWith(fileType))
                    .map(Path::toFile)
                    .filter(file -> file.length() > maxSize && file.lastModified() < currTime)
                    .forEach(x -> {
                        System.out.println(x.getName() + " " + x.length() + " " + sdf.format(x.lastModified()));
//                        x.delete(); // delete founded files
//                        System.out.println("File removed: " + x.getName());
                    });
        }
    }
}
