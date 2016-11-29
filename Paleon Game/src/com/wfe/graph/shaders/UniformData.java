package com.wfe.graph.shaders;

import java.nio.FloatBuffer;

/**
 * Created by Rick on 06.10.2016.
 */

public class UniformData {

    private final int uniformLocation;

    private FloatBuffer floatBuffer;

    public UniformData(int uniformLocation){
        this.uniformLocation = uniformLocation;
    }

    public int getUniformLocation() {
        return uniformLocation;
    }

    public FloatBuffer getFloatBuffer() {
        return floatBuffer;
    }

    public void setFloatBuffer(FloatBuffer floatBuffer) {
        this.floatBuffer = floatBuffer;
    }

}
