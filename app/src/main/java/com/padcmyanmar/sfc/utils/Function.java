package com.padcmyanmar.sfc.utils;

@FunctionalInterface
public interface Function<X, Y> {

    Y map(X data);
}
