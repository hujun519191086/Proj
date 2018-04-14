/*     */ package org.apache.http.entity.mime;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.util.ByteArrayBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpMultipart
/*     */ {
/*  92 */   private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
/*  93 */   private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
/*  94 */   private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");
/*     */   private final String subType;
/*     */   private final Charset charset;
/*     */   private final String boundary;
/*     */   private final List<FormBodyPart> parts;
/*     */   private final HttpMultipartMode mode;
/*     */ 
/*     */   private static ByteArrayBuffer encode(Charset charset, String string)
/*     */   {
/*  53 */     ByteBuffer encoded = charset.encode(CharBuffer.wrap(string));
/*  54 */     ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
/*  55 */     bab.append(encoded.array(), encoded.position(), encoded.remaining());
/*  56 */     return bab;
/*     */   }
/*     */ 
/*     */   private static void writeBytes(ByteArrayBuffer b, OutputStream out) throws IOException
/*     */   {
/*  61 */     out.write(b.buffer(), 0, b.length());
/*     */   }
/*     */ 
/*     */   private static void writeBytes(String s, Charset charset, OutputStream out) throws IOException
/*     */   {
/*  66 */     ByteArrayBuffer b = encode(charset, s);
/*  67 */     writeBytes(b, out);
/*     */   }
/*     */ 
/*     */   private static void writeBytes(String s, OutputStream out) throws IOException
/*     */   {
/*  72 */     ByteArrayBuffer b = encode(MIME.DEFAULT_CHARSET, s);
/*  73 */     writeBytes(b, out);
/*     */   }
/*     */ 
/*     */   private static void writeField(MinimalField field, OutputStream out) throws IOException
/*     */   {
/*  78 */     writeBytes(field.getName(), out);
/*  79 */     writeBytes(FIELD_SEP, out);
/*  80 */     writeBytes(field.getBody(), out);
/*  81 */     writeBytes(CR_LF, out);
/*     */   }
/*     */ 
/*     */   private static void writeField(MinimalField field, Charset charset, OutputStream out) throws IOException
/*     */   {
/*  86 */     writeBytes(field.getName(), charset, out);
/*  87 */     writeBytes(FIELD_SEP, out);
/*  88 */     writeBytes(field.getBody(), charset, out);
/*  89 */     writeBytes(CR_LF, out);
/*     */   }
/*     */ 
/*     */   public HttpMultipart(String subType, Charset charset, String boundary, HttpMultipartMode mode)
/*     */   {
/* 115 */     if (subType == null) {
/* 116 */       throw new IllegalArgumentException("Multipart subtype may not be null");
/*     */     }
/* 118 */     if (boundary == null) {
/* 119 */       throw new IllegalArgumentException("Multipart boundary may not be null");
/*     */     }
/* 121 */     this.subType = subType;
/* 122 */     this.charset = (charset != null ? charset : MIME.DEFAULT_CHARSET);
/* 123 */     this.boundary = boundary;
        /* 124 */this.parts = new ArrayList<FormBodyPart>();
/* 125 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */   public HttpMultipart(String subType, Charset charset, String boundary)
/*     */   {
/* 138 */     this(subType, charset, boundary, HttpMultipartMode.STRICT);
/*     */   }
/*     */ 
/*     */   public HttpMultipart(String subType, String boundary) {
/* 142 */     this(subType, null, boundary);
/*     */   }
/*     */ 
/*     */   public String getSubType() {
/* 146 */     return this.subType;
/*     */   }
/*     */ 
/*     */   public Charset getCharset() {
/* 150 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public HttpMultipartMode getMode() {
/* 154 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public List<FormBodyPart> getBodyParts() {
/* 158 */     return this.parts;
/*     */   }
/*     */ 
/*     */   public void addBodyPart(FormBodyPart part) {
/* 162 */     if (part == null) {
/* 163 */       return;
/*     */     }
/* 165 */     this.parts.add(part);
/*     */   }
/*     */ 
/*     */   public String getBoundary() {
/* 169 */     return this.boundary;
/*     */   }
/*     */ 
/*     */   private void doWriteTo(HttpMultipartMode mode, OutputStream out, boolean writeContent)
/*     */     throws IOException
/*     */   {
/* 177 */     ByteArrayBuffer boundary = encode(this.charset, getBoundary());
/* 178 */     for (FormBodyPart part : this.parts) {
/* 179 */       writeBytes(TWO_DASHES, out);
/* 180 */       writeBytes(boundary, out);
/* 181 */       writeBytes(CR_LF, out);
/*     */ 
/* 183 */       Header header = part.getHeader();
/*     */ 

            /* 185 */switch (mode)
            {
            /*     */case STRICT:
/* 187 */         for (MinimalField field : header) {
/* 188 */           writeField(field, out);
/*     */         }
/* 190 */         break;
                /*     */case BROWSER_COMPATIBLE:
/* 194 */         MinimalField cd = part.getHeader().getField("Content-Disposition");
/* 195 */         writeField(cd, this.charset, out);
/* 196 */         String filename = part.getBody().getFilename();
/* 197 */         if (filename == null) break;
/* 198 */         MinimalField ct = part.getHeader().getField("Content-Type");
/* 199 */         writeField(ct, this.charset, out);
/*     */       }
/*     */ 
/* 203 */       writeBytes(CR_LF, out);
/*     */ 
/* 205 */       if (writeContent) {
/* 206 */         part.getBody().writeTo(out);
/*     */       }
/* 208 */       writeBytes(CR_LF, out);
/*     */     }
/* 210 */     writeBytes(TWO_DASHES, out);
/* 211 */     writeBytes(boundary, out);
/* 212 */     writeBytes(TWO_DASHES, out);
/* 213 */     writeBytes(CR_LF, out);
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream out)
/*     */     throws IOException
/*     */   {
/* 224 */     doWriteTo(this.mode, out, true);
/*     */   }
/*     */ 
/*     */   public long getTotalLength()
/*     */   {
/* 241 */     long contentLen = 0L;
/* 242 */     for (FormBodyPart part : this.parts) {
/* 243 */       ContentBody body = part.getBody();
/* 244 */       long len = body.getContentLength();
/* 245 */       if (len >= 0L)
/* 246 */         contentLen += len;
/*     */       else {
/* 248 */         return -1L;
/*     */       }
/*     */     }
/* 251 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/*     */     try {
/* 253 */       doWriteTo(this.mode, out, false);
/* 254 */       byte[] extra = out.toByteArray();
/* 255 */       return contentLen + extra.length;
/*     */     } catch (IOException ex) {
/*     */     }
/* 258 */     return -1L;
/*     */   }
/*     */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.HttpMultipart
 * JD-Core Version:    0.6.0
 */