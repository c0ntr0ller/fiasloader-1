package ru.progmatik.main.services;

//import fias.wsdl.DownloadFileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.progmatik.main.pojo.DownloadFileInfoJson;
import ru.progmatik.main.webclient.FiasClient;
import ru.progmatik.main.other.UtilClass;

import javax.xml.soap.SOAPException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * класс работает по расписанию, скачивает список файлов ФИАС, сравнивает с имеющимися в архиве,
 * решает какие надо скачать и скачивает их
 */
@Service
public class DownloadFilesSheduler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FiasClient fiasClient;

    @Value("${archDir:archive}")
    String archDir;

    @Value("${workDir:work}")
    String workDir;

    private List<DownloadFileInfoJson> fiasFilesList = new ArrayList<>();
    private Map<Integer,File> archFilesMap = new HashMap<>();
    private Map<Integer,File> workFilesMap = new HashMap<>();

    @Scheduled(fixedRateString = "${downloadperiod:360000000}") // every 100 hour
    public void checkAndGetFiasFiles() throws SOAPException, IOException {

        if (fiasFilesList != null){
            fiasFilesList.clear();
        }

        fiasFilesList = fiasClient.getAllDownloadFileList();

        if(fiasFilesList == null || fiasFilesList.isEmpty()){
            logger.error("Empty fias files list!");
            return;
        }

        Map<Integer,String> filesMapForDownload = getFileMapForDownload();

        // если список на скачивание непустой - запускаем скачивание
        downloadFiles(filesMapForDownload);
    }

    private void downloadFiles(Map<Integer, String> filesMapForDownload) {
        // run by sorted list of versions
        logger.info(String.format("Start downloading %d file(s)", filesMapForDownload.size()));

        for (Integer versionId : filesMapForDownload.keySet().stream().sorted().collect(Collectors.toList())) {
            String url = filesMapForDownload.get(versionId);

            File tmpDir = new File("tmp");
            if(!tmpDir.exists()){
                tmpDir.mkdir();
            }

            String tmpfilename =  "tmp" + File.separatorChar + versionId.toString() + ".zip";

            try {
                logger.info(String.format("Download url %s, file %s ...", url, tmpfilename));
                UtilClass.downLoadFileFromURL(tmpfilename, url);
                File tmpFile = new File(tmpfilename);
                if(tmpFile.exists()) {
                    logger.info(String.format("Move file from %s to %s", tmpfilename, workDir + File.separatorChar + tmpFile.getName()));
//                    tmpFile.renameTo(new File(workDir + File.separatorChar + tmpFile.getName()));
                    Files.copy(tmpFile.toPath(), Paths.get(workDir + File.separatorChar + tmpFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                    if(Files.exists(Paths.get(workDir + File.separatorChar + tmpFile.getName()))){
                        Files.delete(tmpFile.toPath());
                        logger.info("File moved successfully, source file deleted");
                    } else {
                        logger.error("File not moved");
                    }
                }
            } catch (IOException e) {
                logger.error("Exception while downloading file " + url);
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        logger.info("Downloading finished");
    }

    /**
     * get map of files and their version numbers
     * @return
     */
    private Map<Integer, String> getFileMapForDownload(){
        Map<Integer, String> fileMapForDownload = new HashMap<>();

        if(archFilesMap != null){
            archFilesMap.clear();
        }

        if(workFilesMap != null){
            workFilesMap.clear();
        }

        // получаем список файлов в архивной папке
        if(archDir == null || archDir.isEmpty()) {
            archDir = "archive";
        }

        archFilesMap = UtilClass.getDirFiles(archDir, "zip");

        // получаем список файлов в папке для обработки (возможно какие-то еще не обработались либо скачаны частично)
        if(workDir == null || workDir.isEmpty()) {
            workDir = "work";
        }
        workFilesMap = UtilClass.getDirFiles(workDir, "zip");

        // определяем какие файлы надо скачать

        // если обе папки пусты - возвращем только имя последнего ПОЛНОГО архива
        if(archFilesMap.isEmpty() && workFilesMap.isEmpty()){

            DownloadFileInfoJson totalArch  = fiasFilesList
                    .stream()
                    .max(Comparator.comparingInt(DownloadFileInfoJson::getVersionId)).get();
            fileMapForDownload.put(totalArch.getVersionId(), totalArch.getGarXMLFullURL());
            logger.info("Arch and Work empty, downloading full file");
        }else {
            // Нам нужен максимальный номер скачанной версии
            // объединяем все списки файлов в один
            workFilesMap.putAll(archFilesMap);
            // находим максимальный номер версии
            Integer maxVersion = workFilesMap.keySet().stream().max(Comparator.naturalOrder()).get();

            // все файлы с версией выше добавляем на скачивание
            for (DownloadFileInfoJson downloadFileInfo : fiasFilesList) {
                if(downloadFileInfo.getVersionId() > maxVersion){
                    // добавляем только ИНКРЕМЕНТЫ
                    fileMapForDownload.put(downloadFileInfo.getVersionId(), downloadFileInfo.getGarXMLDeltaURL());
                }
            }
            logger.info("Files for download: " + fileMapForDownload.size());
        }
        return fileMapForDownload;
    }

}
