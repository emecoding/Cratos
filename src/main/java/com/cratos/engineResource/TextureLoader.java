package com.cratos.engineResource;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureLoader
{
    public static int LoadTexture(String path)
    {
        try
        {
            PNGDecoder decoder = new PNGDecoder(EngineResourceManager.class.getResourceAsStream(path));
            ByteBuffer Buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(Buffer, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
            Buffer.flip();

            int TEX = glGenTextures();

            TextureLoader.UseTexture(TEX);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, Buffer);

            glGenerateMipmap(GL_TEXTURE_2D);
            TextureLoader.UnbindEveryTexture();

            return TEX;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return -1;
    }
    public static void UseTexture(int tex)
    {
        //glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, tex);
    }
    public static void UnbindEveryTexture() { glBindTexture(GL_TEXTURE_2D, 0); }
    public static void DestroyTexture(int tex) { glDeleteTextures(tex); }
}
