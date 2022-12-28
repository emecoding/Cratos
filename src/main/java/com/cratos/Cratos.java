package com.cratos;

import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineSystem.*;
import com.cratos.engineUtils.EngineUtils;
import com.cratos.entity.Entity;
import com.cratos.entity.component.Component;
import com.cratos.window.Window;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class Cratos
{
    private static Window m_Window = null;
    public static Debug CratosDebug = null;
    public static SceneManager CratosSceneManager = null;
    public static Renderer CratosRenderer = null;
    public static PhysicsEngine CratosPhysicsEngine = null;
    public static InputManager CratosInputManager = null;
    public static Cursor CratosCursor = null;
    public static Window CreateWindow(int width, int height, String title)
    {
        m_Window = new Window(width, height, title);
        m_Window.Init();
        return m_Window;
    }
    public static SceneManager CreateSceneManager()
    {
        CratosSceneManager = new SceneManager();
        CratosSceneManager.Initialize();
        return CratosSceneManager;
    }
    public static SceneManager CreateSceneManager(SceneManager manager)
    {
        CratosSceneManager = manager;
        CratosSceneManager.Initialize();
        return CratosSceneManager;
    }
    public static Renderer CreateRenderer()
    {
        CratosRenderer = new Renderer();
        CratosRenderer.Initialize();
        return CratosRenderer;
    }
    public static Renderer CreateRenderer(Renderer renderer)
    {
        CratosRenderer = renderer;
        CratosRenderer.Initialize();
        return CratosRenderer;
    }
    public static PhysicsEngine CreatePhysicsEngine()
    {
        CratosPhysicsEngine = new PhysicsEngine();
        CratosPhysicsEngine.Initialize();
        return CratosPhysicsEngine;
    }
    public static PhysicsEngine CreatePhysicsEngine(PhysicsEngine engine)
    {
        CratosPhysicsEngine = engine;
        CratosPhysicsEngine.Initialize();
        return CratosPhysicsEngine;
    }
    public static InputManager CreateInputManager()
    {
        CratosInputManager = new InputManager();
        CratosInputManager.Initialize();
        return CratosInputManager;
    }
    public static InputManager CreateInputManager(InputManager manager)
    {
        CratosInputManager = manager;
        CratosInputManager.Initialize();
        return manager;
    }
    public static Cursor CreateCursor()
    {
        CratosCursor = new Cursor();
        CratosCursor.Initialize();
        return CratosCursor;
    }
    public static Cursor CreateCursor(Cursor cursor)
    {
        CratosCursor = cursor;
        CratosCursor.Initialize();
        return CratosCursor;
    }
    public static Debug CreateDebug()
    {
        CratosDebug = new Debug();
        CratosDebug.Initialize();
        return CratosDebug;
    }
    public static Debug CreateDebug(Debug debug)
    {
        CratosDebug = debug;
        CratosDebug.Initialize();
        return CratosDebug;
    }
    public static void Update()
    {
        EngineUtils.UpdateTime();
        List<Entity> Entities = CratosSceneManager.GetCurrentScene().GetEveryEntity();
        for(int i = 0; i < Entities.size(); i++)
        {
            List<Component> comps = Entities.get(i).GetEveryComponent();
            for(int j = 0; j < comps.size(); j++)
            {
                if(comps.get(j).UseInUpdate)
                    comps.get(j).Update();
            }
        }
    }
    public static void Start()
    {
        List<Entity> Entities = CratosSceneManager.GetCurrentScene().GetEveryEntity();
        for(int i = 0; i < Entities.size(); i++)
        {
            List<Component> comps = Entities.get(i).GetEveryComponent();
            for(int j = 0; j < comps.size(); j++)
            {
                comps.get(j).Start();
            }
        }
    }
    public static void Run()
    {
        StartEngine();
        while(!m_Window.ShouldClose())
        {
            m_Window.Clear();
            CratosCursor.Update();
            if(CratosPhysicsEngine != null) CratosPhysicsEngine.Simulate();
            CratosRenderer.RenderCurrentScene();
            Cratos.Update();
            m_Window.SwapBuffersAndPollEvents();
        }
    }
    public static void InitializeCratos()
    {
        if(CratosDebug == null)
            CreateDebug();

        if(m_Window == null)
            CreateWindow(400, 400, "Cratos");

        EngineResourceManager.InitEngineResources();

        if(CratosSceneManager == null)
            CreateSceneManager();

        if(CratosInputManager == null)
            CreateInputManager();

        if(CratosCursor == null)
            CreateCursor();

        if(CratosPhysicsEngine == null)
            CreatePhysicsEngine();

        if(CratosRenderer == null)
            CreateRenderer();

        CratosDebug.Log("Cratos Initialized Successfully!");
    }

    public static void StartEngine()
    {
        CratosDebug.Start();
        CratosSceneManager.Start();
        CratosInputManager.Start();
        CratosCursor.Start();
        CratosPhysicsEngine.Start();
        CratosRenderer.Start();
    }
    public static void Terminate()
    {
        CratosDebug.Log("Terminating...");
        CratosSceneManager.Destroy();
        CratosRenderer.Destroy();
        CratosPhysicsEngine.Destroy();
        m_Window.DestroyWindow();
        CratosInputManager.Destroy();
        CratosDebug.Destroy();
        glfwTerminate();
    }
    public static void Terminate(int state)
    {
        Terminate();
        System.exit(state);
    }
    public static Window CurrentWindow()
    {
        if(m_Window == null) CratosDebug.Warning("No Window Found!");
        return m_Window;
    }
    public static Component GetComponentFromScene(Class<?> type)
    {
        List<Entity> entities = CratosSceneManager.GetCurrentScene().GetEveryEntity();
        for(int i = 0; i < entities.size(); i++)
        {
            if(entities.get(i).GetComponent(type) != null) return entities.get(i).GetComponent(type);

        }

        return null;
    }
    public static List<Component> GetComponentsFromScene(Class<?> type)
    {
        List<Entity> entities = CratosSceneManager.GetCurrentScene().GetEveryEntity();
        List<Component> components = new ArrayList<Component>();
        for(int i = 0; i < entities.size(); i++)
        {
            Component comp = entities.get(i).GetComponent(type);
            if(comp != null)
                components.add(comp);

        }

        return components;
    }
}
