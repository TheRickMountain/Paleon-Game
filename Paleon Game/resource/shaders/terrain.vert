#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 uv;
layout (location = 2) in vec3 normal;

out vec2 UV;
out vec3 FragPos;
out vec3 Normal;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

uniform vec4 plane;

void main() {

    vec4 mPos = model * vec4(position, 1.0f);
    
    gl_ClipDistance[0] = dot(mPos, plane);
    
    vec4 mvPos = view * mPos;
    gl_Position = projection * mvPos;
    UV = uv;
    FragPos = vec3(mPos);
    Normal = mat3(transpose(inverse(model))) * normal;
}