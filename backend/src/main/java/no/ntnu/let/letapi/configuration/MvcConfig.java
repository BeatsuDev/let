package no.ntnu.let.letapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used to expose the uploads directory to the web server.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    /**
     * Exposes the uploads directory to the web server.
     * @param registry The resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("uploads/images", registry);
    }

    /**
     * Exposes a directory to the web server.
     * @param dirName The name of the directory.
     * @param registry The resource handler registry.
     */
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");

        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:" + uploadPath + "/");
    }
}
