#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

uniform float alpha;

in vec2 texCoord;

out vec4 fragColor;

void main() {
	float bloomPower = length((texture(DiffuseSampler, texCoord) - texture(PrevSampler, texCoord)).xyz);
	fragColor = vec4(bloomPower, bloomPower, bloomPower, 1.0) * vec4(alpha);
}
