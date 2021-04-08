package org.ifcparaclete.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.layout.ByteBufferDestinationHelper;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Builder;
import org.apache.logging.log4j.status.StatusLogger;

import org.ifcparaclete.IFCStatics;

public class IFCOutputStream extends OutputStream {

    private static StatusLogger LOGGER = StatusLogger.getLogger();
    private static File ifcFile;
    private static OutputStream ifcFileOutputStream;
    private static PatternLayout ifcPatternLayout;
    private ByteBuffer byteBuffer;
    private int        bufferSize = 8096;


    public IFCOutputStream(String ifcFileNameArg, boolean isAppendArg,
                           PatternLayout ifcPatternLayoutArg) throws IOException {

        initIFCOutputStream(ifcFileNameArg, isAppendArg, ifcPatternLayoutArg);

    }

    private void initIFCOutputStream(String ifcFileNameArg, boolean isAppendArg, PatternLayout ifcPatternLayoutArg) {
        LOGGER.debug("Now writing to {} at {}", ifcFileNameArg, new Date());

        ifcPatternLayout = ifcPatternLayoutArg;
        ifcFile = new File(ifcFileNameArg);
        byteBuffer = ByteBuffer.wrap(new byte[bufferSize]);

        try {
            ifcFileOutputStream = new FileOutputStream(ifcFile, isAppendArg);
        } catch (FileNotFoundException e) {
        }

        if (ifcFile.exists() && ifcFile.length() == 0) {
            try {
                FileTime now = FileTime.fromMillis(System.currentTimeMillis());
                Files.setAttribute(ifcFile.toPath(), "creationTime", now);
            } catch (Exception ex) {
                LOGGER.warn("Unable to set current file time for {}", ifcFileNameArg);
            }
            writeHeader();
        }
    }

    protected void writeHeader() {

        final byte[] header = ifcPatternLayout.getHeader();
        if (header != null) {
            try {
                ifcFileOutputStream.write(header, 0, header.length);
            } catch (final IOException e) {
                LOGGER.error("Unable to write header", e);
            }
        }

    }

    @Override
    public void write(int b) throws IOException {
        // TODO Implement this method
    }

    /**
     * Flushes any buffers.
     */
    public synchronized void flush() {
        flushBuffer(byteBuffer);
        flushDestination();
    }

    protected synchronized boolean closeOutputStream() {
        flush();

        try {
            
            ifcFileOutputStream.close();
            LOGGER.debug("OutputStream closed");
        } catch (final IOException ex) {
            LOGGER.error("Unable to close stream", ex);
            return false;
        }
        return true;
    }

    /**
     * Default hook to write footer during close.
     */

    public boolean releaseSub(final long timeout, final TimeUnit timeUnit) {
        writeFooter();
        return closeOutputStream();
    }


    /**
     * Writes the footer.
     */
    protected void writeFooter() {

        final byte[] footer = ifcPatternLayout.getFooter();
        if (footer != null) {
            write(footer);
        }
    }

    /**
     * Some output streams synchronize writes while others do not.
     * @param bytes The serialized Log event.
     * @throws AppenderLoggingException if an error occurs.
     */
    public void write(final byte[] bytes) {
        write(bytes, 0, bytes.length, false);
    }

    /**
     * Some output streams synchronize writes while others do not.
     * @param bytes The serialized Log event.
     * @param immediateFlush If true, flushes after writing.
     * @throws AppenderLoggingException if an error occurs.
     */
    protected void write(final byte[] bytes, final boolean immediateFlush) {
        write(bytes, 0, bytes.length, immediateFlush);
    }


    public void writeBytes(final byte[] data, final int offset, final int length) {
        write(data, offset, length, false);
    }

    /**
     * Some output streams synchronize writes while others do not. Synchronizing here insures that
     * log events won't be intertwined.
     * @param bytes The serialized Log event.
     * @param offset The offset into the byte array.
     * @param length The number of bytes to write.
     * @throws AppenderLoggingException if an error occurs.
     */
    public void write(final byte[] bytes, final int offset, final int length) {
        writeBytes(bytes, offset, length);
    }

    /**
     * Some output streams synchronize writes while others do not. Synchronizing here insures that
     * log events won't be intertwined.
     * @param bytes The serialized Log event.
     * @param offset The offset into the byte array.
     * @param length The number of bytes to write.
     * @param immediateFlush flushes immediately after writing.
     * @throws AppenderLoggingException if an error occurs.
     */
    protected synchronized void write(final byte[] bytes, final int offset, final int length,
                                      final boolean immediateFlush) {
        if (immediateFlush && byteBuffer.position() == 0) {
            writeToDestination(bytes, offset, length);
            flushDestination();
            return;
        }
        if (length >= byteBuffer.capacity()) {
            // if request length exceeds buffer capacity, flush the buffer and write the data directly
            flush();
            writeToDestination(bytes, offset, length);
        } else {
            if (length > byteBuffer.remaining()) {
                flush();
            }
            byteBuffer.put(bytes, offset, length);
        }
        if (immediateFlush) {
            flush();
        }
    }

    /**
     * Writes the specified section of the specified byte array to the stream.
     *
     * @param bytes the array containing data
     * @param offset from where to write
     * @param length how many bytes to write
     * @since 2.6
     */
    protected synchronized void writeToDestination(final byte[] bytes, final int offset, final int length) {
        try {
            ifcFileOutputStream.write(bytes, offset, length);
        } catch (final IOException ex) {
            throw new AppenderLoggingException("Error writing to stream " + ifcFileOutputStream.toString(), ex);
        }
    }

    /**
     * Calls {@code flush()} on the underlying output stream.
     * @since 2.6
     */
    protected synchronized void flushDestination() {

        if (ifcFileOutputStream != null) {
            try {
                ifcFileOutputStream.flush();
            } catch (final IOException ex) {
                throw new AppenderLoggingException("Error flushing stream " + ifcFileOutputStream.toString(), ex);
            }
        }
    }

    /**
     * Drains the ByteBufferDestination's buffer into the destination. By default this calls
     * {@link OutputStreamManager#write(byte[], int, int, boolean)} with the buffer contents.
     * The underlying stream is not {@linkplain OutputStream#flush() flushed}.
     *
     * @see #flushDestination()
     * @since 2.6
     */
    protected synchronized void flushBuffer(final ByteBuffer buf) {
        ((Buffer) buf).flip();
        try {
            if (buf.remaining() > 0) {
                writeToDestination(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
            }
        } finally {
            buf.clear();
        }
    }


    protected synchronized boolean closeIFCOutputStream() {
        flush();
        try {
            ifcFileOutputStream.close();
            LOGGER.debug("OutputStream closed");
        } catch (final IOException ex) {
            LOGGER.error("Unable to close stream", ex);
            return false;
        }
        return true;
    }

    /**
     * Returns this {@code ByteBufferDestination}'s buffer.
     * @return the buffer
     * @since 2.6
     */

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    /**
     * Drains the ByteBufferDestination's buffer into the destination. By default this calls
     * {@link #flushBuffer(ByteBuffer)} with the specified buffer. Subclasses may override.
     * <p>
     * Do not call this method lightly! For some subclasses this is a very expensive operation. For example,
     * {@link MemoryMappedFileManager} will assume this method was called because the end of the mapped region
     * was reached during a text encoding operation and will {@linkplain MemoryMappedFileManager#remap() remap} its
     * buffer.
     * </p><p>
     * To just flush the buffered contents to the underlying stream, call
     * {@link #flushBuffer(ByteBuffer)} directly instead.
     * </p>
     *
     * @param buf the buffer whose contents to write the destination
     * @return the specified buffer
     * @since 2.6
     */

    public ByteBuffer drain(final ByteBuffer buf) {
        flushBuffer(buf);
        return buf;
    }


    public void writeBytes(final ByteBuffer data) {
        if (data.remaining() == 0) {
            return;
        }
        synchronized (this) {
            ByteBufferDestinationHelper.writeToUnsynchronized(data, (ByteBufferDestination) this);
        }
    }

    public Appendable append(CharSequence csq) throws IOException {
        // TODO Implement this method
        return null;
    }


    public Appendable append(CharSequence csq, int start, int end) throws IOException {
        // TODO Implement this method
        return null;
    }


    public Appendable append(char c) throws IOException {
        // TODO Implement this method
        return null;
    }

    public static void main(String[] args) {
        PatternLayout ifcLayout;

        IFCOutputStream ifcOutputStream;
        Builder ifcBuilder;

        ifcBuilder = PatternLayout.newBuilder()
                                  .withPattern(IFCStatics.IFC_DEFAULT_LOG_LAYOUT)
                                  .withHeader("HEADER: Testing IFCOutputStream")
                                  .withFooter("FOOTER: Testing IFCOutputStream");
        ifcLayout = (PatternLayout) ifcBuilder.build();
        try {
            ifcOutputStream = new IFCOutputStream(IFCStatics.IFC_DEFAULT_LOG_FILE, true, ifcLayout);
        } catch (IOException e) {
        }

    }
}
