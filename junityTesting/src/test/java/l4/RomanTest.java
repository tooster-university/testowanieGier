package l4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RomanTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 5000})
    void toRomanThrows(int number) {
        assertThrows(IllegalArgumentException.class, () -> Roman.toRoman(number));
    }

    @Test
    @ExtendWith(FileExtension.class)
    void toRomanCheckAll(@FileExtension.File(path = "roman.txt") String numbers){
        var actual = IntStream.range(1, 5000).mapToObj(Roman::toRoman);
        assertLinesMatch(Arrays.stream(numbers.split("\n")), actual);
    }
}