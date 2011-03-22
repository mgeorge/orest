package nz.ac.otago.orest;

/**
 *
 * @author mark
 */
public class ORestException extends RuntimeException {

   private Integer httpStatus;

   public ORestException(String message, Integer httpStatus) {
      super(message);
      this.httpStatus = httpStatus;
   }

   public ORestException(String message,  Throwable throwable) {
      super(message, throwable);
      this.httpStatus = 500;
   }

   public Integer getHttpStatus() {
      return httpStatus;
   }

}
