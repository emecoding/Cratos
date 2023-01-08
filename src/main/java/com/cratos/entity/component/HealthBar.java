package com.cratos.entity.component;

import com.cratos.engineResource.Shader;
import com.cratos.engineUtils.EngineUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class HealthBar extends RenderComponent
{

    private int Width = 100;
    private int Height = 20;
    private float Value = 1.0f; //0.0f-1.0f;
    private Vector4f FillColor = null;
    private Vector4f BackgroundColor = null;
    private boolean BasicHealthBarFunctioning = false;
    public HealthBar()
    {
        super("RECTANGLE");
    }
    @Override
    public void Initialize()
    {
        if(FillColor == null)
            this.FillColor = new Vector4f(1.0f, 0.0f, 0.0f, 1.0f);
        if(BackgroundColor == null)
            this.BackgroundColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    @Override
    public void Update()
    {
        if(!this.BasicHealthBarFunctioning)
            return;

        this.SetValue((float) (this.Value-50.0f*EngineUtils.DeltaTime));
    }
    @Override
    public void Render()
    {
        Shader.UnbindEveryShader();
        m_Shader.Use();
        m_Shader.UploadVec4("Color", this.BackgroundColor);
        m_Shader.UploadMat4("Transform", GetTransform(this.ParentEntity.GetPosition(), new Vector2f(Width, Height)));
        glDrawArrays(GL_TRIANGLES, 0, 6);
        m_Shader.UploadVec4("Color", this.FillColor);
        m_Shader.UploadMat4("Transform", GetTransform(this.ParentEntity.GetPosition(), new Vector2f(Width+(Width-Width/this.Value), Height)));
        glDrawArrays(GL_TRIANGLES, 0, 6);
    }
    public void SetValue(float val)
    {
        if(val < 0.0f)
            val = 0.0f;
        else if(val > 1.0f)
            val = 1.0f;

        this.Value = val;
    }
    public void BasicHealthBarFunction() { this.BasicHealthBarFunctioning = true; }
    private Matrix4f GetTransform(Vector3f Position, Vector2f Size)
    {
        if(Size.x < 0)
            Size.x = 0.0f;

        if(Size.y < 0)
            Size.y = 0.0f;


        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(Position.x, Position.y, Position.z));
        transform.scale(new Vector3f(Size.x, Size.y, 1.0f));
        return transform;
    }
}
