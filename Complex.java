public class Complex {
    double a;
    double b;

    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public void square() {
        double r = a * a - b * b;
        double c = 2 * a * b;

        this.a = r;
        this.b = c;
    }

    public double abs() {
        return Math.sqrt(a * a + b * b);

    }

    public void add(Complex c) {
        this.a += c.a;
        this.b += c.b;
    }

    @Override
    public String toString() {
        return a + (b >= 0 ? "+" : "") + b + "i";
    }
}