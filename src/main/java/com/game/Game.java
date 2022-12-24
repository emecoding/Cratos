package com.game;

import com.cratos.Cratos;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Scene;
import com.cratos.engineSystem.Renderer;
import com.cratos.engineSystem.SceneManager;
import com.cratos.entity.Entity;
import com.cratos.entity.component.Camera;
import com.cratos.entity.component.Collider;
import com.cratos.entity.component.Rigidbody;
import com.cratos.entity.component.Sprite;
import com.cratos.window.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Game
{
    public static void main(String[] args)
    {
        Window window = Cratos.CreateWindow(1280, 720, "Game");

        Cratos.InitializeCratos();

        EngineResourceManager.AddTexture("TOILET", EngineResourceManager.EngineResourcesPath+"/sprites/toilet.png");
        EngineResourceManager.AddTexture("PUMPKIN", EngineResourceManager.EngineResourcesPath+"/sprites/pumpkin.png");

        SceneManager sceneManager = Cratos.CreateSceneManager();
        Scene SandBox = sceneManager.AddScene();

        Entity player = SandBox.AddEntity("Player");
        Entity NPC = SandBox.AddEntity(new Vector2f(200.0f, 100.0f));
        Entity CAM = SandBox.AddEntity(new Vector3f(0.0f, 0.0f, 1.0f));
        CAM.AddComponent(new Camera());

        player.SetX(200.0f);
        player.SetY(0.0f);
        player.SetWidth(32.0f);
        player.SetHeight(32.0f);

        Sprite sprite = new Sprite();
        sprite.Color = Sprite.ConvertColorToGLSL(255, 255, 255, 255);
        sprite.RenderOrder = 50;
        sprite.Texture = EngineResourceManager.GetTexture("TOILET");

        Sprite sprite1 = new Sprite();
        sprite1.Color = Sprite.ConvertColorToGLSL(255, 255, 0, 255);
        sprite1.RenderOrder = 100;
        sprite1.Texture = EngineResourceManager.GetTexture("PUMPKIN");

        NPC.AddComponent(sprite1);
        NPC.AddComponent(new Collider());

        player.AddComponent(new Collider());
        player.AddComponent(sprite);
        player.AddComponent(new Rigidbody());
        player.AddComponent(new PlayerMovement());

        Cratos.CreateRenderer();
        Cratos.CreatePhysicsEngine();

        Cratos.Run();
        //window.SetSkyboxColor(255.0f, 0.0f, 0.0f, 255.0f);

        Cratos.Terminate();

    }
}
