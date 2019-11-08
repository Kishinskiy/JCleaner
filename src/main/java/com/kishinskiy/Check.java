package com.kishinskiy;

import java.io.File;

class Check {
    /////// get used space on disk /////////
        static double diskUsage(String diskName) {
            File file = new File(diskName);
            long totalSpace = file.getTotalSpace() ;
            long usedSpace = file.getUsableSpace() ;

            return ((double)usedSpace/(double)totalSpace) * 100; // return percent of used disk space
        }
}


