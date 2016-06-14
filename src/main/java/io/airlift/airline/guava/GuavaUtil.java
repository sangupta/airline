package io.airlift.airline.guava;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.airlift.airline.model.ArgumentsMetadata;

public class GuavaUtil {

    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkArgument(boolean condition, String message, Object... args) {
        if (!condition) {
            throw new IllegalArgumentException(format(message, args));
        }
    }

    public static <T> T checkNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }

        return object;
    }

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }

        return object;
    }

    public static void checkState(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    public static <T> List<T> immutableListOf(T[] objects) {
        List<T> list = new ArrayList<>();
        for (T item : objects) {
            list.add(item);
        }

        // make it immutable
        return list;
    }

    public static <T> List<T> immutableListOf(Iterable<T> objects) {
        List<T> list = new ArrayList<>();
        for (T item : objects) {
            list.add(item);
        }

        // make it immutable
        return list;
    }

    public static <T> T firstNonNull(T... items) {
        for (T item : items) {
            if (item != null) {
                return item;
            }
        }

        // TODO: check if we need to throw an exception here
        return null;
    }

    public static <T> List<T> concatList(List<T> fields, T field) {
        List<T> list = new ArrayList<>(fields);
        list.add(field);
        return list;
    }

    public static <T> List<T> concatList(Iterable<T> iterable, T item) {
        List<T> list = new ArrayList<>();

        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        list.add(item);

        return list;
    }

    public static <T> List<T> emptyList() {
        List<T> list = new ArrayList<>();
        return list;
    }

    public static <F, T> List<T> transform(List<F> fromList, ValueChanger<F, T> transformer) {
        List<T> list = new ArrayList<>();
        for (F item : fromList) {
            T value = transformer.apply(item);
            list.add(value);
        }

        return list;
    }

    public static <F, T> List<T> transform(Iterable<F> fromList, ValueChanger<F, T> transformer) {
        List<T> list = new ArrayList<>();
        for (F item : fromList) {
            T value = transformer.apply(item);
            list.add(value);
        }

        return list;
    }

    public static interface ValueChanger<F, T> {

        public T apply(F item);

    }

    public static <T> List<T> arrayList(T item) {
        List<T> list = new ArrayList<>();
        list.add(item);
        return list;
    }

    public static <T> Set<T> hashSet(T item) {
        Set<T> list = new HashSet<>();
        list.add(item);
        return list;
    }

    public static <T> List<T> arrayList(T... items) {
        List<T> list = new ArrayList<>();
        for (T item : items) {
            list.add(item);
        }

        return list;
    }

    public static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    public static <T> void addAll(Collection<Object> collection, Iterable<?> iterable) {
        for (Object item : iterable) {
            collection.add(item);
        }
    }

    public static void join(String joiner, StringBuilder appendable, Collection<?> items) {
        boolean first = true;

        for (Object item : items) {
            if (!first) {
                appendable.append(joiner);
            }

            appendable.append(item.toString());

            first = false;
        }
    }

    public static void join(String joiner, StringBuilder appendable, Iterable<?> items) {
        boolean first = true;

        for (Object item : items) {
            if (!first) {
                appendable.append(joiner);
            }

            appendable.append(item.toString());

            first = false;
        }
    }

    public static void join(String joiner, StringBuilder appendable, Object[] items) {
        boolean first = true;

        for (Object item : items) {
            if (!first) {
                appendable.append(joiner);
            }

            appendable.append(item.toString());

            first = false;
        }
    }

    public static String join(String joiner, Collection<?> items) {
        StringBuilder builder = new StringBuilder();
        join(joiner, builder, items);

        return builder.toString();
    }

    public static String join(String joiner, Iterable<?> items) {
        StringBuilder builder = new StringBuilder();
        join(joiner, builder, items);

        return builder.toString();
    }

    public static String join(String joiner, Object[] items) {
        StringBuilder builder = new StringBuilder();
        join(joiner, builder, items);

        return builder.toString();
    }

    public static List<String> split(String pattern, boolean omitEmptyStrings, boolean trimResults, String stringToSplit) {
        String[] tokens = stringToSplit.split(pattern);

        List<String> splits = new ArrayList<>();
        for (String token : tokens) {
            if (omitEmptyStrings && token.isEmpty()) {
                continue;
            }

            token = token.trim();
            splits.add(token);
        }

        return splits;
    }

    public static boolean isEmpty(Iterable<?> items) {
        if (items == null) {
            return true;
        }

        if (items.iterator().hasNext()) {
            return false;
        }

        return true;
    }

    public static Object getLast(Iterable<?> values) {
        if (values == null) {
            return null;
        }

        Iterator<?> it = values.iterator();
        Object last = null;
        while (it.hasNext()) {
            last = it.next();
        }

        return last;
    }

    public static Object getFirst(List<?> items, Object defaultValue) {
        if (items == null || items.isEmpty()) {
            return defaultValue;
        }

        return items.get(0);
    }

    public static <T> Set<T> copyOf(Iterable<T> options) {
        Set<T> set = new HashSet<>();

        Iterator<T> it = options.iterator();
        while (it.hasNext()) {
            set.add(it.next());
        }

        return set;
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
        if (iterator instanceof PeekingIteratorImpl) {
            @SuppressWarnings("unchecked")
            PeekingIteratorImpl<T> peeking = (PeekingIteratorImpl<T>) iterator;
            return peeking;
        }
        
        return new PeekingIteratorImpl<T>(iterator);
    }
}
