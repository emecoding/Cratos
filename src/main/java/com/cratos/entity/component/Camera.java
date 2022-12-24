package com.cratos.entity.component;

import com.cratos.Cratos;
import com.cratos.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends Component
{
    public Matrix4f Projection = null;
    public Matrix4f View = null;
    public Vector3f Position = null;
    private Vector3f OldPosition = null;
    @Override
    public void Initialize()
    {
        Window CurrentWindow = Cratos.CurrentWindow();
        this.Position = new Vector3f(0.0f, 0.0f, 1.0f);
        this.OldPosition = new Vector3f(0.0f, 0.0f, 1.0f);
        this.Projection = new Matrix4f().ortho(0.0f, CurrentWindow.Width, CurrentWindow.Height, 0.0f, -1.0f, 1.0f);
        this.View = new Matrix4f();
    }


    @Override
    public void Start()
    {

    }

    @Override
    public void Update()
    {
        this.UpdateView();
    }

    @Override
    public void Destroy()
    {

    }

    private void UpdateView()
    {
        if(this.OldPosition.x != this.Position.x)
            this.View.translate(this.Position.x, 0.0f, 0.0f);
        if(this.OldPosition.y != this.Position.y)
            this.View.translate(0.0f, this.Position.y, 0.0f);
        if(this.OldPosition.z != this.Position.z)
            this.View.translate(0.0f, 0.0f, this.Position.z);

        this.OldPosition.x = this.Position.x;
        this.View = new Matrix4f().ortho(-1.0f, 1.0f, -1.0f, 1.0f, 0.1f, 100.0f).mul(this.View);

    }
}
