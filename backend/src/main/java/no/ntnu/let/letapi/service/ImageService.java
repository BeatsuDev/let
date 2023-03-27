package no.ntnu.let.letapi.service;

import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.repository.listing.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

/**
 * Service for images
 */
@Service
public class ImageService {
    private final String IMAGE_PATH_BASE = "uploads/images/";
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Save an image file
     * @param file The file to save
     * @return The saved image
     * @throws RuntimeException If the file could not be saved
     */
    public Image saveImageFile(MultipartFile file) throws RuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new IllegalArgumentException("File has no name");
        int lastDot = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        if (lastDot == -1) throw new IllegalArgumentException("File has no extension");
        String extension = fileName.substring(lastDot);

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + extension;

        Path imagePath = Path.of(IMAGE_PATH_BASE + newFileName);
        try {
            file.transferTo(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not save image");
        }

        Image image = new Image();
        image.setFileName(newFileName);
        imageRepository.save(image);

        return image;
    }


    /**
     * Get an image by id
     * @param id The id of the image
     * @return The image
     */
    public Image getImageFile(long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
