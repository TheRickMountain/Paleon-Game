#version 400

layout (location = 0) in vec3 position;

uniform mat4 projection;
uniform mat4 view;

out vec3 UV;

void main(void) {

    gl_Position = projection * view * vec4(position, 1.0f);
    UV = position;

}