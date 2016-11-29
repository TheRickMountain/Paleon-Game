package com.wfe.weather;

import com.wfe.graph.DirectionalLight;
import com.wfe.math.Vector3f;
import com.wfe.utils.Color;

public class Weather {

	private static final float RELATIVE_HEIGHT_OF_ARC = 0.6f;// between 0 and 1
	private static final float DAY_NIGHT_BALANCE = 0.75f;// between -1 and 1
	private static final float SUN_DISTANCE = 1000000;

	private static final float HEIGHT_OFFSET = (SUN_DISTANCE * RELATIVE_HEIGHT_OF_ARC)
			* DAY_NIGHT_BALANCE;
	
	
	public final DirectionalLight sun;
	private Color fogColor;
	
	public Weather() {
		sun = new DirectionalLight(new Vector3f(384, 1000, 1500), new Color(255, 255, 200));
	}
	
	private WeatherFrame[] weatherFrames = {
			new WeatherFrame(0, new Color(0.05f, 0.05f, 0.1f), new Color(0f, 0f, 0.26f)),
			new WeatherFrame(2500, new Color(0.05f, 0.05f, 0.1f), new Color(0f, 0f, 0.26f)),
			new WeatherFrame(3500, new Color(0.77f, 0.58f, 0.6f), new Color(1.0f, 0.4f, 0.6f)),
			new WeatherFrame(7500, new Color(1f, 0.9f, 0.6f), new Color(1f, 0.9f, 0.6f)),
			new WeatherFrame(12000, new Color(0.9f, 0.9f, 1.0f), new Color(1.0f, 0.9f, 0.7f)),
			new WeatherFrame(16500, new Color(1f, 0.9f, 0.6f), new Color(1f, 0.9f, 0.6f)),
			new WeatherFrame(21000, new Color(0.77f, 0.58f, 0.6f), new Color(1.0f, 0.4f, 0.6f)),
			new WeatherFrame(23500, new Color(0.05f, 0.05f, 0.1f), new Color(0f, 0f, 0.26f)),
			new WeatherFrame(24001, new Color(0.05f, 0.05f, 0.1f), new Color(0f, 0f, 0.26f)),
	};
	
	public void updateWeather(float time) {
		interpolateOtherVariables(time);
		calculateSunPosition(time);
	}
	
	private void interpolateOtherVariables(float time) {
		WeatherFrame frame1 = weatherFrames[0];
		WeatherFrame frame2 = null;
		int pointer = 1;
		while (true) {
			frame2 = weatherFrames[pointer++];
			if (time < frame2.getTime()) {
				break;
			} else {
				frame1 = frame2;
			}
		}
		float timeFactor = WeatherFrame.getTimeFactor(frame1, frame2, time);
		updateFogVariables(timeFactor, frame1, frame2);
		updateSunVariables(timeFactor, frame1, frame2);
	}
	
	private void calculateSunPosition(float time) {
		double radionTime = ((time * Math.PI) / 12000) - (Math.PI / 2);
		float x = (float) (SUN_DISTANCE * Math.cos(radionTime));
		float z = (float) ((1 - RELATIVE_HEIGHT_OF_ARC) * SUN_DISTANCE * Math.sin(radionTime));
		float y = (float) (RELATIVE_HEIGHT_OF_ARC * SUN_DISTANCE * Math.sin(radionTime))
				+ HEIGHT_OFFSET;
		sun.position.set(x, y, z);
	}
	
	private void updateFogVariables(float timeFactor, WeatherFrame frame1, WeatherFrame frame2) {
		fogColor = WeatherFrame.getInterpolatedFogColor(frame1, frame2, timeFactor);
	}
	
	private void updateSunVariables(float timeFactor, WeatherFrame frame1, WeatherFrame frame2) {	
		sun.color = WeatherFrame.getInterpolatedSunLightColour(frame1, frame2, timeFactor);
	}
	
	public Color getFogColor() {
		return fogColor;
	}

	
}