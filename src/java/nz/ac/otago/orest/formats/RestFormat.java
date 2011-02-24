package nz.ac.otago.orest.formats;

import java.util.Collection;
import nz.ac.otago.orest.RestRequest;
import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author mark
 */
public interface RestFormat {

   String formatResource(RestResource resource, RestRequest request);
   String formatCollection(Collection<? extends RestResource> collection, RestRequest request);
   String getContentType();
}
