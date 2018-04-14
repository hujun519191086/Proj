/*    */ package org.apache.http.entity.mime.content;
/*    */ 
/*    */ public abstract class AbstractContentBody
/*    */   implements ContentBody
/*    */ {
/*    */   private final String mimeType;
/*    */   private final String mediaType;
/*    */   private final String subType;
/*    */ 
/*    */   public AbstractContentBody(String mimeType)
/*    */   {
/* 42 */     if (mimeType == null) {
/* 43 */       throw new IllegalArgumentException("MIME type may not be null");
/*    */     }
/* 45 */     this.mimeType = mimeType;
/* 46 */     int i = mimeType.indexOf('/');
/* 47 */     if (i != -1) {
/* 48 */       this.mediaType = mimeType.substring(0, i);
/* 49 */       this.subType = mimeType.substring(i + 1);
/*    */     } else {
/* 51 */       this.mediaType = mimeType;
/* 52 */       this.subType = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getMimeType() {
/* 57 */     return this.mimeType;
/*    */   }
/*    */ 
/*    */   public String getMediaType() {
/* 61 */     return this.mediaType;
/*    */   }
/*    */ 
/*    */   public String getSubType() {
/* 65 */     return this.subType;
/*    */   }
/*    */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.content.AbstractContentBody
 * JD-Core Version:    0.6.0
 */