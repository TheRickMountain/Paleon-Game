package com.wfe.utils;

import com.wfe.math.Vector3f;

public class CellInfo {

    public final Vector3f position;
    private int state;

    public CellInfo(Vector3f position, int state) {
        this.position = position;
        setState(state);
    }
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		if(state < 0 || state > 3)
			this.state = 0;
		
		this.state = state;
	}
	


}
