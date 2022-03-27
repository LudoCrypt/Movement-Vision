#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

uniform vec3 tint;
uniform int monotone;
uniform float brightness;
uniform float contrast;

in vec2 texCoord;

out vec4 fragColor;

void main() {
	vec4 movementVision = texture(DiffuseSampler, texCoord);
	vec4 colorCorrected = ((movementVision - 0.5) * vec4(contrast)) + 0.5 + vec4(brightness);
	
	if (monotone == 1) {
		fragColor = colorCorrected * vec4(tint, 1.0);
	} else {
		fragColor = colorCorrected * texture(PrevSampler, texCoord) * vec4(tint, 1.0);
	}
}
