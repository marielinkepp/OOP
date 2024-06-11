package test.java.sample;

import main.java.sample.UserFinder;
import main.java.sample.UserStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserFinderTest {

  private static final String SAMPLE_NORMAL = "" +
      "mbakhoff Märt Bakhoff\n" +
      "ssaan Simmo Saan\n" +
      "mlepp Marina Lepp\n";

  private static final String SAMPLE_STRANGE = "" +
      "potato\n" +
      "\n" +
      "           \n" +
      "mbakhoff Märt Bakhoff";

  @Disabled
  @Test
  public void findsSimmo() throws Exception {
    UserFinder finder = new UserFinder(new TestUserStore(SAMPLE_NORMAL));
    String name = finder.findFullNameByUsername("ssaan");
    assertEquals("Simmo Saan", name);
  }

  @Disabled
  @Test
  public void failsOnUnknownUser() {
    assertThrows(NoSuchElementException.class, () -> {
      UserFinder finder = new UserFinder(new TestUserStore(SAMPLE_NORMAL));
      finder.findFullNameByUsername("ivaat");
    });
  }

  @Disabled
  @Test
  public void weirdLinesIgnored() throws Exception {
    UserFinder finder = new UserFinder(new TestUserStore(SAMPLE_STRANGE));
    String name = finder.findFullNameByUsername("mbakhoff");
    assertEquals("Märt Bakhoff", name);
  }

  // overrides UserStore not to use the file system.
  // much easier to test with, because there's no need
  // to create files and clean them up after the tests.
  static class TestUserStore extends UserStore {

    private final String data;

    TestUserStore(String data) {
      this.data = data;
    }

    @Override
    public String read() {
      return data;
    }
  }
}
