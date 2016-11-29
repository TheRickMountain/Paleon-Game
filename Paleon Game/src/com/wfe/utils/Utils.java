package com.wfe.utils;

import java.util.ArrayList;
import java.util.List;

import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;

/**
 * Created by Rick on 06.10.2016.
 */
public class Utils {

    private Utils() {}

    public static float getDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
        float dX = x1 - x2;
        float dY = y1 - y2;
        float distance = (float) Math.sqrt((dX * dX) + (dY * dY));
        return distance;
    }
    
    public static Vector3f calculateNormal(Vector3f point0, Vector3f point1, Vector3f point2) {
		Vector3f vectorA = Vector3f.sub(point1, point0, null);
		Vector3f vectorB = Vector3f.sub(point2, point0, null);	
		Vector3f normal = Vector3f.cross(vectorA, vectorB, null);
		normal.normalise();
		return normal;
	}
    
    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }
    
    public static String [] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
    
    public static float getAverageOfList(List<Float> numbers) {
        float total = 0;
        for (Float number : numbers) {
            total += number;
        }
        return total / numbers.size();
    }
    
    public static float[] joinArrays(float[] vertices, float[] uvs) {
        float[] data = new float[vertices.length + uvs.length];
        int count = 0;
        int vertexCount = 0;
        int uvsCount = 0;
        for(int i = 0; i < data.length / 4; i++) {
            data[count] = vertices[vertexCount];
            vertexCount++;
            count++;
            data[count] = vertices[vertexCount];
            vertexCount++;
            count++;
            data[count] = uvs[uvsCount];
            uvsCount++;
            count++;
            data[count] = uvs[uvsCount];
            uvsCount++;
            count++;
        }
        return data;
    }

}
