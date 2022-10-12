package org.gavaghan.commons.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

/**
 * I/O utility classes.
 *
 * @author <a href="mailto:mike@gavaghan.org">Mike Gavaghan</a>
 * @since 1.0
 */
public class IOUtils
{
    /**
     * Thread local utility byte buffer.
     */
    private static final ThreadLocal<byte[]> sByteBuffer = new ThreadLocal<>();

    /**
     * Thread local utility char buffer.
     */
    private static final ThreadLocal<char[]> sCharBuffer = new ThreadLocal<>();

    /**
     * Get the thread local utility byte buffer.
     *
     * @return local thread safe, GC friendly buffer
     */
    static private byte[] getByteBuffer()
    {
        byte[] buffer = sByteBuffer.get();

        if (buffer == null)
        {
            buffer = new byte[4096];
            sByteBuffer.set(buffer);
        }

        return buffer;
    }

    /**
     * Get the thread local utility char buffer.
     *
     * @return local thread safe, GC friendly buffer
     */
    static private char[] getCharBuffer()
    {
        char[] buffer = sCharBuffer.get();

        if (buffer == null)
        {
            buffer = new char[4096];
            sCharBuffer.set(buffer);
        }

        return buffer;
    }

    /**
     * Disallow instantiation.
     */
    private IOUtils()
    {
    }

    /**
     * Null-safe, exception-safe close of a I/O source or destination.
     *
     * @param io object to close
     */
    static public void close(@Nullable Closeable io)
    {
        if (io == null) return;

        try
        {
            // output objects also implement Flushable
            if (io instanceof Flushable flushable) flushable.flush();
        }
        catch(IOException ignored)
        {
        }

        try
        {
            io.close();
        }
        catch(IOException ignored)
        {
        }
    }

    /**
     * Fully read the content of a {@code InputStream}.  Don't stop until the
     * end of the stream.
     *
     * @param inputStream {@code InputStream} to read from
     * @return the byte array that was read
     * @throws IOException if an I/O error occurs
     */
    static public byte[] readFully(@NotNull InputStream inputStream) throws IOException
    {
        byte[] byteArray;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream())
        {
            byte[] buffer = getByteBuffer();

            for (; ; )
            {
                int got = inputStream.read(buffer);
                if (got < 0) break;

                baos.write(buffer, 0, got);
            }

            byteArray = baos.toByteArray();
        }

        return byteArray;
    }

    /**
     * Fully read the content of a {@code Reader}. Don't stop until the end of
     * the reader.
     *
     * @param reader {@code Reader} stream to read from
     * @return the char array that was read
     * @throws IOException if an I/O error occurs
     */
    static public char[] readFully(@NotNull Reader reader) throws IOException
    {
        char[] charArray;

        try (CharArrayWriter caw = new CharArrayWriter())
        {
            char[] buffer = getCharBuffer();

            for (; ; )
            {
                int got = reader.read(buffer);
                if (got < 0) break;

                caw.write(buffer, 0, got);
            }

            charArray = caw.toCharArray();
        }

        return charArray;
    }
}
