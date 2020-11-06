package ru.progmatik.main.services;

import com.github.junrar.exception.RarException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fias.*;
import ru.progmatik.main.DAO.*;
import ru.progmatik.main.other.XMLFileReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * сервис предназначен для обработки скачанных файлов
 */
@Service
public class ProceedFileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${batchsize:1000}")
    private int BATCH_SIZE;


    private static final File UNPACKFOLDER = new File("Z:\\Work\\Incoming\\_FIASLoader\\unpack");

    @Value("${archDir:archive}")
    String archDir;

    public void proceedFiasArchFile(final File fiasArchFile){

        try(Connection connection = dbService.getConnection()){
            boolean unpackSuccess = true;

            if(fiasArchFile != null){
                unpackSuccess = extractArchFile(fiasArchFile);
            }
            if(unpackSuccess){
                for (File sourceFile: Objects.requireNonNull(UNPACKFOLDER.listFiles())) {
                    if(sourceFile.isDirectory()){
                        for (File file: Objects.requireNonNull(sourceFile.listFiles())){
                            if(FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml")) {
                                String fileName = FilenameUtils.getName(file.getName());
                                proceedFiasObj(file, connection, fileName);
                            }
                        }
                    }
                    if(FilenameUtils.getExtension(sourceFile.getName()).equalsIgnoreCase("xml")) {
                        String fileName = FilenameUtils.getName(sourceFile.getName());
                        proceedFiasObj(sourceFile, connection, fileName);
                    }
                }
                clearUnpackFolder();
            }
            else{
                logger.info("Unpack unsuccess...");
                logger.info(String.format("Unpack file %s unsuccess...", fiasArchFile.getName()));
            }

            if(fiasArchFile != null){
                fiasArchFile.renameTo(new File(archDir + File.separatorChar + fiasArchFile.getName()));
            }
        } catch (Exception e) {
            logger.error("proceedFiasArchFile error", e);
            e.printStackTrace();
        }
    }

    @Autowired
    DAOBatchInsert DAOBatchInsert;
    @Autowired
    DBService dbService;

    private void proceedFiasObj(File sourceFile, Connection connection, String fileName){
        long totalCnt = 0;

        XMLFileReader xmlFileReader = null;
        try {

           String shortName =  fileName.substring(0, fileName.length() - 50);

            FiasObjectFactory fiasObjectFactory = new FiasObjectFactory();
            Class fiasClass = fiasObjectFactory.getFiasObjectClass(shortName);
            xmlFileReader = new XMLFileReader(sourceFile, fiasClass);

            //long start_nanotime = System.nanoTime();

            // бежим по файлу и создаем объекты
            while (xmlFileReader.hasNext()) {
//                logger.info("Address objects read started...");

                List<FiasObject> objectList = xmlFileReader.readAddrObjFromStream(BATCH_SIZE);

                totalCnt = totalCnt + objectList.size();

                DAOBatchInsert.insertFiasObjArray(objectList, connection);


                /*long end_nanotime = System.nanoTime();
                long duration = ((end_nanotime - start_nanotime) / 1000000000);
                long diff = 0;
                if (duration > 0) {
                    diff = totalCnt / duration;
                }*/

                //logger.info(String.format("Address objects inserted: %d; Avg. speed: %d records/sec", totalCnt, diff));
            }
            logger.info("File " + fileName + " insert finished");
        } catch (Exception e) {
            logger.error("proceedFiasObj error: " + fileName, e);
            e.printStackTrace();
        }
        finally {
            try {
                if (xmlFileReader != null)
                xmlFileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean extractArchFile(File fiasArchFile) throws IOException, RarException {
        if(fiasArchFile == null) return false;

        if (!Files.exists(fiasArchFile.toPath())) {
            throw new IOException(String.format("File not found: %s", fiasArchFile.toPath()));
        }

        prepareUnpackFolder();

        logger.info("extract file" + fiasArchFile.toPath());

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fiasArchFile));
        ZipEntry zipEntry = zis.getNextEntry();
        String nextFileName;

        //тестовый коментраи
        while (zipEntry != null) {
            nextFileName = zipEntry.getName();
            if (!nextFileName.contains("Schemas")){
                String fileName = FilenameUtils.getName(nextFileName);
                int endShortFilename = fileName.length() - 50;
                String shortFileName = fileName.substring(0, endShortFilename);
                if (shortFileName.equals("AS_ADDR_OBJ") || shortFileName.equals("AS_ADDR_OBJ_TYPES") || shortFileName.equals("AS_HOUSE_TYPES") ||
                        shortFileName.equals("AS_HOUSES") || shortFileName.equals("AS_HOUSES_PARAMS") || shortFileName.equals("AS_ADM_HIERARCHY") ||
                        shortFileName.equals("AS_MUN_HIERARCHY") || shortFileName.equals("AS_ADDR_OBJ_PARAMS") || shortFileName.equals("AS_OBJECT_LEVELS") ||
                        shortFileName.equals("AS_PARAM_TYPES"))
                {
                    File newFile = new File(UNPACKFOLDER + File.separator + nextFileName);
                    if (zipEntry.isDirectory()) {
                        newFile.mkdir();
                    } else {
                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    fos.close();
                    }
                }
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        logger.info(fiasArchFile.toPath() + " extracted");

        return true;
    }

    private void prepareUnpackFolder() {
        if (!UNPACKFOLDER.exists()) {
            UNPACKFOLDER.mkdir();
        }

        clearUnpackFolder();
    }
    private void clearUnpackFolder(){
        for(File file : Objects.requireNonNull(UNPACKFOLDER.listFiles())){
            recursiveDelete(file);
        }
        logger.info("Unpack folder cleared");
    }

    private void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists())
            return;
        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }
}
