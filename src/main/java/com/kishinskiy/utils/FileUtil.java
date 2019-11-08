package com.kishinskiy.utils;

import java.io.File;
import java.util.Date;

public class FileUtil {

    public static void remove(File x) {
        System.out.println(x.getName() + " " + x.length() + " " + new Date(x.lastModified()));
//                        x.delete(); // delete founded files
//                        System.out.println("File removed: " + x.getName());
    }
}
