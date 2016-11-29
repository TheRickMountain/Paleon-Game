package com.wfe.components;

import com.wfe.graph.Mesh;
import com.wfe.graph.font.FontType;
import com.wfe.graph.font.TextMeshData;
import com.wfe.utils.Color;

public class Text extends Component {

	public String text;
	public FontType font;
    public float size;
    public Color color;

    public int numberOfLines;
	
    public Mesh mesh;
    
    public String lastText = "test";
    
    public Text(String text, FontType font, float fontSize, Color color) {
        this.text = text;
        this.size = fontSize;
        this.color = color;
        this.font = font;
        
        TextMeshData data = font.loadText(this);
        mesh = new Mesh(data.getVertexPositions(), 4);

        type = Type.TEXT;
    }
    
    public void setText(String text) {
      	if(!lastText.equals(text)) {
    		this.lastText = text;
    		this.text = text;
    		mesh.cleanup();
    	
    		TextMeshData data = font.loadText(this);
    		mesh.set(data.getVertexPositions(), 4);
    	}
    }
	
}
