package com.cratos.entity.component;

import com.cratos.entity.Entity;

public abstract class Component
{
    public Entity ParentEntity = null;
    public boolean UseInUpdate = true;
    public void SetParent(Entity parent) { this.ParentEntity = parent; }
    public abstract void Initialize();
    public abstract void Start();
    public abstract void Update();
    public abstract void Destroy();
}
