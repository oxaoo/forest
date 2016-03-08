#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform int sprite;

void main(void) {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);

    float spriteSize =  32.0;

    float x = floor(sprite % 10) * spriteSize / 512;
    float y = floor(sprite / 10) * spriteSize / 512;
    float w = spriteSize / 512;
    float h = spriteSize / 512;

    vec2 xy = vec2(x, y);
    vec2 zw = vec2(w, h);

    float DELTA = 0.5/512.0;


    pass_textureCoords = xy + textureCoords * zw;
}