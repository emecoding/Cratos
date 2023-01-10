package com.cratos.uiComponent;

import org.joml.Vector2f;

public class Button extends UIComponent
{
    public Button()
    {
        super();
    }

    @Override
    public void Update(Vector2f MousePos)
    {
        this.CheckForMouse(MousePos);
    }
}
