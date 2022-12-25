package com.cratos.entity;

import com.cratos.entity.component.Component;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Entity
{
    protected Vector3f Position = null;
    protected Vector2f Size = null;
    protected float Rotation = 0;
    protected List<Component> Components = new ArrayList<Component>();
    public String Name;

    public Entity()
    {
        this.Position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.Size = new Vector2f(32.0f, 32.0f);
    }
    public Entity(Vector3f Position, Vector2f Size, float Rotation)
    {
        this.Position = Position;
        this.Size = Size;
        this.Rotation = Rotation;
    }
    public Entity(Vector2f Position, Vector2f Size, float Rotation)
    {
        this.Position = new Vector3f(Position.x, Position.y, 0.0f);
        this.Size = Size;
        this.Rotation = Rotation;
    }
    public Entity(Vector2f Position)
    {
        this.Position = new Vector3f(Position.x, Position.y, 0.0f);
        this.Size = new Vector2f(32.0f, 32.0f);
    }
    public Entity(Vector3f Position)
    {
        this.Position = Position;
        this.Size = new Vector2f(32.0f, 32.0f);
    }
    public Entity(float Rotation)
    {
        this.Position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.Size = new Vector2f(32.0f, 32.0f);
        this.Rotation = Rotation;
    }
    public Component AddComponent(Component comp)
    {
        this.Components.add(comp);
        comp.SetParent(this);
        comp.Initialize();
        return comp;
    }
    public Component GetComponent(Class<?> type)
    {
        for(int i = 0; i < this.Components.size(); i++)
        {
            if(this.Components.get(i).getClass() == type)
                return this.Components.get(i);
        }

        return null;
    }
    public void RemoveComponent(Class<?> type)
    {
        for(int i = 0; i < this.Components.size(); i++)
        {
            if(this.Components.get(i).getClass() == type)
                this.Components.remove(this.Components.get(i));
        }
    }
    public Vector3f GetPosition() { return this.Position; }
    public Vector2f GetPositionVec2() { return new Vector2f(this.Position.x, this.Position.y); }
    public Vector2f GetSize() { return this.Size; }
    public float GetRotation() { return this.Rotation; }
    public List<Component> GetEveryComponent() { return this.Components; }
    public void SetPosition(Vector2f Position)
    {
        this.Position.x = Position.x;
        this.Position.y = Position.y;
    }
    public void SetPosition(Vector3f Position)
    {
        this.Position.x = Position.x;
        this.Position.y = Position.y;
        this.Position.z = Position.z;
    }
    public void SetPosition(float x, float y)
    {
        this.Position.x = x;
        this.Position.y = y;
    }
    public void SetX(float x)
    {
        this.Position.x = x;
    }
    public void SetY(float y) { this.Position.y = y; }
    public void SetZ(float z) { this.Position.z = z; }
    public void SetSize(Vector2f Size)
    {
        this.Size = Size;
    }
    public void SetWidth(float w) { this.Size.x = w; }
    public void SetHeight(float h) { this.Size.y = h; }
    public void SetRotation(float rot)
    {
        this.Rotation = rot;
    }
    public void IncreaseRotation(float a)
    {
        this.Rotation += a;
    }
    public void DecreaseRotation(float a)
    {
        this.Rotation -= a;
    }
    public void Destroy()
    {
        for(int i = 0; i < this.Components.size(); i++)
        {
            this.Components.get(i).Destroy();
            this.Components.remove(this.Components.get(i));
        }
    }
}
