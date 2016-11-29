package com.wfe.components;

import com.wfe.graph.Texture;
import com.wfe.utils.Color;

/**
 * Created by Rick on 06.10.2016.
 */
public class Material extends Component {

    public final Texture texture;
    public final Color color;

    private int numberOfRows = 1;
    public boolean useFakeLighting = false;
    public boolean transparency = false;
    public boolean useSpecular = false;

    public Material(Texture texture) {
        this(texture, new Color(1.0f, 1.0f, 1.0f));
    }

    public Material(Texture texture, Color color) {
        type = Type.MATERIAL;
        this.texture = texture;
        this.color = color;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        if(numberOfRows <= 0)
            numberOfRows = 1;

        this.numberOfRows = numberOfRows;
    }

}
