/**
 * This class represents fractions of form n/d where n and d are long integer
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction>, Cloneable {

    /**
     * Main method. Different tests.
     */
    public static void main(String[] param) {
        System.out.println(valueOf("2/x"));
    }

    private long numerator;
    private long denominator;

    /**
     * Constructor.
     *
     * @param a numerator
     * @param b denominator > 0
     */
    public Lfraction(long a, long b) {
        if (b <= 0) throw new ArithmeticException("Denominator cannot be 0!");
        long g = gcd(a, b);
        this.numerator = a / g;
        this.denominator = b / g;
    }

    /**
     * Private helping method to get greatest common divisor.
     *
     * @param m param
     * @param n param
     */
    private static long gcd(long m, long n) {
        return (n == 0) ? m : gcd(n, Math.abs(m) % n);
    }

    /**
     * Public method to access the numerator field.
     *
     * @return numerator
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * Public method to access the denominator field.
     *
     * @return denominator
     */
    public long getDenominator() {
        return denominator;
    }

    /**
     * Conversion to string.
     *
     * @return string representation of the fraction
     */
    @Override
    public String toString() {
        return String.format("%d/%d", numerator, denominator);
    }

    /**
     * Equality test.
     *
     * @param m second fraction
     * @return true if fractions this and m are equal
     */
    @Override
    public boolean equals(Object m) {
        return (m instanceof Lfraction) && this.compareTo((Lfraction) m) == 0;
    }

    /**
     * Hashcode has to be equal for equal fractions.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Sum of fractions.
     *
     * @param m second addend
     * @return this+m
     */
    public Lfraction plus(Lfraction m) {
        long lcm = m.denominator * denominator / gcd(m.denominator, denominator);
        return new Lfraction(m.numerator * (lcm / m.denominator) + numerator * (lcm / denominator), lcm);
    }

    /**
     * Multiplication of fractions.
     *
     * @param m second factor
     * @return this*m
     */
    public Lfraction times(Lfraction m) {
        return new Lfraction(numerator * m.numerator, denominator * m.denominator);
    }

    /**
     * Inverse of the fraction. n/d becomes d/n.
     *
     * @return inverse of this fraction: 1/this
     */
    public Lfraction inverse() {
        if (numerator == 0) throw new ArithmeticException("Divide by zero");
        return new Lfraction(numerator > 0 ? denominator : -denominator, Math.abs(numerator));
    }

    /**
     * Opposite of the fraction. n/d becomes -n/d.
     *
     * @return opposite of this fraction: -this
     */
    public Lfraction opposite() {
        return new Lfraction(-numerator, denominator);
    }

    /**
     * Difference of fractions.
     *
     * @param m subtrahend
     * @return this-m
     */
    public Lfraction minus(Lfraction m) {
        return plus(m.opposite());
    }

    /**
     * Quotient of fractions.
     *
     * @param m divisor
     * @return this/m
     */
    public Lfraction divideBy(Lfraction m) {
        if (m.isZero()) throw new ArithmeticException("Divide by zero");
        return times(m.inverse());
    }

    /**
     * Check if fraction equals zero.
     *
     * @return this == 0
     */
    private boolean isZero() {
        return numerator == 0;
    }

    /**
     * Comparision of fractions.
     *
     * @param m second fraction
     * @return -1 if this < m; 0 if this==m; 1 if this > m
     */
    @Override
    public int compareTo(Lfraction m) {
        if (numerator * m.denominator == denominator * m.numerator) return 0;
        return (numerator * m.denominator > denominator * m.numerator) ? 1 : -1;
    }

    /**
     * Clone of the fraction.
     *
     * @return new fraction equal to this
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Integer part of the (improper) fraction.
     *
     * @return integer part of this fraction
     */
    public long integerPart() {
        return numerator / denominator;
    }

    /**
     * Extract fraction part of the (improper) fraction
     * (a proper fraction without the integer part).
     *
     * @return fraction part of this fraction
     */
    public Lfraction fractionPart() {
        return new Lfraction(numerator % denominator, denominator);
    }

    /**
     * Approximate value of the fraction.
     *
     * @return numeric value of this fraction
     */
    public double toDouble() {
        return numerator / (double) denominator;
    }

    /**
     * Double value f presented as a fraction with denominator d > 0.
     *
     * @param f real number
     * @param d positive denominator for the result
     * @return f as an approximate fraction of form n/d
     */
    public static Lfraction toLfraction(double f, long d) {
        return new Lfraction(Math.round(f * d), d);
    }

    /**
     * Conversion from string to the fraction. Accepts strings of form
     * that is defined by the toString method.
     *
     * @param s string form (as produced by toString) of the fraction
     * @return fraction represented by s
     */
    public static Lfraction valueOf(String s) {
        if (!s.matches(".+/.+"))
            throw new IllegalArgumentException("Incorrect format. Excpected: numerator/denominator, got: " + s);
        String[] strings = s.split("/");
        if (!strings[0].matches("-?\\d+")) throw new IllegalArgumentException("Wrong numerator in fraction " + s);
        if (!strings[1].matches("-?\\d+")) throw new IllegalArgumentException("Wrong denominator in fraction " + s);
        long num = Long.parseLong(strings[0]);
        long den = Long.parseLong(strings[1]);
        return new Lfraction(den > 0 ? num : -num, den > 0 ? den : -den);
    }
}