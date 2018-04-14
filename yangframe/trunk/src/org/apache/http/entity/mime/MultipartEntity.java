/*     */ package org.apache.http.entity.mime;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Random;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.entity.mime.content.ContentBody;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ 
/*     */ public class MultipartEntity
/*     */   implements HttpEntity
/*     */ {
/*  52 */   private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
/*     */   private final HttpMultipart multipart;
/*     */   private final Header contentType;
/*     */   private long length;
/*     */   private volatile boolean dirty;
/*     */ 
/*     */   public MultipartEntity(HttpMultipartMode mode, String boundary, Charset charset)
/*     */   {
/*  74 */     if (boundary == null) {
/*  75 */       boundary = generateBoundary();
/*     */     }
/*  77 */     if (mode == null) {
/*  78 */       mode = HttpMultipartMode.STRICT;
/*     */     }
/*  80 */     this.multipart = new HttpMultipart("form-data", charset, boundary, mode);
/*  81 */     this.contentType = new BasicHeader("Content-Type", generateContentType(boundary, charset));
/*     */ 
/*  84 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   public MultipartEntity(HttpMultipartMode mode)
/*     */   {
/*  93 */     this(mode, null, null);
/*     */   }
/*     */ 
/*     */   public MultipartEntity()
/*     */   {
/* 100 */     this(HttpMultipartMode.STRICT, null, null);
/*     */   }
/*     */ 
/*     */   protected String generateContentType(String boundary, Charset charset)
/*     */   {
/* 106 */     StringBuilder buffer = new StringBuilder();
/* 107 */     buffer.append("multipart/form-data; boundary=");
/* 108 */     buffer.append(boundary);
/* 109 */     if (charset != null) {
/* 110 */       buffer.append("; charset=");
/* 111 */       buffer.append(charset.name());
/*     */     }
/* 113 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   protected String generateBoundary() {
/* 117 */     StringBuilder buffer = new StringBuilder();
/* 118 */     Random rand = new Random();
/* 119 */     int count = rand.nextInt(11) + 30;
/* 120 */     for (int i = 0; i < count; i++) {
/* 121 */       buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
/*     */     }
/* 123 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public void addPart(FormBodyPart bodyPart) {
/* 127 */     this.multipart.addBodyPart(bodyPart);
/* 128 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   public void addPart(String name, ContentBody contentBody) {
/* 132 */     addPart(new FormBodyPart(name, contentBody));
/*     */   }
/*     */ 
/*     */   public boolean isRepeatable() {
/* 136 */     for (FormBodyPart part : this.multipart.getBodyParts()) {
/* 137 */       ContentBody body = part.getBody();
/* 138 */       if (body.getContentLength() < 0L) {
/* 139 */         return false;
/*     */       }
/*     */     }
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isChunked() {
/* 146 */     return !isRepeatable();
/*     */   }
/*     */ 
/*     */   public boolean isStreaming() {
/* 150 */     return !isRepeatable();
/*     */   }
/*     */ 
/*     */   public long getContentLength() {
/* 154 */     if (this.dirty) {
/* 155 */       this.length = this.multipart.getTotalLength();
/* 156 */       this.dirty = false;
/*     */     }
/* 158 */     return this.length;
/*     */   }
/*     */ 
/*     */   public Header getContentType() {
/* 162 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   public Header getContentEncoding() {
/* 166 */     return null;
/*     */   }
/*     */ 
/*     */   public void consumeContent() throws IOException, UnsupportedOperationException
/*     */   {
/* 171 */     if (isStreaming())
/* 172 */       throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
/*     */   }
/*     */ 
/*     */   public InputStream getContent()
/*     */     throws IOException, UnsupportedOperationException
/*     */   {
/* 178 */     throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream outstream) throws IOException
/*     */   {
/* 183 */     this.multipart.writeTo(outstream);
/*     */   }
/*     */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.MultipartEntity
 * JD-Core Version:    0.6.0
 */