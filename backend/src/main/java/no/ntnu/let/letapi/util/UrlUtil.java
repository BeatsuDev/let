package no.ntnu.let.letapi.util;

/**
 * Utility class for urls
 */
public class UrlUtil {
    /**
     * Get the base url
     * @return The base url
     */
    public static String getBaseUrl() {
        return "http://localhost:8080";
    }

    /**
     * Get the url for a listing
     * @param filename The filename of the image
     * @return The url for the image
     */
    public static String getImageUrl(String filename) {
        return getBaseUrl() + "/uploads/images/" + filename;
    }
}
