package ig.helperlib.crud;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import ig.helperlib.fetch.Filterable;

public class FilterableCache {
    private static final FilterableCache instance = new FilterableCache();

    public static FilterableCache getInstance() {
        return instance;
    }

    private final HashMap<Class<?>, List<String>> fieldsIndex = new HashMap<>();

    private FilterableCache() {
    }

    public List<String> getFieldsFor(Class<?> cls) {
        ensureFieldsScanned(cls);
        return fieldsIndex.get(cls);
    }

    private void ensureFieldsScanned(Class<?> cls) {
        if (!fieldsIndex.containsKey(cls)) {
            synchronized(this) {
                if (!fieldsIndex.containsKey(cls)) {
                    scanAndStoreFields(cls);
                }
            }
        }
    }

    private void scanAndStoreFields(Class<?> cls) {
        ArrayList<String> fields = new ArrayList<>();
        scanFields(cls, "", fields);

        fieldsIndex.put(cls, Collections.unmodifiableList(fields));
    }

    private void scanFields(Class<?> cls, String prefix, ArrayList<String> fields) {
        Class<?> c = cls;

        while (c != Object.class) {
            for (Field field: cls.getDeclaredFields()) {
                if (field.getAnnotation(Filterable.class) != null) {
                    if (isPrimitiveType(field.getType())) {
                        fields.add(prefix + field.getName());
                    }
                    else {
                        scanFields(field.getType(), prefix + field.getName() + ".", fields);
                    }
                }
            }

            c = c.getSuperclass();
        }
    }

    private boolean isPrimitiveType(Class<?> t) {
        return ClassUtils.isPrimitiveOrWrapper(t) || t == String.class;
    }
}
