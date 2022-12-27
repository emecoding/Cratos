package com.cratos.entity;

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

    public static Vector2f MoveTowardsPoint(Vector2f MyPos, Vector2f Point, float Speed)
    {
        Vector2f Dir = new Vector2f(Point.x-MyPos.x, Point.y-MyPos.y);
        return new Vector2f(MyPos.x, MyPos.y).add(Dir.mul(Speed));
    }
}
