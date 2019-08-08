package math.interfaces;

public interface IMatrix<T, K> {
    IVector toVector(T m);
    IVector[] toVectors(T m);

    String toString();

    IVector mult(IVector<Class<IVector>, K> p);
    T add(T m);
    T mult(T m);
    T neg();
    T sub(T m);
    T inverse();
    T transpose();
    K det();

    IVector row(int i);
    IVector col(int i);

    K at(int i);
    K at(int i, int j);
    void at(int i, int j, K v);
}
