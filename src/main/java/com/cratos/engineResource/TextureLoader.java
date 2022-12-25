package com.cratos.engineResource;


import com.cratos.Cratos;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.memSlice;

public class TextureLoader
{
    /*public static int LoadTexture(String path)
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

            System.out.println("Image width: " + w.get(0));
            System.out.println("Image height: " + h.get(0));
            System.out.println("Image components: " + comp.get(0));
            System.out.println("Image HDR: " + stbi_is_hdr_from_memory(imageBuffer));

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

    */


    /*public static int LoadTexture(String path)
    {
        BufferedImage tex = null;
        InputStream is = null;

        try {
            is = TextureLoader.class.getResourceAsStream(path);
            //Cratos.CratosDebug.Log(TextureLoader.class.getResource(path).getPath());
            System.out.println("is=" + is + "\n\n");
	        tex = (BufferedImage) ImageIO.read(is);
            if (tex == null)
            {
                throw new Exception("Error: Got null from ImageIO.read()");
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        IntBuffer buf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();

        // Extract The Image
        /*try
        {
            tex = ImageIO.read ( is );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

            // Flip Image
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
            //	 tx.translate(0, -image.getHeight(null));
        AffineTransformOp op =
                new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        tex = op.filter(tex, null); //ERROR HERE!

            // Put Image In Memory
        ByteBuffer scratch = ByteBuffer.allocateDirect(4 * tex.getWidth() * tex.getHeight());

	    byte data[] =
            (byte[]) tex.getRaster().getDataElements(
                    0,
                    0,
                    tex.getWidth(),
                    tex.getHeight(),
                    null);
	    scratch.clear();
	    scratch.put(data);
	    scratch.rewind();

	    glGenTextures(buf); // Create Texture In OpenGL

        glBindTexture(GL_TEXTURE_2D, buf.get(0));
	// Typical Texture Generation Using Data From The Image

        // Linear Filtering
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

	    // Generate The Texture
        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGB,
                tex.getWidth(),
                tex.getHeight(),
                0,
                GL_RGB,
                GL_UNSIGNED_BYTE,
                scratch);

	    return buf.get(0); // Return Image Address In Memory
    }

    */

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
