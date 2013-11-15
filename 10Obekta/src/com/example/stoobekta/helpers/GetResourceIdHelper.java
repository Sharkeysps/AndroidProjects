package com.example.stoobekta.helpers;

import java.lang.reflect.Field;



public class GetResourceIdHelper {

	public static int getId(String resourceName, Class<?> c) {
	    try {
	        Field idField = c.getDeclaredField(resourceName);
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        throw new RuntimeException("No resource ID found for: "
	                + resourceName + " / " + c, e);
	    }
	}
}
