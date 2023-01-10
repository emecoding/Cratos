package com.cratos.uiComponent;

import com.cratos.Cratos;
import com.cratos.entity.component.Collider;
import com.cratos.entity.component.Sprite;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public abstract class UIComponent
{
    protected Vector2f Position = null;
    protected Vector2f Size = null;
    protected Vector4f Color = null;
    protected int Texture = -1;
    protected float ContentTextScale = 1f;
    protected String Content = "";
    protected Callable<Integer> OnLeftClick = null;
    protected Callable<Integer> OnRightClick = null;
    private boolean JustLeftClicked = false;
    private boolean JustRightClicked = false;
    public UIComponent()
    {
        this.Position = new Vector2f(0.0f, 0.0f);
        this.Size = new Vector2f(0.0f, 0.0f);
        this.Color = Sprite.ConvertColorToGLSL(255, 255, 255, 255);
    }
    public UIComponent(Vector2f Position, Vector2f Size)
    {
        this.Position = Position;
        this.Size = Size;
        this.Color = Sprite.ConvertColorToGLSL(255, 255, 255, 255);
    }
    public Vector2f GetPosition() { return this.Position; }
    public Vector2f GetSize() { return this.Size; }
    public int GetTexture() { return this.Texture; }
    public Vector4f GetColor() { return this.Color; }
    public String GetContent() { return this.Content; }
    public float GetContentTextScale() { return this.ContentTextScale; }
    public void SetPosition(Vector2f pos)
    {
        this.Position.x = pos.x;
        this.Position.y = pos.y;
    }
    public void SetSize(Vector2f size)
    {
        this.Size.x = size.x;
        this.Size.y = size.y;
    }
    public void SetContent(String s) { this.Content = s; }
    public void SetX(float x) { this.Position.x = x; }
    public void SetY(float y) { this.Position.y = y; }
    public void SetWidth(float w) { this.Size.x = w; }
    public void SetHeight(float h) { this.Size.y = h; }
    public void SetTexture(int tex) { this.Texture = tex; }
    public void SetColor(Vector4f Color) { this.Color = Color; }
    public void SetOnLeftClick(Callable<Integer> onLeftClick) { this.OnLeftClick = onLeftClick; }
    public void SetOnRightClick(Callable<Integer> onRightClick) { this.OnRightClick = onRightClick; }
    public void SetContentTextScale(float s) { this.ContentTextScale = s; }
    public Matrix4f GetUIComponentTransform()
    {
        return GetUIComponentTransform(new Vector3f(this.GetPosition().x, this.GetPosition().y, 0.0f), this.GetSize(), 0.0f);
    }
    private Matrix4f GetUIComponentTransform(Vector3f Position, Vector2f Size, float Rotation)
    {
        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(Position.x, Position.y, Position.z));
        transform.translate(new Vector3f(0.5f * Size.x, 0.5f * Size.y, Position.z));
        transform.rotate(Rotation, new Vector3f(0.0f, 0.0f, 1.0f));
        transform.translate(new Vector3f(-0.5f * Size.x, -0.5f * Size.y, Position.z));
        transform.scale(new Vector3f(Size.x, Size.y, 1.0f));
        return transform;
    }
    protected void CheckForMouse(Vector2f MousePos)
    {
        if(this.Collides(MousePos.x, MousePos.y, 10, 10))
        {
            this.SetColor(new Vector4f(this.Color.x, this.Color.y, this.Color.z, 0.75f));

            if(Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) && !JustLeftClicked)
            {
                JustLeftClicked = true;
                if(this.OnLeftClick != null)
                {
                    try
                    {
                        this.OnLeftClick.call();
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(!Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT))
                JustLeftClicked = false;

            if(Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_RIGHT) && !JustRightClicked)
            {
                JustRightClicked = true;
                if(this.OnRightClick != null)
                {
                    try
                    {
                        this.OnRightClick.call();
                    }
                    catch (Exception e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(!Cratos.CratosInputManager.MouseButtonPressed(GLFW_MOUSE_BUTTON_RIGHT))
                JustRightClicked = false;

        }
        else
        {
            this.SetColor(new Vector4f(this.Color.x, this.Color.y, this.Color.z, 1.0f));
        }
    }
    public abstract void Update(Vector2f MousePos);
    protected boolean Collides(float x, float y, float w, float h)
    {
        Vector2f pos = this.GetPosition();
        Vector2f size = this.GetSize();
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
