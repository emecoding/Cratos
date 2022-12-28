package com.cratos.entity;

import com.cratos.engineUtils.EngineUtils;
import org.joml.Math;
import org.joml.Vector2f;

public class Transformations
{
    public static float RotateTowardsPoint(Vector2f MyPos, Vector2f Point)
    {
        Vector2f lookDir = new Vector2f(Point.x-MyPos.x, Point.y-MyPos.y);
        float angle = (float) (Math.atan2(lookDir.y, lookDir.x)*(360/(Math.PI*2)));
        angle = Math.toRadians(angle);
        return angle;
    }

    public static float Distance(Vector2f a, Vector2f b)
    {
        return a.distance(b);
    }

    public static Vector2f MoveTowardsPoint(Vector2f MyPos, Vector2f Point, float Speed)
    {
        Vector2f Dir = new Vector2f(Point.x-MyPos.x, Point.y-MyPos.y);
        return new Vector2f(MyPos.x, MyPos.y).add(Dir.mul((float) (Speed* EngineUtils.DeltaTime)));
    }

    public static Vector2f MoveAroundPoint(Vector2f MyPos, Vector2f Center, float theta)
    {
        float x = Math.cos(theta) * (MyPos.x - Center.x) - Math.sin(theta) * (MyPos.y - Center.y) + Center.x;
        float y = Math.sin(theta) * (MyPos.x - Center.x) + Math.cos(theta) * (MyPos.y - Center.y) + Center.y;
        return new Vector2f(x, y);
    }


}
