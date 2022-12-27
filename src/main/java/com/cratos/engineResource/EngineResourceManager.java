package com.cratos.engineResource;

import com.cratos.Cratos;
import java.io.InputStream;
import java.util.*;

public class EngineResourceManager
{
    private static List<Shader> m_EngineShaders;
    private static List<SpriteSheet> m_SpriteSheets;
    private static HashMap<String, Integer> m_Textures;
    public static void InitEngineResources()
    {
        m_EngineShaders = new ArrayList<Shader>();
        m_SpriteSheets = new ArrayList<SpriteSheet>();
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
        m_Textures.put(name, TextureLoader.LoadTexture(path));
    }
    public static void AddSpriteSheet(String name, String path, int SpriteWidth, int SpriteHeight)
    {
        m_SpriteSheets.add(new SpriteSheet(name, path, SpriteWidth, SpriteHeight));
    }
    public static int GetTextureFromSpriteSheet(String name, int x, int y)
    {
        for(int i = 0; i < m_SpriteSheets.size(); i++)
        {
            if(m_SpriteSheets.get(i).m_Name.equals(name))
            {
                return m_SpriteSheets.get(i).GetTextureID(x, y);
            }
        }

        Cratos.CratosDebug.Error("No texture found from " + name + " with coordinates (" + x + "," + y + ")");
        return -1;
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
