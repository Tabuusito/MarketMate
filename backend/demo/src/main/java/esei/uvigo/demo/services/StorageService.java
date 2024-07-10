package esei.uvigo.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface StorageService {

	public String store(MultipartFile file);
}
