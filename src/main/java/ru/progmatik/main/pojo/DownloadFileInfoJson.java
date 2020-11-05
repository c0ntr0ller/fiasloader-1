package ru.progmatik.main.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileInfoJson {
    Integer VersionId;
    String GarXMLFullURL;
    String GarXMLDeltaURL;

    public Integer getVersionId() {
        return VersionId;
    }

    public void setVersionId(Integer versionId) {
        VersionId = versionId;
    }

    public String getGarXMLFullURL() {
        return GarXMLFullURL;
    }

    public void setGarXMLFullURL(String garXMLFullURL) {
        GarXMLFullURL = garXMLFullURL;
    }

    public String getGarXMLDeltaURL() {
        return GarXMLDeltaURL;
    }

    public void setGarXMLDeltaURL(String garXMLDeltaURL) {
        GarXMLDeltaURL = garXMLDeltaURL;
    }
}
