package com.cratos.entity.component;

import org.joml.Vector2f;

public class Rigidbody extends Component
{
    public Vector2f Velocity;
    public float Mass;
    @Override
    public void Initialize()
    {
        this.Velocity = new Vector2f(0.0f, 0.0f);
        this.Mass = 1;
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
}
