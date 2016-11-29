package com.wfe.graph.font;

public class TextMeshData {
     
    private float[] vertexPositions;
    
    protected TextMeshData(float[] vertexPositions){
        this.vertexPositions = vertexPositions;
    }
 
    public float[] getVertexPositions() {
        return vertexPositions;
    }
 
}