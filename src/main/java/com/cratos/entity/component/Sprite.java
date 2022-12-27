package com.cratos.entity.component;

import com.cratos.Cratos;
import org.joml.Vector4f;

public class Sprite extends Component
{
    public Vector4f Color = new Vector4f();
    public int Texture = -1;
    public int RenderOrder = 1;
    @Override
    public void Initialize()
    {
        this.UseInUpdate = false;
        this.Color = Sprite.ConvertColorToGLSL(255.0f, 255.0f, 255.0f, 255.0f);
    }
    @Override
    public void Start()
    {

    }
    @Override
    public void Update()
    {
        if(this.Color.x > 1.0f || this.Color.y > 1.0f || this.Color.z > 1.0f || this.Color.w > 1.0f) Cratos.CratosDebug.Error("Not a proper color value!");
    }
    @Override
    public void Destroy()
    {

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
