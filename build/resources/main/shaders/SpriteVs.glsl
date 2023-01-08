#version 330 core
layout(location = 0) in vec4 vertex;

out vec2 texCoords;

uniform mat4 View;
uniform mat4 Projection;
uniform mat4 Transform;

void main()
{
    gl_Position = Projection * View * Transform * vec4(vertex.x, vertex.y, 1.0, 1.0);
    texCoords = vertex.zw;
}