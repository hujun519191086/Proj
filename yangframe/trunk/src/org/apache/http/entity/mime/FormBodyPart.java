/*     */ package org.apache.http.entity.mime;
/*     */ 
/*     */ import org.apache.http.entity.mime.content.ContentBody;
/*     */ 
/*     */ public class FormBodyPart
/*     */ {
/*     */   private final String name;
/*     */   private final Header header;
/*     */   private final ContentBody body;
/*     */ 
/*     */   public FormBodyPart(String name, ContentBody body)
/*     */   {
/*  48 */     if (name == null) {
/*  49 */       throw new IllegalArgumentException("Name may not be null");
/*     */     }
/*  51 */     if (body == null) {
/*  52 */       throw new IllegalArgumentException("Body may not be null");
/*     */     }
/*  54 */     this.name = name;
/*  55 */     this.body = body;
/*  56 */     this.header = new Header();
/*     */ 
/*  58 */     generateContentDisp(body);
/*  59 */     generateContentType(body);
/*  60 */     generateTransferEncoding(body);
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  64 */     return this.name;
/*     */   }
/*     */ 
/*     */   public ContentBody getBody() {
/*  68 */     return this.body;
/*     */   }
/*     */ 
/*     */   public Header getHeader() {
/*  72 */     return this.header;
/*     */   }
/*     */ 
/*     */   public void addField(String name, String value) {
/*  76 */     if (name == null) {
/*  77 */       throw new IllegalArgumentException("Field name may not be null");
/*     */     }
/*  79 */     this.header.addField(new MinimalField(name, value));
/*     */   }
/*     */ 
/*     */   protected void generateContentDisp(ContentBody body) {
/*  83 */     StringBuilder buffer = new StringBuilder();
/*  84 */     buffer.append("form-data; name=\"");
/*  85 */     buffer.append(getName());
/*  86 */     buffer.append("\"");
/*  87 */     if (body.getFilename() != null) {
/*  88 */       buffer.append("; filename=\"");
/*  89 */       buffer.append(body.getFilename());
/*  90 */       buffer.append("\"");
/*     */     }
/*  92 */     addField("Content-Disposition", buffer.toString());
/*     */   }
/*     */ 
/*     */   protected void generateContentType(ContentBody body) {
/*  96 */     StringBuilder buffer = new StringBuilder();
/*  97 */     buffer.append(body.getMimeType());
/*  98 */     if (body.getCharset() != null) {
/*  99 */       buffer.append("; charset=");
/* 100 */       buffer.append(body.getCharset());
/*     */     }
/* 102 */     addField("Content-Type", buffer.toString());
/*     */   }
/*     */ 
/*     */   protected void generateTransferEncoding(ContentBody body) {
/* 106 */     addField("Content-Transfer-Encoding", body.getTransferEncoding());
/*     */   }
/*     */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.FormBodyPart
 * JD-Core Version:    0.6.0
 */