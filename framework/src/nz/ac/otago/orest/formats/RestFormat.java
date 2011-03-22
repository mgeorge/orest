package nz.ac.otago.orest.formats;

import java.util.Collection;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author mark
 */
public interface RestFormat {

   // serialisation
   String serialiseResource(RestResource resource, RestRequest request);
   String serialiseCollection(Collection<? extends RestResource> collection, RestRequest request);

   // deserialisation
   RestResource deserialiseResource(String data, RestRequest request);
//   Collection<? extends RestResource> deserialiseCollection(String data, RestRequest request);

   String getContentType();
}
