package com.cratos.engineSystem;

import com.cratos.Cratos;
import org.joml.Vector2f;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager extends EngineSystem
{
    private long WindowLong = -1;
    @Override
    public void Initialize()
    {
        WindowLong = Cratos.CurrentWindow().GetWindowID();
    }

    public boolean KeyPressed(int key) { return glfwGetKey(Cratos.CurrentWindow().GetWindowID(), key) == GLFW_PRESS; }
    public boolean JoystickIsPresent(int stick) { return glfwJoystickPresent(stick); }
    public boolean JoystickIsPresent() { return JoystickIsPresent(GLFW_JOYSTICK_1); }
    public Vector2f GetLeftStickInput(int stick)
    {
        FloatBuffer axes = glfwGetJoystickAxes(stick);
        float x = axes.get(0);
        float y = axes.get(1);

        x = Round(x, 1);
        y = Round(y, 1);


        return new Vector2f(x, y);
    }
    public Vector2f GetLeftStickInput() { return GetLeftStickInput(GLFW_JOYSTICK_1); }
    public Vector2f GetRightStickInput(int stick)
    {
        FloatBuffer axes = glfwGetJoystickAxes(stick);
        float x = axes.get(2);
        float y = axes.get(3);

        x = Round(x, 1);
        y = Round(y, 1);

        return new Vector2f(x, y);
    }
    public Vector2f GetRightStickInput() { return GetRightStickInput(GLFW_JOYSTICK_1); }
    private float Round(float value, int precision)
    {
        int scale = (int)Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
    @Override
    public void Destroy()
    {

    }
}
