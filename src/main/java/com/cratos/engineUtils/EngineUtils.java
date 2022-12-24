package com.cratos.engineUtils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class EngineUtils
{
    private static final long StartTime = System.currentTimeMillis();
    private static double LastTime = glfwGetTime();
    public static long TimeElapsed;
    public static double DeltaTime = 0.0;
    public static void UpdateTime()
    {
        long endTime = System.currentTimeMillis();
        TimeElapsed = endTime - StartTime;
        TimeElapsed = TimeElapsed/1000;

        double CurrentTime = glfwGetTime();
        DeltaTime = (CurrentTime-LastTime)/1000.0f;
        LastTime = CurrentTime;

    }

}
