/*    */ package org.apache.http.entity.mime.content;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class ByteArrayBody extends AbstractContentBody
/*    */ {
/*    */   private final byte[] data;
/*    */   private final String filename;
/*    */ 
/*    */   public ByteArrayBody(byte[] data, String mimeType, String filename)
/*    */   {
/* 60 */     super(mimeType);
/* 61 */     if (data == null) {
/* 62 */       throw new IllegalArgumentException("byte[] may not be null");
/*    */     }
/* 64 */     this.data = data;
/* 65 */     this.filename = filename;
/*    */   }
/*    */ 
/*    */   public ByteArrayBody(byte[] data, String filename)
/*    */   {
/* 75 */     this(data, "application/octet-stream", filename);
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 79 */     return this.filename;
/*    */   }
/*    */ 
/*    */   public void writeTo(OutputStream out) throws IOException {
/* 83 */     out.write(this.data);
/*    */   }
/*    */ 
/*    */   public String getCharset() {
/* 87 */     return null;
/*    */   }
/*    */ 
/*    */   public String getTransferEncoding() {
/* 91 */     return "binary";
/*    */   }
/*    */ 
/*    */   public long getContentLength() {
/* 95 */     return this.data.length;
/*    */   }
/*    */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.content.ByteArrayBody
 * JD-Core Version:    0.6.0
 */