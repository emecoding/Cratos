package com.cratos.entity.component;

import com.cratos.Cratos;
import com.cratos.engineResource.Shader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Text extends RenderComponent
{
    private String Content = "";
    private float Size = 1f;
    private Vector3f Color = null;
    public void SetContent(String c) { this.Content = c; }
    public void SetColor(Vector3f Color) { this.Color = Color; }
    public void  SetSize(int size) { this.Size = size; }
    public String GetContent() { return this.Content; }

    public Text()
    {
        super("TEXT");
    }
    @Override
    public void Initialize()
    {

    }
    @Override
    public void Render()
    {
        if(this.Content.equals(""))
            return;
        if(this.Color == null)
            this.Color = new Vector3f(1.0f, 1.0f, 1.0f);

        Vector2f Position = this.ParentEntity.GetPositionVec2();
        Cratos.CratosRenderer.AddText(this.Content, (int) Position.x, (int) Position.y, this.Size, this.Color);
    }
}
