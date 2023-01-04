package com.cratos.entity.component;

import com.cratos.engineResource.Shader;
import org.joml.Matrix4f;

public abstract class RenderComponent extends Component
{
    private int RenderOrder = 0;

    public RenderComponent()
    {
        this.SetComponentType(ComponentType.RENDER_COMPONENT);
    }
    @Override
    public void Initialize()
    {

    }

    @Override
    public void Start()
    {

    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Destroy()
    {

    }

    public void SetRenderOrder(int renderOrder) { this.RenderOrder = renderOrder; }
    public int GetRenderOrder() { return this.RenderOrder; }
    public abstract void Render(Shader shader, Matrix4f transform);
}
