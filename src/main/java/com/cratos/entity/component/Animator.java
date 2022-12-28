package com.cratos.entity.component;

import com.cratos.Cratos;
import com.cratos.engineUtils.EngineUtils;

public class Animator extends Component
{
    private Sprite m_Sprite;
    private int CurrentFrame = -1;
    private int FrameRate = -1;
    private float FrameTimer = 0;
    private float FrameSpeed = -1;
    private int[] Frames;


    @Override
    public void Initialize()
    {

    }

    @Override
    public void Start()
    {
        this.m_Sprite = (Sprite) this.ParentEntity.GetComponent(Sprite.class);
        if(this.m_Sprite == null)
            Cratos.CratosDebug.Error("No sprite component found from " + this.ParentEntity.Name);

        this.CurrentFrame = 0;
    }

    @Override
    public void Update()
    {
        if(this.m_Sprite == null)
            return;

        if(this.Frames.length <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frames for the animator.");
            return;
        }

        if(this.FrameRate <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frame rate for the animator.");
            return;
        }

        if(this.FrameSpeed <= 0)
        {
            Cratos.CratosDebug.Warning("Please assign the frame speed for the animator.");
            return;
        }

        this.FrameTimer += this.FrameSpeed * EngineUtils.DeltaTime;
        if(this.FrameTimer >= this.FrameRate)
        {
            this.FrameTimer = 0;
            this.CurrentFrame++;
            if(this.CurrentFrame >= this.Frames.length)
                this.CurrentFrame = 0;

        }

        this.m_Sprite.Texture = this.Frames[this.CurrentFrame];

    }

    public void SetFrames(int[] Frames)
    {
        this.Frames = Frames;
    }
    public void AddFrame(int Frame) { this.Frames[this.Frames.length] = Frame; }
    public void SetFrameRate(int frameRate) { this.FrameRate = frameRate; }
    public void SetFrameSpeed(float speed) { this.FrameSpeed += speed; }

    @Override
    public void Destroy()
    {

    }
}
