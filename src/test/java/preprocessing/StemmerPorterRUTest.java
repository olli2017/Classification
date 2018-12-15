package test.java.preprocessing;

import java.util.stream.Stream;
import main.java.preprocessing.StemmerPorterRU;
import org.junit.Test;

public class StemmerPorterRUTest {

  @Test
  public void stem() {
    Stream<String> dictStream = Stream
        .of("работать", "работаю", "делать", "делаю", "делали", "дело");

    dictStream.forEach(s -> System.out.println(s + " -> " + StemmerPorterRU.stem(s)));
  }
}