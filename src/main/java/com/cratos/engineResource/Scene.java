package com.cratos.engineResource;

import com.cratos.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Scene extends EngineResource
{
    private List<Entity> ENTITIES;
    public Scene(String name)
    {
        super(name);
        this.ENTITIES = new ArrayList<Entity>();
    }

    public List<Entity> GetEveryEntity() { return this.ENTITIES; }
    public Entity AddEntity()
    {
        Entity e = new Entity();
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(String name)
    {
        Entity e = new Entity();
        e.Name = name;
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(Vector3f Position, Vector2f Size, float Rotation)
    {
        Entity e = new Entity(Position, Size, Rotation);
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(Vector2f Position, Vector2f Size, float Rotation)
    {
        Entity e = new Entity(Position, Size, Rotation);
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(Vector2f Position)
    {
        Entity e = new Entity(Position);
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(Vector3f Position)
    {
        Entity e = new Entity(Position);
        this.ENTITIES.add(e);
        return e;
    }
    public Entity AddEntity(float Rotation)
    {
        Entity e = new Entity(Rotation);
        this.ENTITIES.add(e);
        return e;
    }
    public int GetAmountOfEntities() {return this.ENTITIES.size();}
    @Override
    public void Destroy()
    {
        for(int i = 0; i < this.ENTITIES.size(); i++)
        {
            this.ENTITIES.get(i).Destroy();
            this.ENTITIES.remove(this.ENTITIES.get(i));
        }
    }
}
