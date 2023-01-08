package com.cratos.entity.component;

import com.cratos.engineResource.Shader;
import com.cratos.engineResource.TextureLoader;
import com.cratos.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class Sprite extends RenderComponent
{
    public Vector4f Color = new Vector4f();
    public int Texture = -1;
    public Sprite()
    {
        super("SPRITE");
    }
    @Override
    public void Initialize()
    {
        this.UseInUpdate = false;
        this.Color = Sprite.ConvertColorToGLSL(255.0f, 255.0f, 255.0f, 255.0f);
    }
    @Override
    public void Render()
    {
        if(this.Texture > -1) TextureLoader.UseTexture(this.Texture);
        Shader.UnbindEveryShader();
        m_Shader.Use();
        m_Shader.UploadVec4("Color", this.Color);
        m_Shader.UploadMat4("Transform", this.GetEntityTransform(this.ParentEntity));
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
    private Matrix4f GetEntityTransform(Entity entity)
    {
        return GetTransform(entity.GetPosition(), entity.GetSize(), entity.GetRotation());
    }
    private Matrix4f GetTransform(Vector3f Position, Vector2f Size, float Rotation)
    {
        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(Position.x, Position.y, Position.z));
        transform.translate(new Vector3f(0.5f * Size.x, 0.5f * Size.y, Position.z));
        transform.rotate(Rotation, new Vector3f(0.0f, 0.0f, 1.0f));
        transform.translate(new Vector3f(-0.5f * Size.x, -0.5f * Size.y, Position.z));
        transform.scale(new Vector3f(Size.x, Size.y, 1.0f));
        return transform;
    }

}
