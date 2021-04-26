import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

public class ExtensionTest {

    @Test
    @ExtendWith(FileExtension.class)
    void test(@FileExtension.File(path = "abcd.txt") String content) {
        System.out.println(content);
        Assertions.assertEquals(content, "abcdefghijklmnopqrstuvwxyz");
    }
}
