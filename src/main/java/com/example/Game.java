package com.example;

import com.cratos.Cratos;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Scene;
import com.cratos.engineSystem.SceneManager;
import com.cratos.entity.Entity;
import com.cratos.entity.component.Camera;
import com.cratos.entity.component.Rigidbody;
import com.cratos.entity.component.Sprite;
import com.cratos.window.Window;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;


/*

Window window = Cratos.CreateWindow(1280, 720, "Game");

        Cratos.InitializeCratos();
        window.SetSkyboxColor(new Float[]{37.0f, 48.0f, 89.0f, 255.0f});

        //EngineResourceManager.AddTexture("TOILET", EngineResourceManager.EngineResourcesPath+"/sprites/toilet.png");//Builtin textures
        //EngineResourceManager.AddTexture("PUMPKIN", EngineResourceManager.EngineResourcesPath+"/sprites/pumpkin.png");

        SceneManager sceneManager = Cratos.CreateSceneManager();
        Scene SandBox = sceneManager.AddScene("Sandbox");

        Entity sampleEntity = SandBox.AddEntity("SampleEntity");
        Entity MainCamera = SandBox.AddEntity(new Vector3f(0.0f, 0.0f, 1.0f));
        MainCamera.AddComponent(new Camera());

        sampleEntity.SetX(200.0f);
        sampleEntity.SetY(0.0f);
        sampleEntity.SetWidth(32.0f);
        sampleEntity.SetHeight(32.0f);

        Sprite sprite = new Sprite();
        sprite.Color = Sprite.ConvertColorToGLSL(255, 255, 255, 255);
        sprite.RenderOrder = 1;
        //sprite.Texture = EngineResourceManager.GetTexture("TOILET");

        sampleEntity.AddComponent(new Collider());
        sampleEntity.AddComponent(sprite);
        sampleEntity.AddComponent(new Rigidbody());

        Cratos.CreateRenderer();
        Cratos.CreatePhysicsEngine();

        Cratos.Run();

        Cratos.Terminate();


 */


public class Game
{
    public static void main(String[] args)
    {
        Cratos.CreateWindow(1280, 720, "Game");
        Cratos.InitializeCratos();

        //EngineResourceManager.AddSpriteSheet("CURSOR", "/sprites/cursor.png", 32, 32);

        //SceneManager sceneManager = Cratos.CreateSceneManager();

        Scene mainScene = Cratos.CratosSceneManager.AddScene("Main");
        Entity MainCamera = mainScene.AddEntity("MainCamera");
        MainCamera.AddComponent(new Camera());

        Entity player = mainScene.AddEntity();
        player.SetX(100.0f);
        player.AddComponent(new Rigidbody());

        Sprite sp = new Sprite();
        sp.Color = Sprite.ConvertColorToGLSL(255.0f, 0.0f, 0.0f, 255.0f);
        player.AddComponent(sp);


        Cratos.Run();
        Cratos.Terminate();
    }
}
