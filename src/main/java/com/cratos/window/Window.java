package com.cratos.window;

import com.cratos.Cratos;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window
{
    public String Title;
    private Float[] SkyboxColor;
    private long m_Window;
    public int Width;
    public int Height;
    public Window(int width, int height, String title)
    {
        this.Title = title;
        this.Width = width;
        this.Height = height;
    }
    public void Init()
    {
        //InitWindow()
        SkyboxColor = new Float[4];
        this.SetSkyboxColor(new Float[]{37.0f, 48.0f, 89.0f, 255.0f});
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Failed to init glfw...");

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        m_Window = glfwCreateWindow(Width, Height, this.Title, MemoryUtil.NULL, MemoryUtil.NULL);

        if(m_Window == MemoryUtil.NULL)
            throw new IllegalStateException("Failed to create window...");

        glfwSetWindowSizeCallback(m_Window, new GLFWWindowSizeCallback()
        {
            @Override
            public void invoke(long window, int width, int height)
            {
                Cratos.CurrentWindow().Width = width;
                Cratos.CurrentWindow().Height = height;
            }
        });

        glfwSetKeyCallback(m_Window, (window, key, scancode, action, mods) -> {
            if(key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
                this.SetCloseTrue();
        });

        glfwMakeContextCurrent(m_Window);
        glfwSwapInterval(1);
        glfwShowWindow(m_Window);

        GL.createCapabilities();
    }
    public void Clear()
    {
        glClearColor(this.SkyboxColor[0], this.SkyboxColor[1], this.SkyboxColor[2], this.SkyboxColor[3]);
        glClear(GL_COLOR_BUFFER_BIT);
    }
    public void SwapBuffersAndPollEvents()
    {
        glfwSwapBuffers(m_Window);
        glfwPollEvents();
    }
    public void DestroyWindow()
    {
        Callbacks.glfwFreeCallbacks(m_Window);
        glfwDestroyWindow(m_Window);
    }
    public boolean ShouldClose() { return glfwWindowShouldClose(m_Window); }
    public void SetCloseTrue() { glfwSetWindowShouldClose(m_Window, true); }
    public void SetSkyboxColor(Float[] color)
    {
        float r = color[0]/255;
        float g = color[1]/255;
        float b = color[2]/255;
        float a = color[3]/255;
        this.SkyboxColor[0] = r;
        this.SkyboxColor[1] = g;
        this.SkyboxColor[2] = b;
        this.SkyboxColor[3] = a;
    }
    public void SetSkyboxColor(float r, float g, float b, float a)
    {
        this.SkyboxColor[0] = r/255;
        this.SkyboxColor[1] = g/255;
        this.SkyboxColor[2] = b/255;
        this.SkyboxColor[3] = a/255;
    }
    public void Center()
    {
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode mode = glfwGetVideoMode(monitor);
        glfwSetWindowPos(this.m_Window,mode.width()/2-this.Width/2, mode.height()/2-this.Height/2);
    }
    public long GetWindowID() { return m_Window; }
}
