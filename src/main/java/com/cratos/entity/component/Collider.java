package com.cratos.entity.component;

import com.cratos.entity.Entity;
import org.joml.Vector2f;

public class Collider extends BehaviourComponent
{
    public float Right, Left, Top, Bottom;
    public Vector2f Size;
    @Override
    public void Initialize()
    {
        this.Size = new Vector2f(0.0f, 0.0f);
        this.UpdateBounds();
    }
    @Override
    public void Start()
    {

    }
    @Override
    public void Update()
    {
        this.UpdateBounds();
    }
    @Override
    public void Destroy()
    {

    }
    private void UpdateBounds()
    {
        Vector2f Pos = this.ParentEntity.GetPositionVec2();
        Vector2f ParentSize = this.ParentEntity.GetSize();
        this.Right = Pos.x + ParentSize.x + this.Size.x;
        this.Left = Pos.x - this.Size.x;
        this.Top = Pos.y - this.Size.y;
        this.Bottom = Pos.y + ParentSize.y + this.Size.x;
    }
    public boolean Collides(Collider other)
    {
        Vector2f pos = this.ParentEntity.GetPositionVec2();
        Vector2f otherPos = other.ParentEntity.GetPositionVec2();
        if( pos.x < other.Right &&
            this.Right > otherPos.x &&
            pos.y < other.Bottom &&
            this.Bottom > otherPos.y)
        {
            return true;
        }

        return false;

    }
    public boolean Collides(Entity other)
    {
        Vector2f pos = this.ParentEntity.GetPositionVec2();
        Vector2f size = this.ParentEntity.GetSize();
        Vector2f otherPos = other.GetPositionVec2();
        Vector2f otherSize = other.GetSize();
        if(pos.x < otherPos.x + otherSize.x &&
            pos.x + size.x > otherPos.x &&
            pos.y < otherPos.y + otherSize.y &&
            pos.y + size.y > otherPos.y)
        {
            return true;
        }

        return false;
    }
    public boolean Collides(float x, float y, float w, float h)
    {
        Vector2f pos = this.ParentEntity.GetPositionVec2();
        Vector2f size = this.ParentEntity.GetSize();
        if(pos.x < x + w &&
                pos.x + size.x > x &&
                pos.y < y + h &&
                pos.y + size.y > y)
        {
            return true;
        }

        return false;
    }
}
