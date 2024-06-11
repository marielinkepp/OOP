package task;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ForecastDownloader {


    public static String downloadForecast(String FORECAST_LOCATION) throws IOException {

        // download the latest forecast from yr.no weather service
        try (InputStream stream = new URL(FORECAST_LOCATION).openStream()) {
           return main.java.task.IOUtils.toString(stream, UTF_8);
        } catch (MalformedURLException e) {
            try (InputStream is = ForecastDownloader.class.getResourceAsStream("/forecast.xml")) {
                return main.java.task.IOUtils.toString(is, UTF_8);
            }
        }
    }

    public static void main(String[] args) {

    }

}
