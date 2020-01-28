import java.io.IOException;
import java.nio.CharBuffer;

/**
 * A Readable class that always throw a exception.
 */
public class ReadableFail implements Readable {


  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
