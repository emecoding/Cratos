#version 330 core

in vec2 texCoords;
in vec4 texColor;

uniform sampler2D m_texture;

void main()
{
    gl_FragColor = texture2D(m_texture, texCoords) * texColor;
}