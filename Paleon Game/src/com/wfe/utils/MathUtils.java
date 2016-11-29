package com.wfe.utils;

import com.wfe.graph.Camera;
import com.wfe.math.Matrix4f;
import com.wfe.math.Vector2f;
import com.wfe.math.Vector3f;
import com.wfe.math.altvecmath.Matrix4;
import com.wfe.math.altvecmath.Transform;
import com.wfe.math.altvecmath.Vector3;
import com.wfe.physics.CollisionPacket;
import com.wfe.physics.Triangle;

public class MathUtils {
	
	public static final float PI = 3.1415927f;
	public static final float RADIANS_TO_DEGREES = 180f / PI;
	public static final float DEGREES_TO_RADIANS = PI / 180;
	
	public static final Vector3f AXIS_X = new Vector3f(1, 0, 0);
    public static final Vector3f AXIS_Y = new Vector3f(0, 1, 0);
    public static final Vector3f AXIS_Z = new Vector3f(0, 0, 1);
    public static final Vector3f ZERO = new Vector3f(0, 0, 0);
    public static final Vector3f IDENTITY = new Vector3f(1, 1, 1);

    public static boolean point2DBoxIntersection(float x, float y, float xPos, float yPos, float xScale, float yScale) {
    	return x >= xPos && x <= xPos + xScale &&
				y >= yPos && y <= yPos + yScale;
    }
    
    public static boolean point3DBoxIntersection(float x, float y, float z, float xPos, float yPos, float zPos, 
    		float xScale, float yScale, float zScale) {
    	return x >= xPos && x <= xPos + xScale &&
				y >= yPos && y <= yPos + yScale &&
				z >= zPos && z <= zPos + zScale;
    }
    
    public static Vector3f calculateNormal(Vector3f point0, Vector3f point1, Vector3f point2) {// anticlockwise
		// order
    	Vector3f vectorA = Vector3f.sub(point1, point0, null);
    	Vector3f vectorB = Vector3f.sub(point2, point0, null);
    	Vector3f normal = Vector3f.cross(vectorA, vectorB, null);
    	normal.normalise();
    	return normal;
	}
    
	public static Matrix4f getModelMatrix(Matrix4f matrix, float xPos, float yPos, float rotation, float xScale, float yScale){
		matrix.setIdentity();
		Matrix4f.translate(new Vector2f(xPos, yPos), matrix, matrix);
        Matrix4f.translate(new Vector2f(0.5f * xScale, 0.5f * yScale), matrix, matrix);
		Matrix4f.rotate(rotation* DEGREES_TO_RADIANS, AXIS_Z, matrix, matrix);
		Matrix4f.translate(new Vector2f(-0.5f * xScale, -0.5f * yScale), matrix, matrix);
		Matrix4f.scale(new Vector3f(xScale, yScale, 0.0f), matrix, matrix);
        
		return matrix;
	}
	
	public static Matrix4f getModelMatrix(Matrix4f matrix, Vector3f offset, Vector3f rotation, Vector3f scale){
		matrix.setIdentity();
		Matrix4f.translate(offset, matrix, matrix);
		Matrix4f.rotate(rotation.x * DEGREES_TO_RADIANS, AXIS_X, matrix, matrix);
		Matrix4f.rotate(rotation.y * DEGREES_TO_RADIANS, AXIS_Y, matrix, matrix);
		Matrix4f.rotate(rotation.z * DEGREES_TO_RADIANS, AXIS_Z, matrix, matrix);
		Matrix4f.scale(scale, matrix, matrix);
		return matrix;
	}
	
	static Transform transform = new Transform();
	
	public static Matrix4f getEulerModelMatrix(Matrix4f matrix, Vector3f position, Vector3f rotation, Vector3f scale) {
		matrix.setIdentity();
		transform.reset()
        .rotateSelf(Vector3.AXIS_X, rotation.x * DEGREES_TO_RADIANS)
        .rotateSelf(Vector3.AXIS_Z, rotation.z * DEGREES_TO_RADIANS)
        .rotateSelf(Vector3.AXIS_Y, rotation.y * DEGREES_TO_RADIANS)
        .scaleSelf(new Vector3(scale.x, scale.y, scale.z))
        .translateSelf(new Vector3(position.x, position.y, position.z));
		Matrix4 tempMatrix = transform.getMatrix();
		matrix.m00 = tempMatrix.get(0, 0);
		matrix.m01 = tempMatrix.get(0, 1);
		matrix.m02 = tempMatrix.get(0, 2);
		matrix.m03 = tempMatrix.get(0, 3);
		matrix.m10 = tempMatrix.get(1, 0);
		matrix.m11 = tempMatrix.get(1, 1);
		matrix.m12 = tempMatrix.get(1, 2);
		matrix.m13 = tempMatrix.get(1, 3);
		matrix.m20 = tempMatrix.get(2, 0);
		matrix.m21 = tempMatrix.get(2, 1);
		matrix.m22 = tempMatrix.get(2, 2);
		matrix.m23 = tempMatrix.get(2, 3);
		matrix.m30 = tempMatrix.get(3, 0);
		matrix.m31 = tempMatrix.get(3, 1);
		matrix.m32 = tempMatrix.get(3, 2);
		matrix.m33 = tempMatrix.get(3, 3);
		return matrix;
    }
	
	public static Matrix4f getPerspProjectionMatrix(Matrix4f matrix, float fov, float width, float height, float zNear, float zFar){
		float aspectRatio = (float) width/ (float)height;
		float y_scale = (float) ((1f / Math.tan((fov / 2) * DEGREES_TO_RADIANS)) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = zFar - zNear;
		
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((zFar + zNear) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * zFar * zNear) / frustum_length);
		matrix.m33 = 0;
		
		return matrix; 
	}
	
	public static Matrix4f getOrtho2DProjectionMatrix(Matrix4f matrix, float left, float right, float bottom, float top) {
        matrix.m00 = 2.0f / (right - left);
        matrix.m11 = 2.0f / (top - bottom);
        matrix.m22 = -1.0f;
        matrix.m30 = -(right + left) / (right - left);
        matrix.m31 = -(top + bottom) / (top - bottom);
        return matrix;
	}
	
	public static Matrix4f getViewMatrix(Matrix4f matrix, Camera camera) {
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), AXIS_X, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), AXIS_Y, matrix, matrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, matrix, matrix);
		return matrix;
	}
	
	public static float getDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		float distance = (float) Math.sqrt((dX * dX) + (dY * dY));
		return distance;
	}
	
	public static void checkTriangle(CollisionPacket colPackage, Triangle triangle) {
		Plane trianglePlane = new Plane(triangle);
		if(trianglePlane.isFrontFacingTo(colPackage.getNormalizedVelocity())) {
			double t0, t1;
			boolean embeddedInPlane = false;
			
			double signedDistToTrianglePlane = trianglePlane.getSignedDistance(colPackage.getBasePoint());
			
			float normalDotVelocity = Vector3f.dot(trianglePlane.normal, colPackage.getVelocity());
			
			if(normalDotVelocity == 0.0f) {
				if (Math.abs(signedDistToTrianglePlane) >= 1.0f) {
					return;
				} else {
					embeddedInPlane = true;
					t0 = 0.0f;
					t1 = 1.0f;
				}
			} else {
				t0 = (-1.0 - signedDistToTrianglePlane) / normalDotVelocity;
				t1 = (1.0 - signedDistToTrianglePlane) / normalDotVelocity;
				
				if(t0 > t1) {
					double temp = t1;
					t1 = t0;
					t0 = temp;
				}
				
				if(t0 > 1.0f || t1 < 0.0f) {
					return;
				}
				
				if (t0 < 0.0) t0 = 0.0;
				if (t1 < 0.0) t1 = 0.0;
				if (t0 > 1.0) t0 = 1.0;
				if (t1 > 1.0) t1 = 1.0;
			}
			
			Vector3f collisionPoint = new Vector3f();
			boolean foundCollision = false;
			float t = 1.0f;
			
			if(!embeddedInPlane) {
				Vector3f t0Velocity = new Vector3f();
				t0Velocity.set(colPackage.getVelocity());
				t0Velocity.scale((float) t0);
				
				Vector3f planeIntersectionPoint =
						Vector3f.add(Vector3f.sub(colPackage.getBasePoint(), trianglePlane.normal, null),
								t0Velocity, null);
				
				if(checkPointInTriangle(planeIntersectionPoint, 
						triangle.getPointN(0), triangle.getPointN(1), triangle.getPointN(2))) {
					foundCollision = true;
					t = (float)t0;
					collisionPoint.set(planeIntersectionPoint);
				}
			}
			
			if(foundCollision == false) {
				Vector3f velocity = new Vector3f();
				velocity.set(colPackage.getVelocity());
				Vector3f base = new Vector3f();
				base.set(colPackage.getBasePoint());
				float velocitySquaredLength = velocity.lengthSquared();
				float a, b, c;
				float newT;
				
				a = velocitySquaredLength;
				
				b = 2.0f * Vector3f.dot(velocity, Vector3f.sub(base, triangle.getPointN(0), null));
				c = Vector3f.sub(triangle.getPointN(0), base, null).lengthSquared() - 1.0f;
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					t = newT;
					foundCollision = true;
					collisionPoint.set(triangle.getPointN(0));
				}
				
				b = 2.0f * Vector3f.dot(velocity, Vector3f.sub(base, triangle.getPointN(1), null));
				c = Vector3f.sub(triangle.getPointN(1), base, null).lengthSquared() - 1.0f;
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					t = newT;
					foundCollision = true;
					collisionPoint.set(triangle.getPointN(1));
				}
				
				b = 2.0f * Vector3f.dot(velocity, Vector3f.sub(base, triangle.getPointN(2), null));
				c = Vector3f.sub(triangle.getPointN(2), base, null).lengthSquared() - 1.0f;
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					t = newT;
					foundCollision = true;
					collisionPoint.set(triangle.getPointN(2));
				}
				
				// p1 -> p2:
				Vector3f edge = Vector3f.sub(triangle.getPointN(1), triangle.getPointN(0), null);
				Vector3f baseToVertex = Vector3f.sub(triangle.getPointN(0), base, null);
				float edgeSquaredLength = edge.lengthSquared();
				float edgeDotVelocity = Vector3f.dot(edge, velocity);
				float edgeDotBaseToVertex = Vector3f.dot(edge, baseToVertex);
				
				a = edgeSquaredLength * (-velocitySquaredLength) +
						edgeDotVelocity * edgeDotVelocity;
				b = edgeSquaredLength * (2 * Vector3f.dot(velocity, baseToVertex))
						- 2.0f * edgeDotVelocity * edgeDotBaseToVertex;
				c = edgeSquaredLength * (1 - baseToVertex.lengthSquared()) +
						edgeDotBaseToVertex * edgeDotBaseToVertex;
				
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					float f = (edgeDotVelocity * newT - edgeDotBaseToVertex) /
							edgeSquaredLength;
					if(f >= 0.0f && f <= 1.0f) {
						t = newT;
						foundCollision = true;
						
						Vector3f fEdge = new Vector3f();
						fEdge.set(edge);
						fEdge.scale(f);
						
						collisionPoint.set(Vector3f.add(triangle.getPointN(0), fEdge, null));
					}
				}
				
				// p2 -> p3:
				edge = Vector3f.sub(triangle.getPointN(2), triangle.getPointN(1), null);
				baseToVertex = Vector3f.sub(triangle.getPointN(1), base, null);
				edgeSquaredLength = edge.lengthSquared();
				edgeDotVelocity = Vector3f.dot(edge, velocity);
				edgeDotBaseToVertex = Vector3f.dot(edge, baseToVertex);
				
				a = edgeSquaredLength * (-velocitySquaredLength) +
						edgeDotVelocity * edgeDotVelocity;
				b = edgeSquaredLength * (2 * Vector3f.dot(velocity, baseToVertex))
						- 2.0f * edgeDotVelocity * edgeDotBaseToVertex;
				c = edgeSquaredLength * (1 - baseToVertex.lengthSquared()) +
						edgeDotBaseToVertex * edgeDotBaseToVertex;
				
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					float f = (edgeDotVelocity * newT - edgeDotBaseToVertex) /
							edgeSquaredLength;
					if(f >= 0.0f && f <= 1.0f) {
						t = newT;
						foundCollision = true;
						
						Vector3f fEdge = new Vector3f();
						fEdge.set(edge);
						fEdge.scale(f);
						
						collisionPoint.set(Vector3f.add(triangle.getPointN(1), fEdge, null));
					}
				}
				
				// p3 -> p1:
				edge = Vector3f.sub(triangle.getPointN(0), triangle.getPointN(2), null);
				baseToVertex = Vector3f.sub(triangle.getPointN(2), base, null);
				edgeSquaredLength = edge.lengthSquared();
				edgeDotVelocity = Vector3f.dot(edge, velocity);
				edgeDotBaseToVertex = Vector3f.dot(edge, baseToVertex);
				
				a = edgeSquaredLength * (-velocitySquaredLength) +
						edgeDotVelocity * edgeDotVelocity;
				b = edgeSquaredLength * (2 * Vector3f.dot(velocity, baseToVertex))
						- 2.0f * edgeDotVelocity * edgeDotBaseToVertex;
				c = edgeSquaredLength * (1 - baseToVertex.lengthSquared()) +
						edgeDotBaseToVertex * edgeDotBaseToVertex;
				
				newT = getLowestRoot(a, b, c, t);
				if(newT != -1.0f) {
					float f = (edgeDotVelocity * newT - edgeDotBaseToVertex) /
							edgeSquaredLength;
					if(f >= 0.0f && f <= 1.0f) {
						t = newT;
						foundCollision = true;
						
						Vector3f fEdge = new Vector3f();
						fEdge.set(edge);
						fEdge.scale(f);
						
						collisionPoint.set(Vector3f.add(triangle.getPointN(2), fEdge, null));
					}
				}
			}
			
			if(foundCollision == true) {
				float distToCollision = t * colPackage.getVelocity().length();
				if(colPackage.foundCollision == false || distToCollision < colPackage.nearestDistance) {
					colPackage.nearestDistance = distToCollision;
					colPackage.intersectionPoint.set(collisionPoint);
					colPackage.foundCollision = true;
				}
			}
		} // if not backface
	}
	
	public static boolean checkPointInTriangle(Vector3f p, Vector3f a, Vector3f b, Vector3f c) {
		Vector3f v0 = Vector3f.sub(c, a, null);
		Vector3f v1 = Vector3f.sub(b, a, null);
		Vector3f v2 = Vector3f.sub(p, a, null);

		float dot00 = Vector3f.dot(v0, v0);
		float dot01 = Vector3f.dot(v0, v1);
		float dot02 = Vector3f.dot(v0, v2);
		float dot11 = Vector3f.dot(v1, v1);
		float dot12 = Vector3f.dot(v1, v2);

		float invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
		float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
		float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

		return (u >= 0) && (v >= 0) && (u + v < 1);
	}
	
	public static float getLowestRoot(float a, float b, float c, float maxR) {// -1
		// if no valid root
		float determinant = b * b - (4.0f * a * c);
		if (determinant < 0.0f) {
			return -1.0f;
		}
		
		float sqrtD = (float) Math.sqrt(determinant);
		float r1 = (-b - sqrtD) / (2 * a);
		float r2 = (-b + sqrtD) / (2 * a);
		
		// Sort so x1 <= x2
		if (r1 > r2) {
			float temp = r2;
			r2 = r1;
			r1 = temp;
		}
		
		
		if(r1 > 0 && r1 < maxR) {
			return r1;
		}
		
		if(r2 > 0 && r2 < maxR) {
			return r2;
		}
		
		return -1.0f;
	}
	
	
}
