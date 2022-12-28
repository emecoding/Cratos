package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Shader;
import com.cratos.engineResource.SpriteSheet;
import com.cratos.engineResource.TextureLoader;
import com.cratos.entity.Entity;
import com.cratos.entity.component.Camera;
import com.cratos.entity.component.Component;
import com.cratos.entity.component.ParticleSystem;
import com.cratos.entity.component.Sprite;
import org.joml.Matrix4f;
import org.joml.Vector2f;
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

public class Renderer extends EngineSystem
{
    protected Shader SpriteShader = null;
    protected int VAO;
    protected int VBO;
    protected int AMOUNT_OF_SPRITES_IN_SCENE = 0;
    protected int AMOUNT_OF_PARTICLE_SYSTEMS_IN_SCENE = 0;
    protected List<Sprite> SCENE_SPRITES = null;
    protected List<ParticleSystem> SCENE_PARTICLE_SYSTEMS = null;
    @Override
    public void Initialize()
    {
        this.SpriteShader = EngineResourceManager.GetShader("SPRITE");
        this.SCENE_SPRITES = new ArrayList<Sprite>();
        this.SCENE_PARTICLE_SYSTEMS = new ArrayList<ParticleSystem>();
    }
    @Override
    public void Start()
    {
        this.AMOUNT_OF_SPRITES_IN_SCENE = Cratos.GetComponentsFromScene(Sprite.class).size();
        this.AMOUNT_OF_PARTICLE_SYSTEMS_IN_SCENE = Cratos.GetComponentsFromScene(SpriteSheet.class).size();
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

        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        glBindVertexArray(this.VAO);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        try
        {
            this.SpriteShader.Use();
            Camera MainCamera = (Camera)Cratos.GetComponentFromScene(Camera.class);
            this.SpriteShader.UploadMat4("Projection", MainCamera.Projection);
            Shader.UnbindEveryShader();
        } catch (NullPointerException e)
        {
            Cratos.CratosDebug.Error(e.getMessage());
        }
    }
    @Override
    public void Destroy()
    {

    }
    public void RenderEntity(Entity entity)
    {
        if(!ShouldRender(entity))
            return;

        Shader.UnbindEveryShader();
        Camera cam = (Camera)Objects.requireNonNull(Cratos.GetComponentFromScene(Camera.class));
        Sprite sprite = (Sprite)entity.GetComponent(Sprite.class);
        this.SpriteShader.Use();
        if(sprite.Texture > -1) TextureLoader.UseTexture(sprite.Texture);
        this.SpriteShader.UploadVec4("Color", sprite.Color);
        this.SpriteShader.UploadMat4("transform", GetEntityTransform(entity));
        this.SpriteShader.UploadMat4("view", cam.View);
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
        TextureLoader.UnbindEveryTexture();
        Shader.UnbindEveryShader();
    }
    public void RenderCurrentScene()
    {
        this.GetEveryRenderComponent();

        Shader.UnbindEveryShader();
        Camera cam = (Camera)Objects.requireNonNull(Cratos.GetComponentFromScene(Camera.class));

        this.SpriteShader.Use();
        this.SpriteShader.UploadMat4("View", cam.View);
        glBindVertexArray(VAO);

        for (Sprite sprite : this.SCENE_SPRITES)
        {
            if(!ShouldRender(sprite.ParentEntity))
                continue;

            if(sprite.Texture > -1) TextureLoader.UseTexture(sprite.Texture);
            this.SpriteShader.UploadVec4("Color", sprite.Color);
            this.SpriteShader.UploadMat4("Transform", GetEntityTransform(sprite.ParentEntity));
            glDrawArrays(GL_TRIANGLES, 0, 6);
            TextureLoader.UnbindEveryTexture();
        }

        for(ParticleSystem system : this.SCENE_PARTICLE_SYSTEMS)
        {
            if(!system.IsPlaying())
                continue;

            if(!ShouldRender(system.ParentEntity))
                continue;

            if(system.GetCurrentFrame() > -1) TextureLoader.UseTexture(system.GetCurrentFrame());
            this.SpriteShader.UploadVec4("Color", system.GetColor());
            this.SpriteShader.UploadMat4("Transform", system.GetParticleSystemTransform());
            glDrawArrays(GL_TRIANGLES, 0, 6);
            TextureLoader.UnbindEveryTexture();

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
        Shader.UnbindEveryShader();
    }
    private void GetEveryRenderComponent()
    {
        this.AMOUNT_OF_SPRITES_IN_SCENE = Cratos.GetComponentsFromScene(Sprite.class).size();
        if(this.SCENE_SPRITES.size() != this.AMOUNT_OF_SPRITES_IN_SCENE)
        {
            this.SCENE_SPRITES.clear();
            List<Component> comps = Cratos.GetComponentsFromScene(Sprite.class);
            for(Component comp : comps)
                this.SCENE_SPRITES.add((Sprite) comp);

            this.SCENE_SPRITES = this.SortBasedOnRenderOrder();

        }

        this.AMOUNT_OF_PARTICLE_SYSTEMS_IN_SCENE = Cratos.GetComponentsFromScene(ParticleSystem.class).size();
        if(this.SCENE_PARTICLE_SYSTEMS.size() != this.AMOUNT_OF_PARTICLE_SYSTEMS_IN_SCENE)
        {
            this.SCENE_PARTICLE_SYSTEMS.clear();
            List<Component> comps = Cratos.GetComponentsFromScene(ParticleSystem.class);
            for(Component comp : comps)
                this.SCENE_PARTICLE_SYSTEMS.add((ParticleSystem) comp);

        }
    }
    private Matrix4f GetEntityTransform(Entity entity)
    {
        return GetTransform(entity.GetPosition(), entity.GetSize(), entity.GetRotation());
    }
    private Matrix4f GetTransform(Vector3f Position, Vector2f Size, float Rotation)
    {
        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(Position.x, Position.y, Position.z));
        transform.translate(new Vector3f(0.5f * Size.x, 0.5f * Size.y, Position.z));
        transform.rotate(Rotation, new Vector3f(0.0f, 0.0f, 1.0f));
        transform.translate(new Vector3f(-0.5f * Size.x, -0.5f * Size.y, Position.z));
        transform.scale(new Vector3f(Size.x, Size.y, 1.0f));
        return transform;
    }
    private List<Sprite> SortBasedOnRenderOrder()
    {
        List<Sprite> sprites = new ArrayList<Sprite>();

        for(int i = 0; i < this.SCENE_SPRITES.size(); i++)
        {
            Sprite CurrentSprite = this.SCENE_SPRITES.get(i);

            if(i == 0)
            {
                sprites.add(CurrentSprite);
                continue;
            }

            if(CurrentSprite.RenderOrder > this.SCENE_SPRITES.get(i-1).RenderOrder)
            {
                sprites.add(CurrentSprite);
            }
            else
            {
                sprites.add(i-1, CurrentSprite);
            }

        }

        return sprites;
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
}
