package com.cratos.engineResource;

import com.cratos.Cratos;
import com.cratos.entity.component.ParticleSystem;

import java.io.InputStream;
import java.util.*;

public class EngineResourceManager
{
    private static List<Shader> m_EngineShaders;
    private static List<SpriteSheet> m_SpriteSheets;
    private static List<CFont> m_Fonts;
    private static HashMap<String, Integer> m_Textures;
    public static void InitEngineResources()
    {
        m_EngineShaders = new ArrayList<Shader>();
        m_SpriteSheets = new ArrayList<SpriteSheet>();
        m_Fonts = new ArrayList<CFont>();
        m_Textures = new HashMap<String, Integer>();

        AddShader("SPRITE", "/shaders/SpriteVs.glsl", "/shaders/SpriteFs.glsl");
        AddShader("TEXT", "/shaders/TextVs.glsl", "/shaders/TextFs.glsl");

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
    public static CFont AddFont(String name, String filepath, int size)
    {
        CFont f = new CFont(name);
        f.SetFilePath(filepath);
        f.SetFontSize(size);
        f.Initialize();
        m_Fonts.add(f);
        return f;
    }
    public static CFont GetFont(String name)
    {
        for(CFont font : m_Fonts)
        {
            if(font.m_Name.equals(name))
                return font;
        }

        return null;
    }
    public static SpriteSheet AddSpriteSheet(String name, String path, int SpriteWidth, int SpriteHeight)
    {
        SpriteSheet s = new SpriteSheet(name, path, SpriteWidth, SpriteHeight);
        m_SpriteSheets.add(s);
        return s;
    }
    public static SpriteSheet GetSpriteSheet(String name)
    {
        for(int i = 0; i < m_SpriteSheets.size(); i++)
        {
            if(m_SpriteSheets.get(i).m_Name.equals(name))
                return m_SpriteSheets.get(i);
        }

        Cratos.CratosDebug.Error("No sprite sheet found called " + name);
        return null;
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
