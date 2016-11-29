package com.wfe.graph.font;

import com.wfe.components.Text;
import com.wfe.core.ResourceManager;
import com.wfe.graph.Texture;

public class FontType {
 
    private Texture textureAtlas;
    private TextMeshCreator loader;

    public FontType(String font) {
        ResourceManager.loadTexture("fonts/" + font, font);
        this.textureAtlas = ResourceManager.getTexture(font);
        this.loader = new TextMeshCreator("/fonts/" + font + ".fnt");
    }
 
    /**
     * @return The font texture atlas.
     */
    public Texture getTextureAtlas() {
        return textureAtlas;
    }
 
    /**
     * Takes in an unloaded text and calculate all of the vertices for the quads
     * on which this text will be rendered. The vertex positions and texture
     * coords and calculated based on the information from the font file.
     * 
     * @param text
     *            - the unloaded text.
     * @return Information about the vertices of all the quads.
     */
    public TextMeshData loadText(Text text) {
        return loader.createTextMesh(text);
    }
 
}