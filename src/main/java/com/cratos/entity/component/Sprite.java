package com.cratos.entity.component;

import com.cratos.engineResource.Shader;
import com.cratos.engineResource.TextureLoader;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Sprite extends RenderComponent
{
    public Vector4f Color = new Vector4f();
    public int Texture = -1;
    @Override
    public void Initialize()
    {
        this.UseInUpdate = false;
        this.Color = Sprite.ConvertColorToGLSL(255.0f, 255.0f, 255.0f, 255.0f);
    }
    @Override
    public void Render(Shader shader, Matrix4f transform)
    {
        if(this.Texture > -1) TextureLoader.UseTexture(this.Texture);
        shader.UploadVec4("Color", this.Color);
        shader.UploadMat4("Transform", transform);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        TextureLoader.UnbindEveryTexture();
    }
    public static Vector4f ConvertColorToGLSL(Vector4f color)
    {
        float r = color.x/255;
        float g = color.y/255;
        float b = color.z/255;
        float a = color.w/255;
        return new Vector4f(r, g, b, a);
    }
    public static Vector4f ConvertColorToGLSL(float r, float g, float b, float a)
    {
        return new Vector4f(r/255, g/255, b/255, a/255);
    }
}
