package com.cratos.entity.component;

import com.cratos.Cratos;
import com.cratos.engineUtils.EngineUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ParticleSystem extends Component
{
    private int FrameRate = -1;
    private int CurrentFrame = 0;
    private int[] Frames;
    private float FrameTimer = -1;
    private float FrameSpeed = -1;
    private Vector4f Color;
    private Vector2f Position;
    private Vector2f Size;
    private boolean IsPlaying = false;
    private boolean IsLoop = false;
    @Override
    public void Initialize()
    {
        this.SetColor(Sprite.ConvertColorToGLSL(255.0f, 255.0f, 255.0f, 255.0f));
        this.Position = new Vector2f(0.0f, 0.0f);
        this.Size = new Vector2f(0.0f, 0.0f);

        this.SetPosition(this.ParentEntity.GetPositionVec2());
        this.SetSize(this.ParentEntity.GetSize());
    }
    @Override
    public void Start()
    {

    }
    @Override
    public void Update()
    {
        if(!IsPlaying)
            return;

        if(this.Frames.length <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frames for the particle system.");
            return;
        }

        if(this.FrameRate <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frame rate for the particle system.");
            return;
        }

        if(this.FrameSpeed <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frame speed for the particle system.");
            return;
        }

        this.FrameTimer += this.FrameSpeed * EngineUtils.DeltaTime;
        if(this.FrameTimer >= this.FrameRate)
        {
            this.FrameTimer = 0;
            this.CurrentFrame++;
            if(this.CurrentFrame >= this.Frames.length)
            {
                if(!this.IsLoop)
                    this.Stop();

                this.CurrentFrame = 0;
            }


        }

    }
    @Override
    public void Destroy()
    {

    }
    public void SetFrames(int[] Frames)
    {
        this.Frames = Frames;
    }
    public void AddFrame(int Frame) { this.Frames[this.Frames.length] = Frame; }
    public void SetFrameRate(int frameRate) { this.FrameRate = frameRate; }
    public void SetFrameSpeed(float speed) { this.FrameSpeed += speed; }
    public void SetColor(Vector4f Color) { this.Color = Color; }
    public void SetPosition(Vector2f pos)
    {
        this.Position.x = pos.x;
        this.Position.y = pos.y;
    }
    public void SetX(float x) { this.Position.x = x; }
    public void SetY(float y) { this.Position.y = y; }
    public void SetSize(Vector2f size)
    {
        this.Size.x = size.x;
        this.Size.y = size.y;
    }
    public void SetWidth(float w) { this.Size.x = w; }
    public void SetHeight(float h) { this.Size.y = h; }
    public void Loop() { this.IsLoop = true; }
    public void Play() { this.IsPlaying = true; }
    public void Stop() { this.IsPlaying = false; }
    public int GetCurrentFrame() { return this.Frames[this.CurrentFrame]; }
    public Vector4f GetColor() { return this.Color; }
    public Vector2f GetPosition() { return this.Position; }
    public Vector2f GetSize() { return this.Size; }
    public Matrix4f GetParticleSystemTransform()
    {
        Matrix4f transform = new Matrix4f();
        transform.translate(new Vector3f(this.Position.x-this.Size.x/2, this.Position.y-this.Size.y/2, 0.0f));
        transform.scale(new Vector3f(this.Size.x, this.Size.y, 1.0f));
        return transform;
    }
    public boolean IsPlaying() { return this.IsPlaying; }
}
