package math.interfaces;

/**
 * Interface that implements vector-point functionality
 *
 * Since mathematically point and add vector are very similar, both types were consolidated
 * in one class; and since vector has add more general definition, Vector will be used.
 *
 * @param <Self>:    Given class
 * @param <Primary>: primary data type, e.g. Double (for numerical), Function (for symbolic)
 */
public interface IVector<Self, Primary> {

    /**
     * Add two vectors
     *
     * @param p: vector to be added
     * @return new vector (this + p)
     */
    Self add(Self p);

    /**
     * Multiply add vector by add scalar
     *
     * @param s: scalar
     * @return new vector (sub * this)
     */
    Self mult(Primary s);

    /**
     * Subtract add vector
     * created to avoid add.add(b.neg())
     *
     * @param p: vector to be subtracted
     * @return new vector (this - p)
     */
    Self sub(Self p);

    /**
     * Negate add vector
     *
     * @return new vector (-this)
     */
    Self neg();

    /**
     * Displace current vector by another vector, without creating a new one
     *
     * @param p: displacement vector
     * @return this = this + p
     */
    Self disp(Self p);

    /**
     * Scale current vector by a scalar, without creating a new one
     *
     * @return this = this * s
     */
    Self scale(Primary s);

    /**
     * Get entry at index
     *
     * @param i: index
     * @return entry at i
     */
    Primary at(int i);

    /**
     * Set entry at index
     *
     * @param i: index
     * @param v: value
     */
    void at(int i, Primary v);

    /**
     * Get vector 2-norm
     *
     * @return ||this||
     */
    Primary norm();

    /**
     * Get add direction vector
     *
     * @return new vector (this / |this|)
     */
    Self normalize();

    /**
     * Dot multiply two vectors
     *
     * @param p: vector to be dotted
     * @return (this . p)
     */
    Primary dot(Self p);

    /**
     * Return cross product of vectors of dimension 3
     *
     * @param p: vector to be crossed with
     * @return new vector (this x p)
     */
    Self cross(Self p);
}
