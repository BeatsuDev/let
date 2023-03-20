package no.ntnu.let.letapi.controller.listing;

import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.model.listing.Image;
import no.ntnu.let.letapi.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(ImageController.class);
    private final ImageService imageService;
    private final ListingMapper listingMapper;

    @Autowired
    public ImageController(ImageService imageService, ListingMapper listingMapper) {
        this.imageService = imageService;
        this.listingMapper = listingMapper;
    }

    @PostMapping
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile file) {
        logger.info("Uploading image with name: " + file.getOriginalFilename());
        Image image;
        try {
            image = imageService.saveImageFile(file);
        } catch (IllegalArgumentException e) {
            logger.warn("Could not upload image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Could not upload image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        logger.info("Image uploaded successfully: " + image.getFileName() + " with id: " + image.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(listingMapper.toImageDTO(image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getImage(@PathVariable Long id) {
        Image image = imageService.getImageFile(id);
        if (image == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(listingMapper.toImageDTO(image));
    }
}
