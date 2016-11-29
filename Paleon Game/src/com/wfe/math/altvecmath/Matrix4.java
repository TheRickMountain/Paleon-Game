/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Sri Harsha Chilakapati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wfe.math.altvecmath;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * @author Sri Harsha Chilakapati
 */
public class Matrix4
{
    public static final ReusableStack<Matrix4> REUSABLE_STACK = new ReusableStack<>(Matrix4::new);

    private float[][] m;

    private FloatBuffer buffer;

    public Matrix4(Vector4 c0, Vector4 c1, Vector4 c2, Vector4 c3)
    {
        this();

        m[0][0] = c0.x;
        m[1][0] = c1.x;
        m[2][0] = c2.x;
        m[3][0] = c3.x;
        m[0][1] = c0.y;
        m[1][1] = c1.y;
        m[2][1] = c2.y;
        m[3][1] = c3.y;
        m[0][2] = c0.z;
        m[1][2] = c1.z;
        m[2][2] = c2.z;
        m[3][2] = c3.z;
        m[0][3] = c0.w;
        m[1][3] = c1.w;
        m[2][3] = c2.w;
        m[3][3] = c3.w;
    }

    public Matrix4(Matrix3 m)
    {
        this();
        set(m);
    }

    public Matrix4()
    {
        m = new float[4][4];
        initIdentity();

        buffer = BufferUtils.createFloatBuffer(16);
    }

    public Matrix4(Matrix4 m)
    {
        this();
        set(m);
    }

    public Matrix4(float diagonal)
    {
        this();
        set(diagonal);
    }

    public Matrix4 set(Matrix3 m)
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                this.m[i][j] = m.get(i, j);
            }
        }

        return this;
    }

    public Matrix4 initIdentity()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (i == j)
                    m[i][j] = 1;
                else
                    m[i][j] = 0;
            }
        }

        return this;
    }

    public Matrix4 set(Matrix4 m)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.m[i][j] = m.get(i, j);
            }
        }

        return this;
    }

    public float get(int x, int y)
    {
        return m[x][y];
    }

    public Matrix4 set(float diagonal)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                m[i][j] = (i == j) ? diagonal : 0;
            }
        }

        return this;
    }

    public Matrix4 add(Matrix4 m)
    {
        return new Matrix4(this).addSelf(m);
    }

    public Matrix4 addSelf(Matrix4 m)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.m[i][j] += m.get(i, j);
            }
        }

        return this;
    }

    public Matrix4 subtract(Matrix4 m)
    {
        return new Matrix4(this).subtractSelf(m);
    }

    public Matrix4 subtractSelf(Matrix4 m)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                this.m[i][j] -= m.get(i, j);
            }
        }

        return this;
    }

    public Matrix4 multiply(Matrix4 m)
    {
        return copy().multiplySelf(m);
    }

    public Matrix4 multiplySelf(Matrix4 m)
    {
        // Use a temporary matrix from the matrix stack instead of
        // creating a temporary float array every frame.
        Matrix4 temp = Matrix4.REUSABLE_STACK.pop().initZero();

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 4; c++)
            {
                for (int k = 0; k < 4; k++)
                    temp.set(c, r, temp.get(c, r) + this.m[k][r] * m.get(c, k));
            }
        }

        this.set(temp);
        Matrix4.REUSABLE_STACK.push(temp);

        return this;
    }

    public Matrix4 initZero()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                m[i][j] = 0;
            }
        }

        return this;
    }

    public Matrix4 set(int x, int j, float val)
    {
        m[x][j] = val;

        return this;
    }

    public Vector3 multiply(Vector3 v)
    {
        return multiply(v, new Vector3());
    }

    public Vector3 multiply(Vector3 v, Vector3 dest)
    {
        float X = v.x;
        float Y = v.y;
        float Z = v.z;
        float W = 1;

        float A = m[0][0], B = m[0][1], C = m[0][2], D = m[0][3];
        float E = m[1][0], F = m[1][1], G = m[1][2], H = m[1][3];
        float I = m[2][0], J = m[2][1], K = m[2][2], L = m[2][3];

        // /        \  /   \     /                       \
        // | a b c d | | x |     | a.x + b.y + c.z + d.w |
        // | e f g h | | y |     | e.x + f.y + g.z + h.w |
        // | i j k l | | z |  =  | i.x + j.y + k.z + l.w |
        // | m n o p | | w |     | m.x + n.y + o.z + p.w |  // IGNORE FOR Vector3
        // \        /  \   /     \                      /

        return dest.set(A * X + B * Y + C * Z + D * W,
                E * X + F * Y + G * Z + H * W,
                I * X + J * Y + K * Z + L * W);
    }

    public Vector4 multiply(Vector4 v)
    {
        return multiply(v, new Vector4());
    }

    /**
     * Multiplies this matrix with a row matrix defined by the vector \( \vec {v} \) from the right side. The equation
     * is as follows.
     *
     * <p> $$ \begin{bmatrix} a &amp; b &amp; c &amp; d \\ e &amp; f &amp; g &amp; h \\ i &amp; j &amp; k &amp; l \\ m
     * &amp; n &amp; o &amp; p \end{bmatrix}
     *
     * \begin{bmatrix} x \\ y \\ z \\ w \end{bmatrix} =
     *
     * \begin{bmatrix} a\cdot x+b\cdot y+c\cdot z+d\cdot w \\ e\cdot x+f\cdot y+g\cdot z+h\cdot w \\ i\cdot x+j\cdot
     * y+k\cdot z+l\cdot w \\ m\cdot x+n\cdot y+o\cdot z+p\cdot w \end{bmatrix} $$ </p>
     *
     * @param v    The vector to multiply this matrix with.
     * @param dest The vector to store the result.
     *
     * @return The destination vector, aka the result.
     */
    public Vector4 multiply(Vector4 v, Vector4 dest)
    {
        float X = v.x;
        float Y = v.y;
        float Z = v.z;
        float W = 1;

        float A = m[0][0], B = m[0][1], C = m[0][2], D = m[0][3];
        float E = m[1][0], F = m[1][1], G = m[1][2], H = m[1][3];
        float I = m[2][0], J = m[2][1], K = m[2][2], L = m[2][3];
        float M = m[3][0], N = m[3][1], O = m[3][2], P = m[3][3];

        // /        \  /   \     /                       \
        // | a b c d | | x |     | a.x + b.y + c.z + d.w |
        // | e f g h | | y |     | e.x + f.y + g.z + h.w |
        // | i j k l | | z |  =  | i.x + j.y + k.z + l.w |
        // | m n o p | | w |     | m.x + n.y + o.z + p.w |
        // \        /  \   /     \                      /

        return dest.set(A * X + B * Y + C * Z + D * W,
                E * X + F * Y + G * Z + H * W,
                I * X + J * Y + K * Z + L * W,
                M * X + N * Y + O * Z + P * W);
    }

    public Matrix4 transpose()
    {
        return copy().transposeSelf();
    }

    public Matrix4 transposeSelf()
    {
        Matrix4 temp = Matrix4.REUSABLE_STACK.pop();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                temp.set(i, j, m[j][i]);
            }
        }

        this.set(temp);
        Matrix4.REUSABLE_STACK.push(temp);

        return this;
    }

    public Matrix4 invert()
    {
        return copy().invertSelf();
    }

    public Matrix4 invertSelf()
    {
        float s = determinant();

        if (s == 0)
            return this;

        s = 1f / s;

        Matrix4 dest = Matrix4.REUSABLE_STACK.pop();

        dest.m[0][0] = (m[1][1] * (m[2][2] * m[3][3] - m[2][3] * m[3][2]) + m[1][2] * (m[2][3] * m[3][1] - m[2][1] * m[3][3]) + m[1][3] * (m[2][1] * m[3][2] - m[2][2] * m[3][1])) * s;
        dest.m[0][1] = (m[2][1] * (m[0][2] * m[3][3] - m[0][3] * m[3][2]) + m[2][2] * (m[0][3] * m[3][1] - m[0][1] * m[3][3]) + m[2][3] * (m[0][1] * m[3][2] - m[0][2] * m[3][1])) * s;
        dest.m[0][2] = (m[3][1] * (m[0][2] * m[1][3] - m[0][3] * m[1][2]) + m[3][2] * (m[0][3] * m[1][1] - m[0][1] * m[1][3]) + m[3][3] * (m[0][1] * m[1][2] - m[0][2] * m[1][1])) * s;
        dest.m[0][3] = (m[0][1] * (m[1][3] * m[2][2] - m[1][2] * m[2][3]) + m[0][2] * (m[1][1] * m[2][3] - m[1][3] * m[2][1]) + m[0][3] * (m[1][2] * m[2][1] - m[1][1] * m[2][2])) * s;
        dest.m[1][0] = (m[1][2] * (m[2][0] * m[3][3] - m[2][3] * m[3][0]) + m[1][3] * (m[2][2] * m[3][0] - m[2][0] * m[3][2]) + m[1][0] * (m[2][3] * m[3][2] - m[2][2] * m[3][3])) * s;
        dest.m[1][1] = (m[2][2] * (m[0][0] * m[3][3] - m[0][3] * m[3][0]) + m[2][3] * (m[0][2] * m[3][0] - m[0][0] * m[3][2]) + m[2][0] * (m[0][3] * m[3][2] - m[0][2] * m[3][3])) * s;
        dest.m[1][2] = (m[3][2] * (m[0][0] * m[1][3] - m[0][3] * m[1][0]) + m[3][3] * (m[0][2] * m[1][0] - m[0][0] * m[1][2]) + m[3][0] * (m[0][3] * m[1][2] - m[0][2] * m[1][3])) * s;
        dest.m[1][3] = (m[0][2] * (m[1][3] * m[2][0] - m[1][0] * m[2][3]) + m[0][3] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) + m[0][0] * (m[1][2] * m[2][3] - m[1][3] * m[2][2])) * s;
        dest.m[2][0] = (m[1][3] * (m[2][0] * m[3][1] - m[2][1] * m[3][0]) + m[1][0] * (m[2][1] * m[3][3] - m[2][3] * m[3][1]) + m[1][1] * (m[2][3] * m[3][0] - m[2][0] * m[3][3])) * s;
        dest.m[2][1] = (m[2][3] * (m[0][0] * m[3][1] - m[0][1] * m[3][0]) + m[2][0] * (m[0][1] * m[3][3] - m[0][3] * m[3][1]) + m[2][1] * (m[0][3] * m[3][0] - m[0][0] * m[3][3])) * s;
        dest.m[2][2] = (m[3][3] * (m[0][0] * m[1][1] - m[0][1] * m[1][0]) + m[3][0] * (m[0][1] * m[1][3] - m[0][3] * m[1][1]) + m[3][1] * (m[0][3] * m[1][0] - m[0][0] * m[1][3])) * s;
        dest.m[2][3] = (m[0][3] * (m[1][1] * m[2][0] - m[1][0] * m[2][1]) + m[0][0] * (m[1][3] * m[2][1] - m[1][1] * m[2][3]) + m[0][1] * (m[1][0] * m[2][3] - m[1][3] * m[2][0])) * s;
        dest.m[3][0] = (m[1][0] * (m[2][2] * m[3][1] - m[2][1] * m[3][2]) + m[1][1] * (m[2][0] * m[3][2] - m[2][2] * m[3][0]) + m[1][2] * (m[2][1] * m[3][0] - m[2][0] * m[3][1])) * s;
        dest.m[3][1] = (m[2][0] * (m[0][2] * m[3][1] - m[0][1] * m[3][2]) + m[2][1] * (m[0][0] * m[3][2] - m[0][2] * m[3][0]) + m[2][2] * (m[0][1] * m[3][0] - m[0][0] * m[3][1])) * s;
        dest.m[3][2] = (m[3][0] * (m[0][2] * m[1][1] - m[0][1] * m[1][2]) + m[3][1] * (m[0][0] * m[1][2] - m[0][2] * m[1][0]) + m[3][2] * (m[0][1] * m[1][0] - m[0][0] * m[1][1])) * s;
        dest.m[3][3] = (m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) + m[0][1] * (m[1][2] * m[2][0] - m[1][0] * m[2][2]) + m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0])) * s;

        set(dest);
        Matrix4.REUSABLE_STACK.push(dest);

        return this;
    }

    public Matrix4 copy()
    {
        return new Matrix4(this);
    }

    public float determinant()
    {
        return (m[0][0] * m[1][1] - m[0][1] * m[1][0]) * (m[2][2] * m[3][3] - m[2][3] * m[3][2]) -
               (m[0][0] * m[1][2] - m[0][2] * m[1][0]) * (m[2][1] * m[3][3] - m[2][3] * m[3][1]) +
               (m[0][0] * m[1][3] - m[0][3] * m[1][0]) * (m[2][1] * m[3][2] - m[2][2] * m[3][1]) +
               (m[0][1] * m[1][2] - m[0][2] * m[1][1]) * (m[2][0] * m[3][3] - m[2][3] * m[3][0]) -
               (m[0][1] * m[1][3] - m[0][3] * m[1][1]) * (m[2][0] * m[3][2] - m[2][2] * m[3][0]) +
               (m[0][2] * m[1][3] - m[0][3] * m[1][2]) * (m[2][0] * m[3][1] - m[2][1] * m[3][0]);
    }

    public FloatBuffer getAsFloatBuffer()
    {
        buffer.clear();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                buffer.put(get(i, j));
            }
        }

        buffer.flip();

        return buffer;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                s.append(m[i][j]).append(' ');
            }
            s.append('\n');
        }

        return s.toString();
    }
}
