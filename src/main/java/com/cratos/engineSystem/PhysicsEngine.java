package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.entity.Entity;
import com.cratos.entity.component.Collider;
import com.cratos.entity.component.Rigidbody;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine extends EngineSystem
{
    protected int AMOUNT_OF_RIGIDBODIES_IN_SCENE = 0;
    protected int AMOUNT_OF_COLLIDERS_IN_SCENE = 0;
    protected List<Rigidbody> Rigidbodies = null;
    protected List<Collider> Colliders = null;
    protected float GravityForce = 0.3f;

    @Override
    public void Initialize()
    {
        this.Rigidbodies = new ArrayList<Rigidbody>();
        this.Colliders = new ArrayList<Collider>();
    }

    @Override
    public void Start()
    {
        this.AMOUNT_OF_RIGIDBODIES_IN_SCENE = Cratos.GetComponentsFromScene(Rigidbody.class).size();
        this.AMOUNT_OF_COLLIDERS_IN_SCENE = Cratos.GetComponentsFromScene(Collider.class).size();
    }

    public void Simulate()
    {
        this.UpdateRigidbodiesAndCollidersList();

        for(Rigidbody rb : this.Rigidbodies)
        {
            this.SimulateRigidbody(rb);
        }
    }
    protected void SimulateRigidbody(Rigidbody rb)
    {
        rb.Velocity.y += this.CalculateAccelerationForRigidbody(rb);
        //CALCULATE PHYSICS
        /*Collider MyCollider = (Collider) rb.ParentEntity.GetComponent(Collider.class);
        BUILTIN PHYSICS ENGINE IS UNDER CONSTRUCTION!
        if(MyCollider != null)
        {
            Vector2f MyPos = rb.ParentEntity.GetPositionVec2();

            List<Collider> collidesWith = this.CollidesWith(MyCollider);

            for(Collider other : collidesWith)
            {
                if(MyPos.y+rb.Velocity.y > other.Top)
                {
                    rb.Velocity.y = 0;
                    rb.ParentEntity.SetY(other.Top-rb.ParentEntity.GetSize().y);
                }

                /*if(MyPos.y-rb.Velocity.y < other.Bottom)
                    rb.Velocity.y = 0;
                if(MyPos.x+rb.Velocity.x > other.Left)
                    rb.Velocity.x = 0;
                if(MyPos.x-rb.Velocity.x < other.Right)
                    rb.Velocity.x = 0;
            }
        }
        */
        //APPLY VELOCITY
        Vector2f OldPos = rb.ParentEntity.GetPositionVec2();
        rb.ParentEntity.SetX(OldPos.x + rb.Velocity.x);
        rb.ParentEntity.SetY(OldPos.y + rb.Velocity.y);

    }
    private float CalculateAccelerationForRigidbody(Rigidbody rb)
    {
        return this.GravityForce * rb.Mass;
    }
    private void UpdateRigidbodiesAndCollidersList()
    {
        if(this.Rigidbodies.size() != this.AMOUNT_OF_RIGIDBODIES_IN_SCENE)
        {
            this.Rigidbodies.clear();
            List<Entity> entities = Cratos.CratosSceneManager.GetCurrentScene().GetEveryEntity();
            for(int i = 0; i < entities.size(); i++)
            {
                Rigidbody rb = (Rigidbody) entities.get(i).GetComponent(Rigidbody.class);
                if(rb != null)
                    this.Rigidbodies.add(rb);

                this.AMOUNT_OF_RIGIDBODIES_IN_SCENE = Cratos.GetComponentsFromScene(Rigidbody.class).size();
            }
        }

        if(this.Colliders.size() != this.AMOUNT_OF_COLLIDERS_IN_SCENE)
        {
            this.Colliders.clear();
            List<Entity> entities = Cratos.CratosSceneManager.GetCurrentScene().GetEveryEntity();
            for(int i = 0; i < entities.size(); i++)
            {
                Collider cl = (Collider) entities.get(i).GetComponent(Collider.class);
                if(cl != null)
                    this.Colliders.add(cl);
            }

            this.AMOUNT_OF_COLLIDERS_IN_SCENE = Cratos.GetComponentsFromScene(Collider.class).size();
        }

    }
    private List<Collider> CollidesWith(Collider myCollider)
    {
        List<Collider> colliders = new ArrayList<Collider>();
        for(int i = 0; i < this.AMOUNT_OF_COLLIDERS_IN_SCENE; i++)
        {
            Collider cl = this.Colliders.get(i);
            if(cl == myCollider)
                continue;

            if(myCollider.Collides(cl))
                colliders.add(cl);
        }

        return colliders;
    }
    @Override
    public void Destroy()
    {

    }

}
