#version 330 core

in vec3 UV;

uniform samplerCube cubeMap0;
uniform samplerCube cubeMap1;
uniform float blendFactor;

out vec4 out_Color;


void main(void){
	vec4 cubemap0 = texture(cubeMap0, UV);
	vec4 cubemap1 = texture(cubeMap1, UV);
	vec4 finalColor = mix(cubemap0, cubemap1, blendFactor);
	
	out_Color = finalColor;
}