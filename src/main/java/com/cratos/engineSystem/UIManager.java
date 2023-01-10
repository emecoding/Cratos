package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Shader;
import com.cratos.uiComponent.UIComponent;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class UIManager extends EngineSystem
{
    private List<UIComponent> UIComponents = null;
    private Shader SpriteShader = null;
    private Shader RectangleShader = null;
    @Override
    public void Initialize()
    {
        this.UIComponents = new ArrayList<UIComponent>();
    }

    @Override
    public void Start()
    {
        this.SpriteShader = EngineResourceManager.GetShader("SPRITE");
        this.RectangleShader = EngineResourceManager.GetShader("RECTANGLE");
    }

    public void Manage()
    {
        Cratos.CratosRenderer.UnbindEveryVAO();
        Cratos.CratosRenderer.BindSpriteVAO();
        Vector2f MousePosition = Cratos.CratosInputManager.GetMousePosition();
        for(int i = 0; i < this.UIComponents.size(); i++)
        {
            UIComponent CurrentComponent = this.UIComponents.get(i);

            //UPDATE
            CurrentComponent.Update(MousePosition);

            //RENDER
            Shader.UnbindEveryShader();
            if(CurrentComponent.GetTexture() != -1)
            {
                this.SpriteShader.Use();
                this.SpriteShader.UploadMat4("Transform", CurrentComponent.GetUIComponentTransform());
                glDrawArrays(GL_TRIANGLES, 0, 6);
            }
            else
            {
                this.RectangleShader.Use();
                this.RectangleShader.UploadVec4("Color", CurrentComponent.GetColor());
                this.RectangleShader.UploadMat4("Transform", CurrentComponent.GetUIComponentTransform());
                glDrawArrays(GL_TRIANGLES, 0, 6);
            }

            if(!CurrentComponent.GetContent().equals(""))
            {
                Shader.UnbindEveryShader();
                Cratos.CratosRenderer.UnbindEveryVAO();
                float y = Cratos.CurrentWindow().Height-(int) CurrentComponent.GetPosition().y - (Cratos.CratosRenderer.GetCurrentFontSize()*CurrentComponent.GetContentTextScale())/2 - CurrentComponent.GetSize().y/2;
                float x = (CurrentComponent.GetPosition().x + CurrentComponent.GetSize().x/2) - ((Cratos.CratosRenderer.GetCurrentFontSize()*CurrentComponent.GetContentTextScale()) * CurrentComponent.GetContent().length())/4;
                Cratos.CratosRenderer.AddText(CurrentComponent.GetContent(), (int) x, (int)y, CurrentComponent.GetContentTextScale(), new Vector3f(1.0f, 1.0f, 1.0f));
            }


        }

        Cratos.CratosRenderer.UnbindEveryVAO();
    }

    @Override
    public void Destroy()
    {

    }

    public UIComponent AddComponent(UIComponent Component)
    {
        this.UIComponents.add(Component);
        return Component;
    }

}
