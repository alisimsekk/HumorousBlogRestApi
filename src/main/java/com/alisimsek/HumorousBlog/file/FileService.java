package com.alisimsek.HumorousBlog.file;

import com.alisimsek.HumorousBlog.configuration.HumorousProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    public final HumorousProperties humorousProperties;
    public String saveBase64ImageStringAsFile(String image) {
        String fileName = UUID.randomUUID().toString();

        Path path = Paths.get(humorousProperties.getStorage().getRoot(),humorousProperties.getStorage().getProfile(), fileName);

        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            byte [] base64decoded = Base64.getDecoder().decode(image.split(",")[1]);
            outputStream.write(base64decoded);
            outputStream.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
