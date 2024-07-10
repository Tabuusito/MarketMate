package esei.uvigo.demo.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    public String store(MultipartFile file) {
        System.out.println("hello from storage");
        
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "-" + filename;
        Path destinationFile;

        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            destinationFile = Paths.get("uploads").resolve(
                    Paths.get(newFileName))
                    .normalize().toAbsolutePath();
            
            if (!destinationFile.getParent().equals(Paths.get("uploads").toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }
            if (!Files.exists(destinationFile.getParent())) {
                Files.createDirectories(destinationFile.getParent());
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + newFileName, e);
        }
        
        // Devolver solo el nombre del archivo, no la ruta completa
        return newFileName;
    }
}

