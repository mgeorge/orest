package nz.ac.otago.orest.NOLONGERUSED;

import java.lang.reflect.Field;

/**
 * A utility class for getting/setting object property values.
 * 
 * <b>NOTE</b>: Currently directly accesses the fields rather than using
 * getter/setter methods, so not really using "properties" in the JavaBean
 * sense.
 * 
 * Will work its way up the inheritance tree if it can't find the property in
 * the given instance
 * 
 * @author Mark George
 */
public class PropertyUtils {

	/**
	 * Sets the given property to the given value in the given object.
	 * 
	 * Will perform string to number conversion if required.
	 * 
	 * @param target
	 *            The target object to set the property in.
	 * @param property
	 *            The property to set.
	 * @param value
	 *            The value to set the property to.
	 */
	public static void setProperty(Object target, String property, Object value) {
		try {
			// find the field in the target object that matches the property
			// name
			
			Field field = findField(target.getClass(), property);
			
			if(field == null) {
				throw new RuntimeException("No property matching " + property + " was found");
			}
			
			
			// get initial field accessibility so we can reset it when we are
			// finished
			boolean origAccessibility = field.isAccessible();

			// since it is probably private, make it accessible
			field.setAccessible(true);

			Class<?> type = field.getType();

			// local version of value to avoid assigning to parameter
			Object newValue = value;

			// if value is a string and property is a numeric field
			// then perform the necessary type conversion on value
			if(newValue instanceof String) {
				if (type.isAssignableFrom(Double.class)
						|| type.isAssignableFrom(double.class)) {
					newValue = new Double(value.toString());
				} else if (type.isAssignableFrom(Float.class)
						|| type.isAssignableFrom(float.class)) {
					newValue = new Float(value.toString());
				} else if (type.isAssignableFrom(Long.class)
						|| type.isAssignableFrom(long.class)) {
					newValue = new Long(value.toString());
				} else if (type.isAssignableFrom(Integer.class)
						|| type.isAssignableFrom(int.class)) {
					newValue = new Integer(value.toString());
				} else if (type.isAssignableFrom(Short.class)
						|| type.isAssignableFrom(short.class)) {
					newValue = new Short(value.toString());
				} else if (type.isAssignableFrom(Byte.class)
						|| type.isAssignableFrom(byte.class)) {
					newValue = new Byte(value.toString());
				}
			}

			// set the value of the field to the new value
			field.set(target, newValue);

			// set field accessibility back to whatever it was to start with
			field.setAccessible(origAccessibility);

		// wrap and re-throw any checked exceptions as unchecked BindingException
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the value of the given property in the given target object.
	 * 
	 * @param target
	 *            The target object to get the value from.
	 * @param property
	 *            The property to get the value of.
	 * @return The value of the property.
	 */
	public static Object getProperty(Object target, String property) {
		try {

			// find the field in the target object that matches the property
			// name
			Field field = findField(target.getClass(), property);
			
			if(field == null) {
				throw new RuntimeException("No property matching " + property + " was found");
			}

			// get initial field accessibility so we can reset it when we are
			// finished
			boolean origAccessibility = field.isAccessible();

			// since it is probably private, make it accessible
			field.setAccessible(true);

			// get the fields value
			Object value = field.get(target);

			// set field accessibility back to whatever it was to start with
			field.setAccessible(origAccessibility);

			return value;

			// re-throw any exceptions as unchecked BindingExceptions
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Recursively searches the target class and its parent classes for a field
	 * which name matches the value given in property.
	 * 
	 * Returns null if it doesn't find a match.
	 * 
	 * @param target -
	 *            The class to search.
	 * @param property -
	 *            The name of the field to search for.
	 * @return - The found field, or null.
	 */
	private static Field findField(Class<?> target, String property) {
		try {
			return target.getDeclaredField(property);
		} catch (NoSuchFieldException e) {
			Class<?> parent = target.getSuperclass();
			
			// if parent is null we have hit the top of the inheritance hierarchy
			if(parent==null) {
				return null;
			} else {
				// recurse
				return findField(parent, property);
			}
		}
	}
	
}
