import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import org.hamcrest.Matcher;
import org.hamcrest.text.StringContainsInOrder;
import org.junit.Ignore;
import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  // This was added as a convenience method in Hamcrest source, but it hasn't been released yet.
  private static Matcher<String> stringContainsInOrder(String... substrings) {
    return new StringContainsInOrder(Arrays.asList(substrings));
  }

  // TODO(andrewkrieger): Throw a special Dart TypeError instead of reusing java.RuntimeException.
  private static final Class<? extends Throwable> err = RuntimeException.class;

  @Test()
  public void testDowncastStringAtReturnNoError() {
    try {
      __TopLevel.downcastStringAtReturnNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Test()
  public void testDowncastStringAtReturnError() {
    try {
      __TopLevel.downcastStringAtReturnError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(), stringContainsInOrder("TypeError", "double", "subtype", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtReturnNoError() {
    try {
      __TopLevel.downcastListAtReturnNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtReturnError() {
    try {
      __TopLevel.downcastListAtReturnError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(),
          stringContainsInOrder("TypeError", "List", "int", "subtype", "List", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }

  @Test()
  public void testDowncastStringAtCallNoError() {
    try {
      __TopLevel.downcastStringAtCallNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Test()
  public void testDowncastStringAtCallError() {
    try {
      __TopLevel.downcastStringAtCallError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(), stringContainsInOrder("TypeError", "double", "subtype", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtCallNoError() {
    try {
      __TopLevel.downcastListAtCallNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtCallError() {
    try {
      __TopLevel.downcastListAtCallError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(),
          stringContainsInOrder("TypeError", "List", "int", "subtype", "List", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }

  @Test()
  public void testDowncastStringAtLocalNoError() {
    try {
      __TopLevel.downcastStringAtLocalNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Test()
  public void testDowncastStringAtLocalError() {
    try {
      __TopLevel.downcastStringAtLocalError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(), stringContainsInOrder("TypeError", "double", "subtype", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtLocalNoError() {
    try {
      __TopLevel.downcastListAtLocalNoError();
    } catch (Throwable t) {
      fail("Expected no error, but got: " + t);
    }
  }

  @Ignore("List type not handled correctly.")
  @Test()
  public void testDowncastListAtLocalError() {
    try {
      __TopLevel.downcastListAtLocalError();
    } catch (Throwable t) {
      assertTrue("Expected a " + err.getName() + ", but got: " + t, err.isInstance(t));
      assertThat(t.getMessage(),
          stringContainsInOrder("TypeError", "List", "int", "subtype", "List", "String"));
      return;
    }
    fail("Expected a " + err.getName() + ", but got no error");
  }
}
