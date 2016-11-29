package com.wfe.components;

import com.wfe.graph.Texture;
import com.wfe.utils.Color;

public class Image extends Component {

	public Texture texture;
	public Color color;
	
	public Image(Texture texture) {
		this(texture, new Color(1.0f, 1.0f, 1.0f));
	}
	
	public Image(Texture texture, Color color) {
		this.texture = texture;
		this.color = color;
		type = Type.IMAGE;
	}
	
}
