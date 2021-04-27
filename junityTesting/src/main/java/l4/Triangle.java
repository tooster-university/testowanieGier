package l4;

import org.jetbrains.annotations.NotNull;

public class Triangle {

    private static final double EPSILON = 0.1e-5;

    public static @NotNull TYPE getTriangleType(@NotNull Vec2 p1, @NotNull Vec2 p2, @NotNull Vec2 p3) {
        double s1 = Vec2.distance(p1, p2);
        double s2 = Vec2.distance(p2, p3);
        double s3 = Vec2.distance(p1, p3);

        int equalPairs = 0;
        equalPairs += Math.abs(s1 - s2) <= EPSILON ? 1 : 0;
        equalPairs += Math.abs(s2 - s3) <= EPSILON ? 1 : 0;
        equalPairs += Math.abs(s1 - s3) <= EPSILON ? 1 : 0;
        switch (equalPairs) {
            case 3:
            	return TYPE.EQUILATERAL;
            case 2:
            case 1:
            	return TYPE.ISOSCELES;
            default:
            	return TYPE.OTHER;
        }
    }

    enum TYPE {EQUILATERAL, ISOSCELES, OTHER}
}
