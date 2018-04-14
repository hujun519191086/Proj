/*    */ package org.apache.http.entity.mime.content;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class InputStreamBody extends AbstractContentBody
/*    */ {
/*    */   private final InputStream in;
/*    */   private final String filename;
/*    */ 
/*    */   public InputStreamBody(InputStream in, String mimeType, String filename)
/*    */   {
/* 46 */     super(mimeType);
/* 47 */     if (in == null) {
/* 48 */       throw new IllegalArgumentException("Input stream may not be null");
/*    */     }
/* 50 */     this.in = in;
/* 51 */     this.filename = filename;
/*    */   }
/*    */ 
/*    */   public InputStreamBody(InputStream in, String filename) {
/* 55 */     this(in, "application/octet-stream", filename);
/*    */   }
/*    */ 
/*    */   public InputStream getInputStream() {
/* 59 */     return this.in;
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public void writeTo(OutputStream out, int mode)
/*    */     throws IOException
/*    */   {
/* 67 */     writeTo(out);
/*    */   }
/*    */ 
/*    */   public void writeTo(OutputStream out) throws IOException {
/* 71 */     if (out == null)
/* 72 */       throw new IllegalArgumentException("Output stream may not be null");
/*    */     try
/*    */     {
/* 75 */       byte[] tmp = new byte[4096];
/*    */       int l;
/* 77 */       while ((l = this.in.read(tmp)) != -1) {
/* 78 */         out.write(tmp, 0, l);
/*    */       }
/* 80 */       out.flush();
/*    */     } finally {
/* 82 */       this.in.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getTransferEncoding() {
/* 87 */     return "binary";
/*    */   }
/*    */ 
/*    */   public String getCharset() {
/* 91 */     return null;
/*    */   }
/*    */ 
/*    */   public long getContentLength() {
/* 95 */     return -1L;
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 99 */     return this.filename;
/*    */   }
/*    */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.content.InputStreamBody
 * JD-Core Version:    0.6.0
 */