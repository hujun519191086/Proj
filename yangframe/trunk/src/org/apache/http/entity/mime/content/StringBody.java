/*     */package org.apache.http.entity.mime.content;

/*     */
/*     */import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */public class StringBody extends AbstractContentBody
/*     */
{
    /*     */private final byte[] content;
    /*     */private final Charset charset;

    /*     */
    /*     */public static StringBody create(String text, String mimeType, Charset charset)
    /*     */throws IllegalArgumentException
    /*     */
    {
        /*     */try
        /*     */{
            /* 58 */return new StringBody(text, mimeType, charset);
        }
        catch (UnsupportedEncodingException ex)
        {
            /* 60 */throw new IllegalArgumentException("Charset " + charset + " is not supported", ex);
            /*     */}
    }

    /*     */
    /*     */public static StringBody create(String text, Charset charset)
    /*     */throws IllegalArgumentException
    /*     */
    {
        /* 69 */return create(text, null, charset);
        /*     */}

    /*     */
    /*     */public static StringBody create(String text)
    /*     */throws IllegalArgumentException
    /*     */
    {
        /* 76 */return create(text, null, null);
        /*     */}

    /*     */
    /*     */public StringBody(String text, String mimeType, Charset charset)
    /*     */throws UnsupportedEncodingException
    /*     */
    {
        /* 92 */super(mimeType);
        /* 93 */if (text == null)
        {
            /* 94 */throw new IllegalArgumentException("Text may not be null");
            /*     */}
        /* 96 */if (charset == null)
        {
            /* 97 */charset = Charset.forName("US-ASCII");
            /*     */}
        /* 99 */this.content = text.getBytes(charset.name());
        /* 100 */this.charset = charset;
        /*     */}

    /*     */
    /*     */public StringBody(String text, Charset charset)
    /*     */throws UnsupportedEncodingException
    /*     */
    {
        /* 113 */this(text, "text/plain", charset);
        /*     */}

    /*     */
    /*     */public StringBody(String text)
    /*     */throws UnsupportedEncodingException
    /*     */
    {
        /* 126 */this(text, "text/plain", null);
        /*     */}

    /*     */
    /*     */public Reader getReader()
    {
        /* 130 */return new InputStreamReader(new ByteArrayInputStream(this.content), this.charset);
        /*     */}

    /*     */
    /*     */@Deprecated
    /*     */public void writeTo(OutputStream out, int mode)
    /*     */throws IOException
    /*     */
    {
        /* 140 */writeTo(out);
        /*     */}

    /*     */
    /*     */public void writeTo(OutputStream out) throws IOException
    {
        /* 144 */if (out == null)
        {
            /* 145 */throw new IllegalArgumentException("Output stream may not be null");
            /*     */}
        /* 147 */InputStream in = new ByteArrayInputStream(this.content);
        /* 148 */byte[] tmp = new byte[4096];
        /*     */int l;
        /* 150 */while ((l = in.read(tmp)) != -1)
        {
            /* 151 */out.write(tmp, 0, l);
            /*     */}
        /* 153 */out.flush();
        /*     */}

    /*     */
    /*     */public String getTransferEncoding()
    {
        /* 157 */return "8bit";
        /*     */}

    /*     */
    /*     */public String getCharset()
    {
        /* 161 */return this.charset.name();
        /*     */}

    /*     */
    /*     */public long getContentLength()
    {
        /* 165 */return this.content.length;
        /*     */}

    /*     */
    /*     */public String getFilename()
    {
        /* 169 */return null;
        /*     */}
    /*     */
}

/*
 * Location: C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar Qualified Name: org.apache.http.entity.mime.content.StringBody JD-Core Version: 0.6.0
 */
