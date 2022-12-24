package com.cratos.engineResource;


import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memSlice;

public class TextureLoader
{
    public static int LoadTexture(String path)
    {
        int ID = -1;
        ByteBuffer image;
        ByteBuffer imageBuffer;
        try {
            imageBuffer = ioResourceToByteBuffer(path, 8 * 1024);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer w    = stack.mallocInt(1);
            IntBuffer h    = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            if (!stbi_info_from_memory(imageBuffer, w, h, comp)) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            } else {
                System.out.println("OK with reason: " + stbi_failure_reason());
            }

            /*System.out.println("Image width: " + w.get(0));
            System.out.println("Image height: " + h.get(0));
            System.out.println("Image components: " + comp.get(0));
            System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));*/

            image = stbi_load_from_memory(imageBuffer, w, h, comp, 0);
            if (image == null) {
                throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
            }

            int width = w.get(0);
            int height = h.get(0);

            ID = glGenTextures();

            TextureLoader.UseTexture(ID);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexImage2D(GL_TEXTURE_2D, 0, (comp.get(0) == 4 ? GL_RGBA : GL_RGB), width, height, 0, (comp.get(0) == 4 ? GL_RGBA : GL_RGB), GL_UNSIGNED_BYTE, image);

            glGenerateMipmap(GL_TEXTURE_2D);
            TextureLoader.UnbindEveryTexture();
        }

        return ID;
    }

    private static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException
    {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int)fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = new BufferedInputStream(new FileInputStream(resource));//Texture.class.getClass().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return memSlice(buffer);
    }
    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)
    {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
    public static void UseTexture(int tex) { glBindTexture(GL_TEXTURE_2D, tex); }
    public static void UnbindEveryTexture() { glBindTexture(GL_TEXTURE_2D, 0); }
    public static void DestroyTexture(int tex) { glDeleteTextures(tex); }
}
