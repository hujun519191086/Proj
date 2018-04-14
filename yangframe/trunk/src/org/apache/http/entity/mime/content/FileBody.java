/*     */ package org.apache.http.entity.mime.content;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class FileBody extends AbstractContentBody
/*     */ {
/*     */   private final File file;
/*     */   private final String filename;
/*     */   private final String charset;
/*     */ 
/*     */   public FileBody(File file, String filename, String mimeType, String charset)
/*     */   {
/*  55 */     super(mimeType);
/*  56 */     if (file == null) {
/*  57 */       throw new IllegalArgumentException("File may not be null");
/*     */     }
/*  59 */     this.file = file;
/*  60 */     if (filename != null)
/*  61 */       this.filename = filename;
/*     */     else
/*  63 */       this.filename = file.getName();
/*  64 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   public FileBody(File file, String mimeType, String charset)
/*     */   {
/*  73 */     this(file, null, mimeType, charset);
/*     */   }
/*     */ 
/*     */   public FileBody(File file, String mimeType) {
/*  77 */     this(file, mimeType, null);
/*     */   }
/*     */ 
/*     */   public FileBody(File file) {
/*  81 */     this(file, "application/octet-stream");
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream() throws IOException {
/*  85 */     return new FileInputStream(this.file);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void writeTo(OutputStream out, int mode)
/*     */     throws IOException
/*     */   {
/*  93 */     writeTo(out);
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream out) throws IOException {
/*  97 */     if (out == null) {
/*  98 */       throw new IllegalArgumentException("Output stream may not be null");
/*     */     }
/* 100 */     InputStream in = new FileInputStream(this.file);
/*     */     try {
/* 102 */       byte[] tmp = new byte[4096];
/*     */       int l;
/* 104 */       while ((l = in.read(tmp)) != -1) {
/* 105 */         out.write(tmp, 0, l);
/*     */       }
/* 107 */       out.flush();
/*     */     } finally {
/* 109 */       in.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTransferEncoding() {
/* 114 */     return "binary";
/*     */   }
/*     */ 
/*     */   public String getCharset() {
/* 118 */     return this.charset;
/*     */   }
/*     */ 
/*     */   public long getContentLength() {
/* 122 */     return this.file.length();
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/* 126 */     return this.filename;
/*     */   }
/*     */ 
/*     */   public File getFile() {
/* 130 */     return this.file;
/*     */   }
/*     */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.content.FileBody
 * JD-Core Version:    0.6.0
 */