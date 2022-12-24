package com.cratos.engineSystem;

import com.cratos.Cratos;
import com.cratos.engineUtils.EngineUtils;

public class Debug extends EngineSystem
{
    private String GetMessageForDebugMessage(String text, DebugMessageType type)
    {
        String typeInString = "";
        if(type == DebugMessageType.LOG)
            typeInString = "LOG";
        else if(type == DebugMessageType.ERROR)
            typeInString = "ERROR";
        else if(type == DebugMessageType.WARNING)
            typeInString = "WARNING";

        return String.format("[CRATOS %s]: %s (%d s)", typeInString, text, EngineUtils.TimeElapsed);
    }
    public void Log(Object text)
    {
        System.out.println(GetMessageForDebugMessage(text.toString(), DebugMessageType.LOG));
    }

    public void Error(Object text)
    {
        System.err.println(GetMessageForDebugMessage(text.toString(), DebugMessageType.ERROR));
        Cratos.Terminate(-1);
    }
    public void Warning(Object text)
    {
        System.out.println(GetMessageForDebugMessage(text.toString(), DebugMessageType.WARNING));
    }

    @Override
    public void Initialize()
    {

    }

    @Override
    public void Destroy()
    {

    }
}
