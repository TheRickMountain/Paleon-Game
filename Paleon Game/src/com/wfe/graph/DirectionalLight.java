package com.wfe.graph;

import com.wfe.math.Vector3f;
import com.wfe.utils.Color;

/**
 * Created by Rick on 08.10.2016.
 */
public class DirectionalLight {

    public final Vector3f position;
    public Color color;

    public DirectionalLight(Vector3f position, Color color) {
        this.position = position;
        this.color = color;
    }
    
    public static class OrthoCoords {
        
        public float left;
        
        public float right;
        
        public float bottom;
        
        public float top;

        public float near;
        
        public float far;
    }

}
