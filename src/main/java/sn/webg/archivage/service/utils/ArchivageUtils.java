package sn.webg.archivage.service.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ArchivageUtils {

    public void clearMataData(Map<String, Object> metaDataList) {

        metaDataList.remove("page");

        metaDataList.remove("size");

        metaDataList.remove("sort");

        metaDataList.remove("accessMetaData");

        metaDataList.remove("name");

        metaDataList.remove("reference");

        metaDataList.remove("folderTypeIds");

        metaDataList.remove("folderIds");

        metaDataList.remove("year");
        metaDataList.remove("documentIds");
        metaDataList.remove("documentTypeIds");

    }
}
