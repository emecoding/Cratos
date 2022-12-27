package com.cratos.engineSystem;

import com.cratos.Cratos;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager extends EngineSystem
{
    public static int BUTTON_A = 0;
    public static int BUTTON_B = 1;
    public static int BUTTON_X = 2;
    public static int BUTTON_Y = 3;
    public static int BUTTON_LEFT_BUMPER = 4;
    public static int BUTTON_RIGHT_BUMPER = 5;
    private long WindowLong = -1;
    @Override
    public void Initialize()
    {
        WindowLong = Cratos.CurrentWindow().GetWindowID();
    }

    public boolean KeyPressed(int key) { return glfwGetKey(Cratos.CurrentWindow().GetWindowID(), key) == GLFW_PRESS; }
    public boolean JoystickButtonPressed(int button, int stick) { return GetJoystickButtons(stick).get(button) == 1; }
    public boolean JoystickButtonPressed(int button) { return GetJoystickButtons(GLFW_JOYSTICK_1).get(button) == 1; }
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
    public ByteBuffer GetJoystickButtons(int stick) { return glfwGetJoystickButtons(stick); }
    public ByteBuffer GetJoystickButtons() { return GetJoystickButtons(GLFW_JOYSTICK_1); }
    public Vector2f GetMousePosition()
    {
        DoubleBuffer x_pos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y_pos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(Cratos.CurrentWindow().GetWindowID(), x_pos, y_pos);

        return new Vector2f((float) x_pos.get(0), (float) y_pos.get(0));
    }
    public boolean MouseButtonPressed(int key) { return glfwGetMouseButton(Cratos.CurrentWindow().GetWindowID(), key) == GLFW_PRESS; }
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
