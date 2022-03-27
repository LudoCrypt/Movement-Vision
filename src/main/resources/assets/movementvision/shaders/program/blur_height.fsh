#version 150

const float gaussian[] = float[] (0.2270270270, 0.3162162162, 0.0702702703);

uniform sampler2D DiffuseSampler;

uniform float blurHeight;

in vec2 texCoord;

out vec4 fragColor;

vec4 sample(float x) {
	return texture(DiffuseSampler, vec2(texCoord.x, texCoord.y + x * blurHeight));
}

void main() {
	fragColor = sample(1.0) * gaussian[0] +
				sample(2.0) * gaussian[1] +
				sample(3.0) * gaussian[2] +
				sample(-1.0) * gaussian[0] +
				sample(-2.0) * gaussian[1] +
				sample(-3.0) * gaussian[2];
}
