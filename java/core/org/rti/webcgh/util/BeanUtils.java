/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/org/rti/webcgh/util/BeanUtils.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:07 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.WebcghRuntimeException;
import org.rti.webcgh.core.WebcghSystemException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Methods for manipulating beans
 */
public class BeanUtils {
    
    // ====================================================
    //        Static variables
    // ====================================================
    
    private static final Logger LOGGER = Logger.getLogger(BeanUtils.class);
	

	/**
	 * Creates a map containing name/value pairs for all String
	 * fields in object
	 * @param obj Object
	 * @return Map containing name/value pairs for String fields
	 */
	public static synchronized Map objectPropsToMap(Object obj) {
		Map map = new HashMap();
		Class objClass = obj.getClass();
		Field[] objFields = objClass.getDeclaredFields();
		for (int i = 0; i < objFields.length; i++) {
			Field objField = objFields[i];
			Class fieldClass = objField.getType();
			if ("java.lang.String".equals(fieldClass.getName())) {
				String fieldName = objField.getName();
				String methodName = 
					"get" + 
					fieldName.substring(0, 1).toUpperCase() +
					fieldName.substring(1);
				try {
					Method objMethod = objClass.getMethod(methodName, new Class[0]);
					Object value = objMethod.invoke(obj, new Object[0]);
					map.put(fieldName, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	/**
	 * Get name/value pairs for all object String properties
	 * @param obj An object
	 * @return Object properties
	 */
	public static Properties getSringProperties(Object obj) {
		Properties props = new Properties();
		Class objClass = obj.getClass();
		Method[] objMethods = objClass.getMethods();
		for (int i = 0; i < objMethods.length; i++) {
			Method objMethod = objMethods[i];
			String methodName = objMethod.getName();
			if (methodName.indexOf("get") == 0 && 
				objMethod.getParameterTypes().length == 0) {
				if ("java.lang.String".equals(objMethod.getReturnType().getName()))
					try {
						String value = (String)objMethod.invoke(obj, new Object[0]);
						String propName = 
							methodName.substring(3, 4).toLowerCase() +
							methodName.substring(4);
						if (value == null)
							value = "";
						props.put(propName, value);
					} catch (Exception e) {}
			}
		}
		return props;	
	}
	/**
	 * Convert bean properties to XML element.  Each primitive property will
	 * be encoded as an attribute.  Each collection property will be
	 * converted into a set of nested elements.
	 * @param doc XML document
	 * @param obj Object whose properties will be converted to XML
	 * element
	 * @return XML element
	 */
	public static Element beanToXml(Document doc, Object obj) {
		Class objClass = obj.getClass();
		Element el = doc.createElement(objClass.getName());
		if (objClass == String.class)
			el.setAttribute("value", (String)obj);
		else {
			try {
				Method[] objMethods = objClass.getMethods();
				for (int i = 0; i < objMethods.length; i++) {
					Method objMethod = objMethods[i];
					String methodName = objMethod.getName();
					if (methodName.indexOf("get") == 0 && 
						objMethod.getParameterTypes().length == 0 &&
						! "getClass".equals(methodName)) {
						Object value = objMethod.invoke(obj, new Object[0]);
						String propName = 
							methodName.substring(3, 4).toLowerCase() +
							methodName.substring(4);
						if (value instanceof Collection) {
							Element childEl = doc.createElement(propName);
							childEl.setAttribute("class", value.getClass().getName());
							el.appendChild(childEl);
							Collection col = (Collection)value;
							for (Iterator it = col.iterator(); it.hasNext();)
								childEl.appendChild(beanToXml(doc, it.next()));
						} else {
						    if (value != null) {
								String valStr = value.toString();
								el.setAttribute(propName, valStr);
						    }
						}
					}
				}
			} catch (Exception e) {
				throw new WebcghRuntimeException("Error converting bean to XML", e);
			}
		}
		
		return el;
	}
	/**
	 * Convert XML element into an object with properties specified by
	 * XML attributes.  The XML element may represent a tree of objects.
	 * The class of each node in the tree will be given by the element
	 * tag name.  Object properties are encoded as element attributes.
	 * @param el Element
	 * @return An object
	 */
	public static Object xmlToBean(Element el) {
		Object obj = null;
		try {
			
			// Instantiate object
			Class objClass = Class.forName(el.getTagName());
			if (objClass == String.class)
				return el.getAttribute("value");
			obj = objClass.newInstance();
			
			
			// Set primitive attributes
			NamedNodeMap nodeMap = el.getAttributes();
			int idx = 0;
			Node att = nodeMap.item(idx++);
			while (att != null) {
				String name = att.getNodeName();
				String value = att.getNodeValue();
				setPrimitiveProperty(obj, name, value);
				att = nodeMap.item(idx++);
			}
			
			// Set collections
			NodeList children = el.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				if (node instanceof Element) {
					Element childEl = (Element)node;
					String propName = childEl.getNodeName();
					Class colClass = Class.forName(childEl.getAttribute("class"));
					Collection col = (Collection)colClass.newInstance();
					setProperty(obj, propName, col);
					NodeList grandChildren = childEl.getChildNodes();
					for (int j = 0; j < grandChildren.getLength(); j++) {
						Node temp = grandChildren.item(j);
						if (temp instanceof Element) {
							Element grandChild = (Element)temp;
							col.add(xmlToBean(grandChild));
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw new WebcghRuntimeException("Error converting XML to bean", e);
		}
		
		return obj;
	}
	/**
	 * Set primitieve property (e.g. int, long, boolean) of given object
	 * @param obj An object
	 * @param property Name of property to set
	 * @param value String encoding of value to assign property
	 * @throws IllegalArgumentException
	 */
	public static void setPrimitiveProperty
	(
		Object obj, String property, String value
	) throws IllegalArgumentException {
		try {
			String targetMethodName = 
				"set" + 
				property.substring(0, 1).toUpperCase() +
				property.substring(1);
			Class objClass = obj.getClass();
			Method[] objMethods = objClass.getMethods();
			for (int i = 0; i < objMethods.length; i++) {
				Method objMethod = objMethods[i];
				String methodName = objMethod.getName();
				if (methodName.equals(targetMethodName)) {
					Class[] paramTypes = objMethod.getParameterTypes();
					if (paramTypes.length == 1) {
						Class paramType = paramTypes[0];
						String className = paramType.getName();
						Object[] args = new Object[1];
						if ("int".equals(className))
							args[0] = new Integer(value);
						else if ("long".equals(className))
							args[0] = new Long(value);
						else if ("short".equals(className))
							args[0] = new Short(value);
						else if ("double".equals(className)) {
							String temp = value.toUpperCase();
							if ("-INFINITY".equals(temp))
								args[0] = new Double(Double.NEGATIVE_INFINITY);
							else if ("INFINITY".equals(temp))
								args[0] = new Double(Double.POSITIVE_INFINITY);
							else
								args[0] = new Double(value);
						}
						else if ("float".equals(className)) {
							String temp = value.toUpperCase();
							if ("-INFINITY".equals(temp))
								args[0] = new Float(Float.NEGATIVE_INFINITY);
							else if ("INFINITY".equals(temp))
								args[0] = new Float(Float.POSITIVE_INFINITY);
							else
								args[0] = new Float(value);
						}
						else if ("boolean".equals(className))
							args[0] = new Boolean(value);
						else if ("java.lang.String".equals(className))
							args[0] = value;
						else
						    try {
						        args[0] = Class.forName(value).newInstance();
						    } catch (Exception e) {
						        LOGGER.warn(e);
						    }
						objMethod.invoke(obj, args);
					}
				}
			}
		} catch (Exception e) {
			throw new WebcghRuntimeException("Error setting object property", e);
		}
	}
	/**
	 * Set property of object
	 * @param obj An object
	 * @param property Name of property to set
	 * @param value Value to set property to
	 */
	public static void setProperty
	(
		Object obj, String property, Object value
	) {
		try {
			String targetMethodName = 
				"set" + 
				property.substring(0, 1).toUpperCase() +
				property.substring(1);
			Class objClass = obj.getClass();
			Method[] objMethods = objClass.getMethods();
			for (int i = 0; i < objMethods.length; i++) {
				Method objMethod = objMethods[i];
				String methodName = objMethod.getName();
				if (methodName.equals(targetMethodName)) {
					Class[] paramTypes = objMethod.getParameterTypes();
					if (paramTypes.length == 1) {
						Class paramType = paramTypes[0];
						String className = paramType.getName();
						Object[] args = new Object[1];
						args[0] = value;
						objMethod.invoke(obj, args);
					}
				}
			}
		} catch (Exception e) {
			throw new WebcghRuntimeException("Error setting object property", e);
		}
	}
	
	
	/**
	 * Get class of property
	 * @param object An object
	 * @param propertyName Property name
	 * @return Property class
	 */
	public static Class getPropertyClass(Object object, String propertyName) {
	    return getPropertyClass(object.getClass(), propertyName);
	}
	
	
	/**
	 * Get class of property
	 * @param klass A class
	 * @param propertyName Property name
	 * @return Property class
	 */
	public static Class getPropertyClass(Class klass, String propertyName) {
	    String methodName = "set" + propertyName.substring(0, 1).toString().toUpperCase() + propertyName.substring(1);
	    Class propertyClass = null;
	    Method[] methods = klass.getMethods();
	    for (int i = 0; i < methods.length && propertyClass == null; i++) {
	        Method method = methods[i];
	        if (method.getName().equals(methodName)) {
	            Class[] paramClasses = method.getParameterTypes();
	            if (paramClasses.length == 1)
	                propertyClass = paramClasses[0];
	        }
	    }
	    return propertyClass;
	}
	
	
	/**
	 * Get object properties (i.e. attributes that have getters)
	 * @param object An object
	 * @param setterRequired If true, then only return properties
	 * for which there is both a getter and setter
	 * @return Properties
	 */
	public static Map getProperties(Object object, boolean setterRequired) {
		Map props = new HashMap();
		Class objectClass = object.getClass();
		Method[] methods = objectClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (BeanUtils.isGetter(method)) {
				boolean isProperty = true;
				String propertyName = BeanUtils.parsePropertyName(method.getName());
				if (setterRequired)
					if (! BeanUtils.hasSetter(object, propertyName))
						isProperty = false;
				if (isProperty) {
					Object value = null;
					try {
						value = method.invoke(object, new Object[0]);
					} catch (Exception e) {
						throw new WebcghSystemException("Error getting property value", e);
					}
					props.put(propertyName, value);
				}
			}
		}
		return props;
	}
	
	
	private static boolean isGetter(Method method) {
		return
			method.getName().indexOf("get") == 0 &&
			method.getParameterTypes() != null &&
			method.getParameterTypes().length == 0;
	}
	
	
	private static boolean isSetter(Method method) {
		return
			method.getName().indexOf("set") == 0 &&
			method.getParameterTypes() != null &&
			method.getParameterTypes().length == 1;
	}
	
	
	private static String parsePropertyName(String methodName) {
		assert methodName.length() > 3;
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}
	
	
	private static boolean hasSetter(Object object, String propertyName) {
		boolean hasSetter = false;
		Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < methods.length && ! hasSetter; i++) {
			Method method = methods[i];
			if (isSetter(method)) {
				String currPropertyName = parsePropertyName(method.getName());
				if (currPropertyName.equals(propertyName))
					hasSetter = true;
			}
		}
		return hasSetter;
	}
}
