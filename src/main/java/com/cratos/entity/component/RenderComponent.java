package com.cratos.entity.component;

import com.cratos.Cratos;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Shader;
import org.joml.Matrix4f;

public abstract class RenderComponent extends Component
{
    private int RenderOrder = 0;
    protected Shader m_Shader = null;

    public RenderComponent(String shaderName)
    {
        this.SetComponentType(ComponentType.RENDER_COMPONENT);
        this.m_Shader = EngineResourceManager.GetShader(shaderName);
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
    public static Matrix4f GetMainCameraView()
    {
        try
        {
            Camera MainCamera = (Camera) Cratos.GetComponentFromScene(Camera.class);
            return MainCamera.View;
        }
        catch (NullPointerException e)
        {
            Cratos.CratosDebug.Error(e.getMessage());
        }

        return null;
    }
    public static Matrix4f GetMainCameraProjection()
    {
        try
        {
            Camera MainCamera = (Camera)Cratos.GetComponentFromScene(Camera.class);
            return MainCamera.Projection;
        }
        catch (NullPointerException e)
        {
            Cratos.CratosDebug.Error(e.getMessage());
        }

        return null;
    }
    public abstract void Render();
}
