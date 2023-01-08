package com.cratos.engineResource;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL20.*;

public class Shader extends EngineResource
{
    private int programId;
    public Shader(String name, InputStream vertex_path, InputStream fragment_path)
    {
        super(name);

        try
        {
            programId = glCreateProgram();
            int vertex_shader = glCreateShader(GL_VERTEX_SHADER);
            int fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);

            glShaderSource(vertex_shader, GetShaderFileData(vertex_path));
            glShaderSource(fragment_shader, GetShaderFileData(fragment_path));
            glCompileShader(vertex_shader);
            glCompileShader(fragment_shader);
            if (glGetShaderi(vertex_shader, GL_COMPILE_STATUS) == GL_FALSE)
            {
                System.out.println(glGetShaderInfoLog(vertex_shader, 500));
                System.err.println("Could not compile shader "+ vertex_path);
                System.exit(-1);
            }

            if (glGetShaderi(fragment_shader, GL_COMPILE_STATUS) == GL_FALSE)
            {
                System.out.println(glGetShaderInfoLog(fragment_shader, 500));
                System.err.println("Could not compile shader "+ fragment_path);
                System.exit(-1);
            }

            glAttachShader(programId, vertex_shader);
            glAttachShader(programId, fragment_shader);

            glDeleteShader(vertex_shader);
            glDeleteShader(fragment_shader);

            glLinkProgram(programId);
            if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
                System.err.println("Program failed to link");
                System.err.println(glGetProgramInfoLog(programId, glGetProgrami(programId, GL_INFO_LOG_LENGTH)));
            }
            glValidateProgram(programId);
            if (glGetProgrami(programId, GL_VALIDATE_STATUS) == GL_FALSE) {
                System.err.println("Program failed to validate");
                System.err.println(glGetProgramInfoLog(programId, glGetProgrami(programId, GL_INFO_LOG_LENGTH)));
            }
            Shader.UnbindEveryShader();

            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    private String GetShaderFileData(InputStream stream) throws IOException
    {
        String data = "";
        Scanner sc = new Scanner(stream);

        while(sc.hasNextLine())
        {
            String d = sc.nextLine() + "\n";
            data += d;
        }

        return data;
    }
    public void Use() { glUseProgram(programId); }
    public static void UnbindEveryShader() { glUseProgram(0); }
    public void UploadMat4(String name, Matrix4f mat4)
    {
        try(MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer fb = stack.mallocFloat(16);
            mat4.get(fb);
            glUniformMatrix4fv(glGetUniformLocation(programId, name), false, fb);
        }

    }
    public void UploadVec3(String name, Vector3f vec3)
    {
        glUniform3f(glGetUniformLocation(programId, name), vec3.x, vec3.y, vec3.z);
    }
    public void UploadVec4(String name, Vector4f vec4)
    {
        glUniform4f(glGetUniformLocation(programId, name), vec4.x, vec4.y, vec4.z, vec4.w);
    }
    public void UploadTexture(String varName, int slot) {
        int varLocation = glGetUniformLocation(programId, varName);
        glUniform1i(varLocation, slot);
    }
    public void UploadBool(String name, boolean bool)
    {
        int val = 0;
        if(bool)
            val = 1;
        glUniform1i(glGetUniformLocation(programId, name), val);
    }
    @Override
    public void Destroy()
    {
        glDeleteProgram(programId);
    }
}