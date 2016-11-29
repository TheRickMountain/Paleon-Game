#version 330 core

in vec2 UV;
in vec3 FragPos;
in vec3 Normal;
in float Visibility;

uniform sampler2D blendMap;
uniform sampler2D aTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;

uniform vec4 lightColor;
uniform vec3 lightPosition;
uniform vec4 fogColor;

out vec4 out_Color;

vec4 totalColor;

void main() {
    vec4 blendMapColor = texture(blendMap, UV);

    float backTextureAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    
    vec2 tiledCoords = UV * 40;
    vec4 backgroundTextureColor = texture(aTexture, tiledCoords) * backTextureAmount;
    vec4 rTextureColor = texture(rTexture, tiledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, tiledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, tiledCoords) * blendMapColor.b;

    totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;

    // Ambient
    float ambientStrength = 0.4f;
    vec3 ambient = ambientStrength * lightColor.rgb;

    // Diffuse
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPosition - FragPos);
    float diff = max(dot(norm, lightDir), 0.2f);
    vec3 diffuse = diff * lightColor.rgb;

    vec4 result = vec4(ambient + diffuse, 1.0f);
    out_Color = result * totalColor;
    out_Color = mix(fogColor, out_Color, Visibility);
}