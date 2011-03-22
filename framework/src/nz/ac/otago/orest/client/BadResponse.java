package nz.ac.otago.orest.client;

/**
 *
 * @author mark
 */
public class BadResponse extends RuntimeException {

   private RestResponse response;

   public BadResponse(RestResponse response) {
      this.response = response;
   }

   public RestResponse getResponse() {
      return response;
   }

   @Override
   public String getMessage() {
      return "HTTP Status: " + response.getStatus() + ". " + response.getMessage() + "\n" + response.getBody();
   }

}
