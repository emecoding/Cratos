package com.cratos.engineResource;

import com.cratos.Cratos;
import com.cratos.engineSystem.Debug;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EngineResourceManager
{
    private static List<Shader> m_EngineShaders;
    private static HashMap<String, Integer> m_Textures;
    public static String EngineResourcesPath = "src/main/resources/";
    public static void InitEngineResources()
    {
        m_EngineShaders = new ArrayList<Shader>();
        m_Textures = new HashMap<String, Integer>();

        AddShader("SPRITE", "/shaders/SpriteVs.glsl", "/shaders/SpriteFs.glsl");

        AddTexture("PUMPKIN", "/sprites/pumpkin.png");
        AddTexture("TOILET", "/sprites/toilet.png");

        Cratos.CratosDebug.Log("Engine Resources Initialized!");
    }
    public static Shader GetShader(String name)
    {
        for(int i = 0; i < m_EngineShaders.size(); i++)
        {
            if(m_EngineShaders.get(i).m_Name.equals(name))
                return m_EngineShaders.get(i);
        }

        Cratos.CratosDebug.Error("Failed to find shader named " + name);
        return null;
    }
    public static void AddShader(String name, String vertexPath, String fragmentPath)
    {
        InputStream Vertex = EngineResourceManager.class.getResourceAsStream(vertexPath);
        InputStream Fragment = EngineResourceManager.class.getResourceAsStream(fragmentPath);

        m_EngineShaders.add(new Shader(name, Vertex, Fragment));
    }
    public static void AddTexture(String name, String path)
    {
        //Cratos.CratosDebug.Log(TextureLoader.class.getResource(path).getPath());
        m_Textures.put(name, TextureLoader.LoadTexture(path));
    }
    public static int GetTexture(String name)
    {
        for(Map.Entry<String, Integer> entry : m_Textures.entrySet())
        {
            if(entry.getKey().equals(name))
                return entry.getValue();
        }

        Cratos.CratosDebug.Error("Failed to find texture named " + name);
        return -1;
    }
}
