package l4;

public class Vec2 {
    public double x;
    public double y;

    public Vec2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns squared euclidean distance between two points
     *
     * @param a first point
     * @param b second point
     *
     * @return squared euclidean distance between a and b
     */
    public static double distanceSquared(Vec2 a, Vec2 b) {
        double v0 = b.x - a.x;
        double v1 = b.y - a.y;
        return v0 * v0 + v1 * v1;
    }

    /**
     * Returns euclidean distance of two points
     *
     * @param a first point
     * @param b second point
     *
     * @return distance between two points
     */
    public static double distance(Vec2 a, Vec2 b) {
        return Math.sqrt(distanceSquared(a, b));
    }

    public Vec2 scaled(double scale) { return new Vec2(x * scale, y * scale); }

    public Vec2 translated(Vec2 offset) { return new Vec2(x + offset.x, y + offset.y); }

    // Compare two vectors
    public boolean equals(Vec2 other) {
        return (this.x == other.x && this.y == other.y);
    }

}