package ru.progmatik.main.other;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ExtractArchFileThread implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ZipEntry entry;

    private File UNPACKFOLDER;

    private ZipFile zipFile;

    public ExtractArchFileThread(ZipEntry zipEntry, File UNPACKFOLDER, ZipFile zipFile){
        this.entry = zipEntry;
        this.UNPACKFOLDER = UNPACKFOLDER;
        this.zipFile = zipFile;
    }

    @Override
    public void run(){
        File file = new File(UNPACKFOLDER, entry.getName());
        if (entry.isDirectory()) {
            file.mkdirs();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            BufferedOutputStream dest = null;
            FileOutputStream fos = null;
            InputStream in = null;
            FileOutputStream fileOutputStream = null;
            BufferedOutputStream out = null;
            try{
                in = zipFile.getInputStream(entry);
                fileOutputStream = new FileOutputStream(file);
                out = new BufferedOutputStream(fileOutputStream);
                IOUtils.copy(in, out);
            }
            catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString());
            }
            finally {
                try {
                    in.close();
                    out.close();
                    fileOutputStream.close();
                    in = null;
                    out = null;
                    fileOutputStream = null;
                    this.entry = null;
                    this.zipFile = null;
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.toString());
                }
            }
        }
    }
}
