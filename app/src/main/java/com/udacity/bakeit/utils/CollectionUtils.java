package com.udacity.bakeit.utils;

import java.util.List;

/**
 * Created by Viji
 * Collection related methods
 */
public class CollectionUtils {
    /**
     *
     * @param list
     * @param <T>
     * @return true if empty
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
