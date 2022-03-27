#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

uniform vec3 trail;

in vec2 texCoord;

out vec4 fragColor;

void main() {
	fragColor = vec4(max(texture(DiffuseSampler, texCoord).xyz, texture(PrevSampler, texCoord).xyz * trail), 1.0);
}
