package com.game;

import com.cratos.Cratos;
import com.cratos.entity.component.Component;
import com.cratos.entity.component.Rigidbody;
import com.cratos.window.Window;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerMovement extends Component
{
    @Override
    public void Initialize() {

    }

    @Override
    public void Start() {

    }

    @Override
    public void Update()
    {
        Window window = Cratos.CurrentWindow();
        Rigidbody myRb = (Rigidbody) this.ParentEntity.GetComponent(Rigidbody.class);
        float y = 0;
        float x = 0;
        if(window.KeyPressed(GLFW_KEY_UP))
            y += -1;

        if(window.KeyPressed(GLFW_KEY_DOWN))
            y += 1;

        if(window.KeyPressed(GLFW_KEY_RIGHT))
            x += 1;

        if(window.KeyPressed(GLFW_KEY_LEFT))
            x += -1;

        this.ParentEntity.SetX(this.ParentEntity.GetPosition().x+x);
        this.ParentEntity.SetY(this.ParentEntity.GetPosition().y+y);


    }

    @Override
    public void Destroy() {

    }
}
