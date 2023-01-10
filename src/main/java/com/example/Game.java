package com.example;

import com.cratos.Cratos;
import com.cratos.engineResource.CFont;
import com.cratos.engineResource.EngineResourceManager;
import com.cratos.engineResource.Scene;
import com.cratos.entity.Entity;
import com.cratos.entity.component.*;
import com.cratos.uiComponent.Button;
import com.cratos.window.Window;

import java.util.concurrent.Callable;

public class Game
{
    public static void main(String[] args)
    {
        Window window = Cratos.CreateWindow(1280, 720, "Game");
        Cratos.InitializeCratos();
        window.Center();

        CFont f = EngineResourceManager.AddFont("BOXY", "Boxy-Bold.ttf", 16);
        Cratos.CratosRenderer.SetFont(f);

        Scene mainScene = Cratos.CratosSceneManager.AddScene("Main");
        Entity MainCamera = mainScene.AddEntity("MainCamera");
        MainCamera.AddComponent(new Camera());

        Entity player = mainScene.AddEntity();
        player.SetX(500.0f);
        player.SetY(100.0f);

        Sprite sp = (Sprite) player.AddComponent(new Sprite());
        sp.Color = Sprite.ConvertColorToGLSL(255.0f, 255.0f, 0.0f, 255.0f);
        sp.Texture = EngineResourceManager.GetTexture("TOILET");

        Text txt = (Text) player.AddComponent(new Text());
        txt.SetContent("Eemeli");

        HealthBar bar = (HealthBar) player.AddComponent(new HealthBar());
        bar.BasicHealthBarFunction();


        Button comp = (Button) Cratos.CratosUIManager.AddComponent(new Button());
        comp.SetContent("Click Me!");
        comp.SetX(0.0f);
        comp.SetY(0.0f);
        comp.SetWidth(120.0f);
        comp.SetHeight(200.0f);
        comp.SetColor(Sprite.ConvertColorToGLSL(255.0f, 0.0f, 0.0f, 255.0f));

        comp.SetOnLeftClick(new Callable<Integer>()
        {
            @Override
            public Integer call() throws Exception
            {
                comp.SetContent("Left Clicked!");
                comp.SetColor(Sprite.ConvertColorToGLSL(0.0f, 255.0f, 0.0f, 255.0f));
                return 0;
            }
        });

        comp.SetOnRightClick(new Callable<Integer>()
        {
            @Override
            public Integer call() throws Exception
            {
                comp.SetContent("Right Clicked!");
                comp.SetColor(Sprite.ConvertColorToGLSL(255.0f, 0.0f, 0.0f, 255.0f));
                return 0;
            }
        });

        Cratos.Run();
        Cratos.Terminate();
    }
}
