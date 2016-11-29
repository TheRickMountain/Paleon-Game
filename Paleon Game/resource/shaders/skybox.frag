#version 330 core

in vec3 UV;

uniform samplerCube cubeMap0;
uniform samplerCube cubeMap1;
uniform float blendFactor;
uniform vec4 fogColor;

out vec4 out_Color;

const float lowerLimit = 0.0f;
const float upperLimit = 120.0f;

void main(void){
	vec4 cubemap0 = texture(cubeMap0, UV);
	vec4 cubemap1 = texture(cubeMap1, UV);
	vec4 finalColor = mix(cubemap0, cubemap1, blendFactor);
    
    float factor = (UV.y - lowerLimit) / (upperLimit - lowerLimit);
	factor = clamp(factor, 0.0f, 1.0f);
	out_Color = mix(fogColor, finalColor, factor);
}