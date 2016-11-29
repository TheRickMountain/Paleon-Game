package com.wfe.physics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.wfe.math.Vector3f;

public class ColliderMesh {

	private List<Vector3f> points;
	private List<Triangle> triangles;

	private BufferedReader reader;

	public ColliderMesh(String fileName) throws Exception {
		reader = new BufferedReader(
				new InputStreamReader(ColliderMesh.class.getClass().getResourceAsStream("/" + fileName + ".obj")));
		points = new ArrayList<Vector3f>();
		triangles = new ArrayList<Triangle>();
	}

	public Triangle[] extractTriangles() throws IOException {
		String line;
		while ((line = reader.readLine()) != null) {
			String[] values = line.split(" ");
			if (values[0].equals("v")) {
				processVertex(values);
			} else if (values[0].equals("f")) {
				processFace(values);
			}
		}
		Triangle[] triangleArray = new Triangle[triangles.size()];
		for (int i = 0; i < triangles.size(); i++) {
			triangleArray[i] = triangles.get(i);
		}
		return triangleArray;
	}

	public void closeFile() throws IOException {
		reader.close();
	}

	private void processVertex(String[] vertexValues) {
		float x = (float) Float.parseFloat(vertexValues[1]);
		float y = (float) Float.parseFloat(vertexValues[2]);
		float z = (float) Float.parseFloat(vertexValues[3]);
		Vector3f point = new Vector3f(x, y, z);
		points.add(point);
	}

	private void processFace(String[] faceValues) {
		Vector3f p2 = points.get(Integer.parseInt(faceValues[1].split("/")[0]) - 1);
		Vector3f p1 = points.get(Integer.parseInt(faceValues[2].split("/")[0]) - 1);
		Vector3f p0 = points.get(Integer.parseInt(faceValues[3].split("/")[0]) - 1);
		triangles.add(new Triangle(p2, p1, p0));
	}

}

