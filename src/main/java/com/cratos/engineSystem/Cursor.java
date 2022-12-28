package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.entity.component.Sprite;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class Cursor extends EngineSystem
{
    private int Mode;
    private int DefaultTexture = -1;
    private int OnLeftClickTexture = -1;
    private int OnRightClickTexture = -1;
    private int CurrentTexture = -1;
    private int Width;
    private int Height;
    private Vector4f Color;
    @Override
    public void Initialize()
    {
        this.Color = Sprite.ConvertColorToGLSL(255.0f, 255.0f, 255.0f, 255.0f);
        this.SetMode(GLFW_CURSOR_NORMAL);
    }

    @Override
    public void Start() {

    }

    public void SetDefaultTexture(int tex) { this.DefaultTexture = tex; }
    public void SetOnLeftClickTexture(int tex) { this.OnLeftClickTexture = tex; }
    public void SetOnRightClickTexture(int tex) { this.OnRightClickTexture = tex; }
    public void Update()
    {
        if(Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) && this.OnLeftClickTexture != -1)
            this.CurrentTexture = this.OnLeftClickTexture;
        else if(Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_RIGHT) && this.OnRightClickTexture != -1)
            this.CurrentTexture = this.OnRightClickTexture;
        else
            this.CurrentTexture = this.DefaultTexture;
    }
    public void SetSize(int w, int h)
    {
        this.Width = w;
        this.Height = h;
    }
    public void SetColor(Vector4f color) { this.Color = color; }
    public void SetMode(int mode)
    {
        this.Mode = mode;
        glfwSetInputMode(Cratos.CurrentWindow().GetWindowID(), GLFW_CURSOR, this.Mode);
    }
    public int GetCurrentTexture() { return this.CurrentTexture; }
    public Matrix4f GetCursorTransform()
    {
        Vector2f MousePos = Cratos.CratosInputManager.GetMousePosition();
        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(MousePos.x-this.Width/2, MousePos.y-this.Height/2, 0.0f));
        transform.scale(new Vector3f(this.Width, this.Height, 1.0f));
        return transform;
    }
    public Vector4f GetColor() { return this.Color; }
    @Override
    public void Destroy()
    {

    }
}
