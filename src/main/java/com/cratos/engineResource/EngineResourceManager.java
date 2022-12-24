package com.cratos.engineResource;

import com.cratos.Cratos;
import com.cratos.engineSystem.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineResourceManager
{
    private static List<Shader> m_EngineShaders;
    private static HashMap<String, Integer> m_Textures;
    public static String EngineResourcesPath = "src/main/engineResources/";
    public static void InitEngineResources()
    {
        m_EngineShaders = new ArrayList<Shader>();
        m_Textures = new HashMap<String, Integer>();
        AddShader("SPRITE", EngineResourcesPath+"/shaders/SpriteVs.glsl", EngineResourcesPath+"/shaders/SpriteFs.glsl");

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
        m_EngineShaders.add(new Shader(name, vertexPath, fragmentPath));
    }
    public static void AddTexture(String name, String path)
    {
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
