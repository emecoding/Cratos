package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.engineResource.*;
import com.cratos.entity.Entity;
import com.cratos.entity.component.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL31.GL_TEXTURE_BUFFER;

public class Renderer extends EngineSystem
{
    protected Shader SpriteShader = null;
    protected int SPRITE_VAO;
    protected int SPRITE_VBO;
    protected List<RenderComponent> SCENE_RENDER_COMPONENTS = null;

    private final int[] TEXT_INDICIES = {
            0,1,3,
            1,2,3
    };

    protected int TEXT_BATCH_SIZE = 100;
    protected int TEXT_VERTEX_SIZE = 7;
    protected float[] TextVertices = new float[TEXT_BATCH_SIZE*TEXT_VERTEX_SIZE];
    protected int CurrentTextSize = 0;
    protected int TEXT_VAO;
    protected int TEXT_VBO;
    protected Shader TextShader;
    protected CFont TextFont;
    protected Matrix4f TextProjection;

    @Override
    public void Initialize()
    {
        this.SpriteShader = EngineResourceManager.GetShader("SPRITE");
        this.SCENE_RENDER_COMPONENTS = new ArrayList<RenderComponent>();
    }
    private void InitializeSpriteRendering()
    {
        float vertices[] = new float[]{
                // pos      // tex
                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 0.0f, 0.0f,

                0.0f, 1.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 1.0f, 0.0f
        };

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(6 * 4);
        vertexData.put(vertices);
        vertexData.flip();

        SPRITE_VAO = glGenVertexArrays();
        SPRITE_VBO = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, SPRITE_VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        glBindVertexArray(this.SPRITE_VAO);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        List<Shader> shaders = EngineResourceManager.GetEveryShader();
        for(int i = 0; i < shaders.size(); i++)
        {
            shaders.get(i).Use();
            shaders.get(i).UploadMat4("Projection", RenderComponent.GetMainCameraProjection());
            shaders.get(i).UploadMat4("View", RenderComponent.GetMainCameraView());
            Shader.UnbindEveryShader();
        }
    }
    private void InitializeTextRendering()
    {
        this.TextShader = EngineResourceManager.GetShader("TEXT");

        try
        {
            Camera MainCamera = (Camera)Cratos.GetComponentFromScene(Camera.class);
            this.TextProjection = MainCamera.GetTextProjection();

        } catch (NullPointerException e)
        {
            Cratos.CratosDebug.Error(e.getMessage());
        }

        Shader.UnbindEveryShader();
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        this.TEXT_VAO = glGenVertexArrays();
        glBindVertexArray(this.TEXT_VAO);

        this.TEXT_VBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, this.TEXT_VBO);
        glBufferData(GL_ARRAY_BUFFER, Float.BYTES*this.TEXT_VERTEX_SIZE*this.TEXT_BATCH_SIZE, GL_DYNAMIC_DRAW);

        GenerateEBO();

        int stride = 7 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 5 * Float.BYTES);
        glEnableVertexAttribArray(2);

        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        Shader.UnbindEveryShader();
    }
    @Override
    public void Start()
    {
        this.InitializeSpriteRendering();
        this.InitializeTextRendering();

        this.UpdateRenderComponents();
    }
    @Override
    public void Destroy()
    {

    }
    public void RenderCurrentScene()
    {
        Shader.UnbindEveryShader();
        Camera cam = (Camera)Objects.requireNonNull(Cratos.GetComponentFromScene(Camera.class));

        this.SpriteShader.Use();
        this.SpriteShader.UploadMat4("View", cam.View);
        Shader.UnbindEveryShader();
        glBindVertexArray(this.SPRITE_VAO);

        for(RenderComponent component : this.SCENE_RENDER_COMPONENTS)
        {
            if(!ShouldRender(component.ParentEntity))
                continue;

            component.Render();
        }

        if(Cratos.CratosCursor.GetCurrentTexture() != -1)
        {
            TextureLoader.UseTexture(Cratos.CratosCursor.GetCurrentTexture());
            this.SpriteShader.UploadVec4("Color", Cratos.CratosCursor.GetColor());
            this.SpriteShader.UploadMat4("Transform", Cratos.CratosCursor.GetCursorTransform());
            glDrawArrays(GL_TRIANGLES, 0, 6);
            TextureLoader.UnbindEveryTexture();
        }

        glBindVertexArray(0);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);
        Shader.UnbindEveryShader();
    }
    public void UpdateRenderComponents()
    {
        this.SCENE_RENDER_COMPONENTS.clear();
        for(Entity entity : Cratos.CratosSceneManager.GetCurrentScene().GetEveryEntity())
        {
            for(Component component : entity.GetEveryComponent())
            {
                if(component.GetComponentType().equals(ComponentType.RENDER_COMPONENT))
                    this.SCENE_RENDER_COMPONENTS.add((RenderComponent) component);
            }
        }

        this.SCENE_RENDER_COMPONENTS = this.SortBasedOnRenderOrder();
    }
    private void GenerateEBO()
    {
        int elementSize = TEXT_BATCH_SIZE * 3;
        int[] elementBuffer = new int[elementSize];

        for (int i=0; i < elementSize; i++) {
            elementBuffer[i] = TEXT_INDICIES[(i % 6)] + ((i / 6) * 4);
        }

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
    }
    public void FlushTextFromRenderer()
    {
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, this.TEXT_VBO);
        glBufferData(GL_ARRAY_BUFFER, Float.BYTES * TEXT_VERTEX_SIZE * TEXT_BATCH_SIZE, GL_DYNAMIC_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, TextVertices);

        TextureLoader.UnbindEveryTexture();
        Shader.UnbindEveryShader();

        this.TextShader.Use();
        glBindTexture(GL_TEXTURE_BUFFER, this.TextFont.textureId);
        this.TextShader.UploadTexture("uFontTexture", 0);
        this.TextShader.UploadMat4("uProjection", this.TextProjection);

        glBindVertexArray(this.TEXT_VAO);

        glDrawElements(GL_TRIANGLES, this.CurrentTextSize * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        Shader.UnbindEveryShader();

        glBindVertexArray(this.SPRITE_VAO);

        this.CurrentTextSize = 0;
        this.TextVertices = new float[TEXT_BATCH_SIZE * TEXT_VERTEX_SIZE];
    }
    private List<RenderComponent> SortBasedOnRenderOrder()
    {
        List<RenderComponent> RenderComponents = new ArrayList<RenderComponent>();

        for(int i = 0; i < this.SCENE_RENDER_COMPONENTS.size(); i++)
        {

            RenderComponent CurrentComponent = this.SCENE_RENDER_COMPONENTS.get(i);

            if(i == 0)
            {
                RenderComponents.add(CurrentComponent);
                continue;
            }

            if(CurrentComponent.GetRenderOrder() > this.SCENE_RENDER_COMPONENTS.get(i-1).GetRenderOrder())
            {
                RenderComponents.add(CurrentComponent);
            }
            else
            {
                RenderComponents.add(i-1, CurrentComponent);
            }

        }

        return RenderComponents;
    }
    private boolean ShouldRender(Entity entity)
    {
        return ShouldRender(entity.GetPosition().x, entity.GetPosition().y, entity.GetSize().x, entity.GetSize().y);
    }
    private boolean ShouldRender(float x, float y, float w, float h)
    {
        float windowWidth = Cratos.CurrentWindow().Width;
        float windowHeight = Cratos.CurrentWindow().Height;

        if(x + w < 0)
            return false;

        if(x - w > windowWidth)
            return false;

        if(y - h > windowHeight)
            return false;

        if(y + h < 0)
            return false;

        return true;

    }
    private void AddCharacter(float x, float y, float scale, CharInfo charInfo, Vector3f rgb)
    {
        /*if (this.CurrentTextSize >= TEXT_BATCH_SIZE - 4) {
            FlushTextFromRenderer();
        }*/

        float r = rgb.x;
        float g = rgb.y;
        float b = rgb.z;

        float x0 = x;
        float y0 = y;
        float x1 = x + scale * charInfo.width;
        float y1 = y + scale * charInfo.height;

        float ux0 = charInfo.textureCoordinates[0].x; float uy0 = charInfo.textureCoordinates[0].y;
        float ux1 = charInfo.textureCoordinates[1].x; float uy1 = charInfo.textureCoordinates[1].y;

        int index = CurrentTextSize * 7;
        TextVertices[index] = x1;      TextVertices[index + 1] = y0;
        TextVertices[index + 2] = r;   TextVertices[index + 3] = g;  TextVertices[index + 4] = b;
        TextVertices[index + 5] = ux1; TextVertices[index + 6] = uy0;

        index += 7;
        TextVertices[index] = x1;      TextVertices[index + 1] = y1;
        TextVertices[index + 2] = r;   TextVertices[index + 3] = g;  TextVertices[index + 4] = b;
        TextVertices[index + 5] = ux1; TextVertices[index + 6] = uy1;

        index += 7;
        TextVertices[index] = x0;      TextVertices[index + 1] = y1;
        TextVertices[index + 2] = r;   TextVertices[index + 3] = g;  TextVertices[index + 4] = b;
        TextVertices[index + 5] = ux0; TextVertices[index + 6] = uy1;

        index += 7;
        TextVertices[index] = x0;      TextVertices[index + 1] = y0;
        TextVertices[index + 2] = r;   TextVertices[index + 3] = g;  TextVertices[index + 4] = b;
        TextVertices[index + 5] = ux0; TextVertices[index + 6] = uy0;

        this.CurrentTextSize += 4;
    }
    public void AddText(String text, int x, int y, float scale, Vector3f rgb)
    {
        for (int i=0; i < text.length(); i++) {
            char c = text.charAt(i);

            CharInfo charInfo = this.TextFont.GetCharacter(c);
            if (charInfo.width == 0) {
                Cratos.CratosDebug.Warning("Unknown character " + c);
                continue;
            }

            float xPos = x;
            float yPos = y;
            AddCharacter(xPos, yPos, scale, charInfo, rgb);
            x += charInfo.width * scale;
        }

        FlushTextFromRenderer();
    }
    public void SetFont(CFont font) { this.TextFont = font; }
    public void BindSpriteVAO() { glBindVertexArray(this.SPRITE_VAO); }
    public void UnbindEveryVAO() { glBindVertexArray(0); }
    public int GetCurrentFontSize() { return this.TextFont.GetFontSize(); }
}
