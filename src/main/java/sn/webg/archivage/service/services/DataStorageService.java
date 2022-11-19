package sn.webg.archivage.service.services;

import java.io.InputStream;

public interface DataStorageService {

    String storeFile(String type, String reference, String expention, InputStream inputStream);
}
