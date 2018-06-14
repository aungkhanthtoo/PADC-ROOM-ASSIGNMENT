package com.padcmyanmar.sfc.utils;

import java.util.List;

@FunctionalInterface
public interface QueryMapper<T> {

    List<T> query(String id);
}
