#version 400 core

in vec2 position;

out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;
out vec3 fromLightVector;
out vec4 mvPos;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;
uniform vec3 lightPosition;

const float tiling = 4.0f;

void main(void) {
	vec4 mPos = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);
	mvPos = viewMatrix * mPos;
	clipSpace = projectionMatrix * mvPos;
	gl_Position = clipSpace;
	textureCoords = vec2(position.x / 2.0f + 0.5f, position.y / 2.0f + 0.5f) * tiling;
 	toCameraVector = cameraPosition - mPos.xyz;
 	fromLightVector = mPos.xyz - lightPosition;
}