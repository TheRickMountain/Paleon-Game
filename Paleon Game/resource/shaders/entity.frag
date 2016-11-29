#version 330 core

in vec2 UV;
in vec3 FragPos;
in vec3 Normal;
in float Visibility;

uniform sampler2D image;
uniform vec4 color;

uniform vec4 lightColor;
uniform vec3 lightPosition;

uniform vec3 viewPosition;

uniform vec4 fogColor;

uniform int useFakeLighting;
uniform int transparency;
uniform int useSpecular;

out vec4 out_Color;

void main() {
	vec4 objectColor = texture(image, UV);
    if(transparency == 1) {
	    if(objectColor.a < 0.5f) {
	        discard;
	    }
    }

    // Ambient
    float ambientStrength = 0.4f;
    vec3 ambient = ambientStrength * lightColor.rgb;

    // Diffuse
    vec3 norm = vec3(0.0f, 1.0f, 0.0f);
    if(useFakeLighting != 1) {
        norm = normalize(Normal);
    }
    vec3 lightDir = normalize(lightPosition - FragPos);
    float diff = max(dot(norm, lightDir), 0.2f);
    vec3 diffuse = diff * lightColor.rgb;

    vec3 result = ambient + diffuse;

    // Specular
    if(useSpecular == 1) {
        float specularStrength = 0.5f;
        vec3 viewDir = normalize(viewPosition - FragPos);
        vec3 reflectDir = reflect(-lightDir, norm);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0f), 16);
        vec3 specular = specularStrength * spec * lightColor.rgb;
        result = result + specular;
    }

    out_Color = vec4(result, 1.0f) * objectColor * color;
    out_Color = mix(fogColor, out_Color, Visibility);
}