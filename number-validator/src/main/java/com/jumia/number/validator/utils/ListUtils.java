package com.jumia.number.validator.utils;

import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static List getSubList(List targetList, Integer offset, Integer pageSize) {
        final int end = Math.min((offset + pageSize), targetList.size());
        return offset > targetList.size() ? Collections.emptyList() : targetList.subList(offset, end);
    }
}
