package org.survey.repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.persistence.Id;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for getting and setting values of annotated field of a bean.
 */
@Slf4j
public class BeanHelper {
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";

    /**
     * Utility class should not have a public constructor.
     */
    private BeanHelper() {
    }

    /**
     * Return a value of a field from object which is a field marked with @Id
     * annotation.
     * 
     * @param object
     *            Object from which to read the value.
     */
    public static Object getId(Object object) {
        Preconditions.checkNotNull(object);
        return BeanHelper.getValueOfAnnotatedField(object, Id.class);
    }

    /**
     * Return a value of a field from object, which is marked with a given
     * annotation. Annotation can mark either a field or a getter method. Used
     * for reading an Id of an Entity.
     * 
     * @param object
     *            Object from which to read the value.
     * @param annotation
     *            Annotation which marks the field or getter.
     */
    public static Object getValueOfAnnotatedField(Object object,
            Class<? extends Annotation> annotation) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(annotation);
        Method getterMethod = null;
        Object value = null;
        getterMethod = getGetterMethodForFieldWithAnnotation(object.getClass(),
                annotation);
        if (getterMethod != null) {
            value = getValue(object, getterMethod);
        } else {
            getterMethod = getGetterMethodWithAnnotation(object.getClass(),
                    annotation);
            if (getterMethod != null) {
                value = getValue(object, getterMethod);
            }
        }
        return value;
    }

    private static Object getValue(Object object, Method getterMethod) {
        try {
            return getterMethod.invoke(object);
        } catch (Exception e) {
            log.warn("Unable to invoke method {}", getterMethod, e);
        }
        return null;
    }

    /**
     * Set the value of a field from object, which is marked with a given
     * annotation. Annotation can mark either a field or a setter method. Used
     * for setting an Id of an Entity.
     * 
     * @param object
     *            Object to which to read the value.
     * @param annotation
     *            Annotation which marks the field or setter.
     * @param value
     *            Value to set.
     */
    public static void setValueOfAnnotatedField(Object object,
            Class<? extends Annotation> annotation, Object value) {
        Method setterMethod = null;
        setterMethod = getSetterMethodForFieldWithAnnotation(object.getClass(),
                annotation, value.getClass());
        if (setterMethod != null) {
            setValue(object, value, setterMethod);
        } else {
            setterMethod = getSetterMethodWithAnnotation(object.getClass(),
                    annotation);
            if (setterMethod != null) {
                setValue(object, value, setterMethod);
            }
        }
    }

    private static void setValue(Object object, Object value,
            Method setterMethod) {
        try {
            setterMethod.invoke(object, value);
        } catch (Exception e) {
            log.warn("Unable to invoke method {}", setterMethod, e);
        }
    }

    protected static Method getGetterMethodForFieldWithAnnotation(
            Class<?> clazz, Class<? extends Annotation> annotation) {
        Method getterMethod = null;
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotation)) {
                    getterMethod = clazz.getMethod(fieldToGetter(field
                            .getName()));
                }
            }
        } catch (Exception e) {
            log.warn("Unable to get method {}", getterMethod, e);
        }
        if (getterMethod == null && clazz.getSuperclass() != null) {
            getterMethod = getGetterMethodForFieldWithAnnotation(clazz.getSuperclass(), annotation);
        }
        return getterMethod;
    }

    protected static Method getSetterMethodForFieldWithAnnotation(
            Class<?> clazz, Class<? extends Annotation> annotation,
            Class<?> parameterType) {
        Method setterMethod = null;
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotation)) {
                    String fieldSetterName = fieldToSetter(field.getName());
                    setterMethod = clazz.getDeclaredMethod(fieldSetterName,
                            parameterType);
                }
            }
        } catch (Exception e) {
            log.warn("Unable to get method {}", setterMethod, e);
        }
        if (setterMethod == null && clazz.getSuperclass() != null) {
            setterMethod = getSetterMethodForFieldWithAnnotation(clazz.getSuperclass(), annotation, parameterType);
        }
        return setterMethod;
    }

    protected static Method getGetterMethodWithAnnotation(Class<?> clazz,
            Class<? extends Annotation> annotation) {
        return getGetterOrSetterMethodWithAnnotation(clazz, annotation,
                GETTER_PREFIX);
    }

    protected static Method getSetterMethodWithAnnotation(Class<?> clazz,
            Class<? extends Annotation> annotation) {
        return getGetterOrSetterMethodWithAnnotation(clazz, annotation,
                SETTER_PREFIX);
    }

    private static Method getGetterOrSetterMethodWithAnnotation(Class<?> clazz,
            Class<? extends Annotation> annotation, String prefix) {
        try {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)
                        && method.getName().startsWith(prefix)) {
                    return method;
                }
            }
        } catch (SecurityException e) {
            log.warn("Unable to get methods from class {}", clazz.getCanonicalName(), e);
        }
        return null;
    }

    // TODO document
    protected static String fieldToGetter(String fieldName) {
        return fieldToSetterOrGetter(fieldName, GETTER_PREFIX);
    }

    // TODO document
    protected static String fieldToSetter(String fieldName) {
        return fieldToSetterOrGetter(fieldName, SETTER_PREFIX);
    }

    private static String fieldToSetterOrGetter(String fieldName, String prefix) {
        Preconditions.checkNotNull(fieldName);
        Preconditions.checkArgument(fieldName.length() > 0);
        return prefix + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }
}
