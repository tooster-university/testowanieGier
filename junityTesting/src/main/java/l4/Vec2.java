package l4;

public class Vec2 {
    public float x;
    public float y;

    public Vec2() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public Vec2(float x, float y) {
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
        float v0 = b.x - a.x;
        float v1 = b.y - a.y;
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

    // Compare two vectors
    public boolean equals(Vec2 other) {
        return (this.x == other.x && this.y == other.y);
    }

}