package com.cratos.entity.component;

import org.joml.Vector2f;

public class Collider extends Component
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
}
