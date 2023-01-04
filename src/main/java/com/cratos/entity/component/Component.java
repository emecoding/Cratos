package com.cratos.entity.component;

import com.cratos.entity.Entity;

public abstract class Component
{
    public Entity ParentEntity = null;
    public boolean UseInUpdate = true;
    protected ComponentType m_Type;
    public void SetParent(Entity parent) { this.ParentEntity = parent; }
    protected void SetComponentType(ComponentType t) { this.m_Type = t; }
    public ComponentType GetComponentType() { return this.m_Type; }
    public abstract void Initialize();
    public abstract void Start();
    public abstract void Update();
    public abstract void Destroy();
}
