package l4;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static l4.Triangle.TYPE.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangleTest {

    // generates a fan of n points in distance 1 around origin.
    private List<Vec2> generateFan(int n) {
        List<Vec2> fan = new ArrayList<>();
        var angle = n / 2.0 * Math.PI;
        for (int i = 0; i < n; i++)
            fan.add(new Vec2(Math.cos(angle), Math.sin(angle)));
        return fan;
    }

    @TestFactory
    Iterable<DynamicTest> triangleTestFactory() {
        // points
        var fanEqu = generateFan(6); // 60 degree
        var fanIso = generateFan(2); // 30 degree triangle
        var fanOther = IntStream.range(0, fanIso.size())
                .mapToObj(i -> {
                    var scale = 1.0 + ((double) i / fanIso.size());
                    return fanIso.get(i).scaled(scale);
                }).collect(Collectors.toList());

        var tests = new ArrayList<DynamicTest>();
        var origin = new Vec2(0.0, 0.0);
        var offset = new Vec2(-135.0, +227.0);

        var typeToFan = Map.of(EQUILATERAL, fanEqu,
                               ISOSCELES, fanIso,
                               OTHER, fanOther);

        for (var entry : typeToFan.entrySet()) {
            var type = entry.getKey();
            var fan = entry.getValue();

            for (int i = 0; i < fan.size(); i++) {
                Vec2 p1 = fan.get(i);
                Vec2 p2 = fan.get((i + 1) % fan.size());

                tests.add(DynamicTest.dynamicTest(type.name() + " " + i, () ->
                        assertEquals(type, Triangle.getTriangleType(origin,
                                                                    p1,
                                                                    p2))
                ));

                tests.add(DynamicTest.dynamicTest(type.name() + " translated " + i, () ->
                        assertEquals(type, Triangle.getTriangleType(origin.translated(offset),
                                                                    p1.translated(offset),
                                                                    p2.translated(offset)))
                ));
            }
        }

        return tests;
    }
}