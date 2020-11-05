package ru.progmatik.main.webclient;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import ru.progmatik.main.pojo.DownloadFileInfoJson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static ru.progmatik.main.other.UtilClass.checkUrlFoRedirect;

/**
 * веб-клиент для получения списка файлов ФИАС
 */
public class FiasClient extends WebServiceGatewaySupport {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${client.default-uri}")
    private String fiasurl;

    @Value("${client.readTimeoutMsec:1000}")
    private long readTimeoutMsec;

    @Value("${client.connTimeoutMsec:1000}")
    private int connTimeoutMsec;

    public List<DownloadFileInfoJson> getAllDownloadFileList() throws IOException {
        LOGGER.info("Requesting FIAS files list");
        String realURL = checkUrlFoRedirect(fiasurl);
        URL hh= new URL(realURL);
        URLConnection connection = hh.openConnection();
        //String responseMessage = ((HttpURLConnection)connection).getContent().toString(); // ResponseMessage();
        InputStream is = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String readString = in.readLine();
        JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(readString.getBytes())));
        List<DownloadFileInfoJson> fileInfoList = new ArrayList<>();
        reader.setLenient(true);
        reader.beginArray();
        while (reader.hasNext()) {
            fileInfoList.add(readFileInfo(reader));
        }
        reader.endArray();
        return  fileInfoList;
    }

    private DownloadFileInfoJson readFileInfo(JsonReader reader) throws IOException {
      DownloadFileInfoJson fileInfoJson = new DownloadFileInfoJson();

      reader.beginObject();
      while (reader.hasNext()) {
          String name = reader.nextName();

          switch (name) {
              case "VersionId":
                  fileInfoJson.setVersionId(reader.nextInt());
                  break;
              case "GarXMLFullURL":
                  fileInfoJson.setGarXMLFullURL(reader.nextString());
                  break;
              case "GarXMLDeltaURL":
                  fileInfoJson.setGarXMLDeltaURL(reader.nextString());
                  break;
              default:
                  reader.skipValue();
                  break;
          }
      }
      reader.endObject();
      return fileInfoJson;
    }
}
