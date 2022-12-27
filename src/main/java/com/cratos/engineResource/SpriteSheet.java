package com.cratos.engineResource;



import com.cratos.Cratos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class SpriteSheet extends EngineResource
{
    private int SpriteWidth;
    private int SpriteHeight;
    private int TexWidth;
    private int TexHeight;

    private String PATH;
    private Shader m_Shader;

    private List<Integer[]> SPRITES;
    public SpriteSheet(String name, String path, int spriteWidth, int spriteHeight)
    {
        super(name);
        this.PATH = path;
        this.SPRITES = new ArrayList<Integer[]>();
        this.m_Shader = EngineResourceManager.GetShader("SPRITE");
        this.SpriteWidth = spriteWidth;
        this.SpriteHeight = spriteHeight;
        this.LoadSprites();
    }
    private void LoadSprites()
    {
        try
        {
            File file = new File(SpriteSheet.class.getResource(this.PATH).getPath());
            BufferedImage I = ImageIO.read(file);
            this.TexWidth = I.getWidth();
            this.TexHeight = I.getHeight();

            int X = 0;
            int Y = 0;

            for(int x = 0; x < this.TexWidth; x++)
            {
                for(int y = 0; y < this.TexHeight; y++)
                {
                    BufferedImage IMAGE = I.getSubimage(X, Y, SpriteWidth, SpriteHeight);
                    if(ImageIsBlank(IMAGE))
                    {
                        X += SpriteWidth;
                        if(X >= this.TexWidth - SpriteWidth)
                        {
                            X = 0;
                            Y += SpriteHeight;
                        }

                        if(Y >= this.TexHeight - SpriteHeight)
                        {
                            return;
                        }

                        continue;
                    }

                    int IMAGE_WIDTH = IMAGE.getWidth();
                    int IMAGE_HEIGHT = IMAGE.getHeight();
                    int[] pixels = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
                    IMAGE.getRGB(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, pixels, 0, IMAGE_HEIGHT);
                    ByteBuffer buffer = ByteBuffer.allocateDirect(IMAGE_WIDTH * IMAGE_HEIGHT * 4);

                    for(int hi = 0; hi < IMAGE_HEIGHT; hi++) {
                        for(int wi = 0; wi < IMAGE_WIDTH; wi++) {
                            int pixel = pixels[hi * IMAGE_WIDTH + wi];

                            buffer.put((byte) ((pixel >> 16) & 0xFF));
                            buffer.put((byte) ((pixel >> 8) & 0xFF));
                            buffer.put((byte) (pixel & 0xFF));
                            buffer.put((byte) ((pixel >> 24) & 0xFF));
                        }
                    }

                    buffer.flip();

                    int ID = glGenTextures();

                    glBindTexture(GL_TEXTURE_2D, ID);

                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, IMAGE_WIDTH, IMAGE_HEIGHT, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

                    glGenerateMipmap(GL_TEXTURE_2D);

                    Integer[] new_sprite = new Integer[]{X, Y, ID};
                    this.SPRITES.add(new_sprite);

                    glBindTexture(GL_TEXTURE_2D, 0);


                    X += SpriteWidth;
                    if(X >= this.TexWidth - SpriteWidth)
                    {
                        X = 0;
                        Y += SpriteHeight;
                    }

                    if(Y >= this.TexHeight - SpriteHeight)
                    {
                        x = this.TexWidth;
                        y = this.TexHeight;
                    }
                }
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
    public int GetTextureID(int x, int y)
    {
        for(int i = 0; i < SPRITES.size(); i++)
        {
            if(SPRITES.get(i)[0] == x && SPRITES.get(i)[1] == y)
                return SPRITES.get(i)[2];
        }

        System.err.println("NO sprite found with coordinates (" + x + "," + y + ")");
        return -1;
    }
    private boolean ImageIsBlank(BufferedImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();

        int blankPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                int RGB = image.getRGB(x, y);
                if(RGB == 0)
                    blankPixels += 1;
            }
        }

        if(blankPixels >= (width * height))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    @Override
    public void Destroy()
    {
        for(int i = 0; i < SPRITES.size(); i++)
        {
            glDeleteTextures(SPRITES.get(i)[2]);
        }
    }

}
