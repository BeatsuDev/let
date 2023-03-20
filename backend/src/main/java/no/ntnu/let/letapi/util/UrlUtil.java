package no.ntnu.let.letapi.util;

public class UrlUtil {
    public static String getBaseUrl() {
        return "http://localhost:8080";
    }

    public static String getImageUrl(String filename) {
        return getBaseUrl() + "/uploads/images/" + filename;
    }
}
