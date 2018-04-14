/*    */ package org.apache.http.entity.mime;
/*    */ 
/*    */ public class MinimalField
/*    */ {
/*    */   private final String name;
/*    */   private final String value;
/*    */ 
/*    */   MinimalField(String name, String value)
/*    */   {
/* 42 */     this.name = name;
/* 43 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 47 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getBody() {
/* 51 */     return this.value;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 56 */     StringBuilder buffer = new StringBuilder();
/* 57 */     buffer.append(this.name);
/* 58 */     buffer.append(": ");
/* 59 */     buffer.append(this.value);
/* 60 */     return buffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\jieranyishen\Desktop\httpmime-4.1.2.jar
 * Qualified Name:     org.apache.http.entity.mime.MinimalField
 * JD-Core Version:    0.6.0
 */