package nz.ac.otago.orest.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author mark
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

   public String path();

}
