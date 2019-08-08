package math.num;

import math.interfaces.IMatrix;
import math.interfaces.IVector;

public class Matrix implements IMatrix<Matrix, Double> {
    double[][] m;
    int[] d;

    public Matrix(double[][] m) {
        d = new int[]{m.length, m[0].length};
        this.m = m;
    }

    public Matrix(Vector v) {
        d = new int[]{v.d, 1};
        m = new double[d[1]][d[0]];
        for (int i = 0; i < d[1]; ++i)
            m[i][0] = v.at(i);
    }

    @Override
    public Vector toVector(Matrix m) {
        return null;
    }

    @Override
    public Vector[] toVectors(Matrix m) {
        return new Vector[0];
    }

    @Override
    public Vector mult(IVector<Class<IVector>, Double> p) {
        return null;
    }

    @Override
    public Matrix add(Matrix m) {
        return null;
    }

    @Override
    public Matrix mult(Matrix m) {
        return null;
    }

    @Override
    public Matrix neg() {
        return null;
    }

    @Override
    public Matrix sub(Matrix m) {
        return null;
    }

    @Override
    public Matrix inverse() {
        return null;
    }

    @Override
    public Matrix transpose() {
        return null;
    }

    @Override
    public Double det() {
        return null;
    }

    @Override
    public Vector row(int i) {
        return null;
    }

    @Override
    public Vector col(int i) {
        return null;
    }

    @Override
    public Double at(int i) {
        return null;
    }

    @Override
    public Double at(int i, int j) {
        return null;
    }

    @Override
    public void at(int i, int j, Double v) {

    }
}
