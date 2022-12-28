package com.cratos.engineSystem;
import com.cratos.Cratos;
import com.cratos.engineResource.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SceneManager extends EngineSystem
{
    protected List<Scene> SCENES = null;
    protected int CurrentSceneIndex = 0;

    @Override
    public void Initialize()
    {
        SCENES = new ArrayList<Scene>();
    }

    @Override
    public void Start()
    {

    }

    public Scene AddScene(String name)
    {
        Scene s = new Scene(name);
        this.SCENES.add(s);
        return s;
    }
    public Scene AddScene(Scene scene)
    {
        this.SCENES.add(scene);
        return scene;
    }
    public Scene AddScene()
    {
        String name = "Scene(" + this.SCENES.size() + ")";
        return AddScene(name);
    }
    public Scene GetCurrentScene()
    {
        return GetScene(this.CurrentSceneIndex);
    }
    public Scene GetScene(String name)
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            if(this.SCENES.get(i).m_Name.equals(name))
            {
                return this.SCENES.get(i);
            }
        }

        Cratos.CratosDebug.Error("No scene found with name: " + name);
        return null;
    }
    public Scene GetScene(int scene)
    {
        if(scene < 0 || scene >= this.SCENES.size())
        {
            Cratos.CratosDebug.Error("No scenes found with index: " + scene);
            return null;
        }

        return this.SCENES.get(scene);

    }
    public void ChangeSceneTo(String name)
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            if(this.SCENES.get(i).m_Name.equals(name))
            {
                this.CurrentSceneIndex = i;
                return;
            }
        }
    }
    public void ChangeSceneTo(Scene scene)
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            if(this.SCENES.get(i).equals(scene))
            {
                this.CurrentSceneIndex = i;
                return;
            }
        }
    }
    public void ChangeSceneTo(int scene)
    {
        if(scene < 0 || scene >= this.SCENES.size())
            Cratos.CratosDebug.Error("Invalid scene index set: " + scene);

        this.CurrentSceneIndex = scene;

    }
    public void RemoveScene(String name)
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            if(this.SCENES.get(i).m_Name.equals(name))
            {
                this.SCENES.remove(this.SCENES.get(i));
                if(this.CurrentSceneIndex == i)
                {
                    this.CurrentSceneIndex -= 1;
                    if(this.CurrentSceneIndex < 0)
                        this.CurrentSceneIndex = 0;
                }

                return;
            }
        }

        Cratos.CratosDebug.Error("No scenes found with name: " + name);
    }
    public void RemoveScene(Scene scene)
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            if(this.SCENES.get(i).equals(scene))
            {
                this.SCENES.remove(this.SCENES.get(i));
                if(this.CurrentSceneIndex == i)
                {
                    this.CurrentSceneIndex -= 1;
                    if(this.CurrentSceneIndex < 0)
                        this.CurrentSceneIndex = 0;
                }

                return;
            }
        }

        Cratos.CratosDebug.Error("No scenes found with name: " + scene.m_Name);
    }
    public void RemoveScene(int scene)
    {
        if(scene < 0 || scene >= this.SCENES.size())
            Cratos.CratosDebug.Error("No scenes found with index: " + scene);
        this.SCENES.remove(this.SCENES.get(scene));

        if(this.CurrentSceneIndex == scene)
        {
            this.CurrentSceneIndex -= 1;
            if(this.CurrentSceneIndex < 0)
                this.CurrentSceneIndex = 0;
        }
    }
    public List<Scene> GetEveryScene()
    {
        return this.SCENES;
    }
    @Override
    public void Destroy()
    {
        for(int i = 0; i < this.SCENES.size(); i++)
        {
            this.SCENES.get(i).Destroy();
        }
    }
}
