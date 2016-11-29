#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 uv;
layout (location = 2) in vec3 normal;

out vec2 UV;
out vec3 FragPos;
out vec3 Normal;
out float Visibility;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform int numberOfRows;
uniform vec2 offset;

uniform vec4 plane;

const float density = 0.003f;
const float gradient = 5.0f;

void main() {

    vec4 mPos = model * vec4(position, 1.0f);
    
    gl_ClipDistance[0] = dot(mPos, plane);
    
    vec4 mvPos = view * mPos;
    gl_Position = projection * mvPos;
    if(numberOfRows == 1) {
        UV = uv;
    } else {
        UV = (uv / numberOfRows) + offset;
    }
    FragPos = vec3(mPos);
    Normal = mat3(transpose(inverse(model))) * normal;
    
	float distance = length(mvPos.xyz);
	Visibility = exp(-pow((distance * density), gradient));
	Visibility = clamp(Visibility, 0.0f, 1.0f);
}