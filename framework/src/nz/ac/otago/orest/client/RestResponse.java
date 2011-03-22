package nz.ac.otago.orest.client;

/**
 *
 * @author mark
 */
public class RestResponse {

   private Integer status;
   private String message;
   private String contentType;
   private String body;

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer code) {
      this.status = code;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getContentType() {
      return contentType;
   }

   public void setContentType(String contentType) {
      this.contentType = contentType;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }
   
}
