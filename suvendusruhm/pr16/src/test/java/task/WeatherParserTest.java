package task;

import main.java.task.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherParserTest {

  // you can hardcode dates to test against:
  // LocalDateTime ldt = LocalDateTime.parse("2018-02-12T00:00:00");

  //@Test
  public static void testDataMustBeAvailable() throws Exception {
    assertNotNull(readTestForecast());
  }

  // TODO add your tests here

  private static String readTestForecast() throws IOException {
    // reads src/test/resources/forecast.xml to string
    try (InputStream is = WeatherParserTest.class.getResourceAsStream("/forecast.xml")) {
      return IOUtils.toString(is, UTF_8);
    }
  }

  private static void findsCorrectTemperatureFromForecast(WeatherParser wp) throws Exception {
    assertEquals(-6, wp.temperatureAt(LocalDateTime.parse("2018-02-12T06:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
  }

  private static void throwsExceptionIfTemperatureNotFoundForGivenDate(WeatherParser wp) throws Exception {
   assertNotNull(wp.temperatureAt(LocalDateTime.parse("2028-02-12T06:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))));
  }

  public static void main(String[] args) throws Exception {
    /*  Create a mock forecast downloader for testing by overriding methods in the new forecast downloading class.
        The mock shouldn't download anything from the actual *yr.no* weather service.
        The `forecast.xml` in src/test/resources should be used instead (see the `readTestForecast` method in `WeatherParserTest`).

        Don't use mockito for this task.

        Create at least the following tests:
        1. findsCorrectTemperatureFromForecast
        2. throwsExceptionIfTemperatureNotFoundForGivenDate*/
    testDataMustBeAvailable();

    WeatherParser wp = new WeatherParser("/forecast.xml");
    findsCorrectTemperatureFromForecast(wp);
    throwsExceptionIfTemperatureNotFoundForGivenDate(wp);

  }

}
