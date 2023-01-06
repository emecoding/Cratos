package com.cratos.engineResource;

public abstract class EngineResource
{
    public String m_Name;
    public EngineResource(String name)
    {
        m_Name = name;
    }
    public abstract void Destroy();
}
