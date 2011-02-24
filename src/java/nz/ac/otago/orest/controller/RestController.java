package nz.ac.otago.orest.controller;

import java.util.Collection;
import java.util.Map;
import nz.ac.otago.orest.resource.RestResource;

public interface RestController<Domain extends RestResource> {

   public Collection<Domain> getAll();

   public Domain get(String id);

   public void create(Domain objectToCreate);

   public void update(String id, Map<String,Object> tuplesToUpdate);

   public void delete(String id);

}
