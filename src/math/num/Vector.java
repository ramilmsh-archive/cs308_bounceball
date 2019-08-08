package math.num;

import math.interfaces.IVector;

public class Vector implements IVector<Vector, Double> {
    public double x;
    public double y;
    public double z;

    public int d;

    private double[] arr;

    public Vector(double... a) {
        if (a.length < 2)
            throw new IllegalArgumentException();
        arr = a;
        d = a.length;
        init();
    }

    public Vector(Vector p) {
        arr = new double[p.d];
        for (int i = 0; i < p.d; ++i)
            arr[i] = p.at(i);
        init();
    }

    public String toString() {
        return "vec: (" + this.x + ", " + this.y + ")";
    }

    private void init() {
        this.x = arr[0];
        this.y = arr[1];
        if (d == 3)
            this.z = arr[2];
    }

    public Vector add(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        double[] _arr = new double[d];
        for (int i = 0; i < d; ++i)
            _arr[i] = at(i) + p.at(i);
        return new Vector(_arr);
    }

    public Vector mult(Double s) {
        double[] _arr = new double[d];
        for (int i = 0; i < d; ++i) {
            _arr[i] = s * at(i);
        }
        return new Vector(_arr);
    }

    public Vector sub(Vector p) {
        return add(p.neg());
    }

    public Vector neg() {
        return mult(-1.);
    }

    public Vector disp(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        for (int i = 0; i < d; ++i)
            arr[i] += p.at(i);
        return this;
    }

    @Override
    public Vector scale(Double s) {
        for (int i = 0; i < d; ++i)
            arr[i] += s;
        return this;
    }

    public Double at(int i) {
        return arr[i];
    }

    public void at(int i, Double v) {
        switch (i) {
            case 0:
                x = v;
                break;
            case 1:
                y = v;
                break;
            case 2:
                z = v;
        }
        arr[i] = v;
    }

    public Double norm() {
        double sum = 0;
        for (int i = 0; i < d; ++i)
            sum += Math.pow(at(i), 2);
        return Math.sqrt(sum);
    }

    public Vector normalize() {
        return mult(1. / this.norm());
    }

    public Double dot(Vector p) {
        if (d != p.d)
            throw new IllegalArgumentException();
        double sum = 0;
        for (int i = 0; i < d; ++i)
            sum += at(i) * p.at(i);
        return sum;
    }

    public Vector cross(Vector p) {
        if (d != p.d || d != 3)
            throw new IllegalArgumentException();
        return new Vector(at(2) * p.at(3) - at(3) * p.at(2),
                at(3) * p.at(1) - at(1) * p.at(3),
                at(1) * p.at(2) - at(2) * p.at(1)
        );
    }
}
