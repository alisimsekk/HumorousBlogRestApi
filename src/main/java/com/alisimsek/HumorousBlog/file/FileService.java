package com.alisimsek.HumorousBlog.file;

import com.alisimsek.HumorousBlog.configuration.HumorousProperties;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final HumorousProperties humorousProperties;
    private Tika tika = new Tika();

    public String saveBase64ImageStringAsFile(String image) {
        String fileName = UUID.randomUUID().toString();

        Path path = getProfileImagePath(fileName);

        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            byte [] base64decoded = decodedImage(image);
            outputStream.write(base64decoded);
            outputStream.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String detectDocumentType(String value) {
        String documentType = tika.detect(decodedImage(value));
        return documentType;
    }

    public byte[] decodedImage(String encodedImage){
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

    public void deleteProfileImage(String image) {
        if (image == null) return;
        Path path = getProfileImagePath(image);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getProfileImagePath(String fileName){
        return Paths.get(humorousProperties.getStorage().getRoot(),humorousProperties.getStorage().getProfile(), fileName);
    }
}
