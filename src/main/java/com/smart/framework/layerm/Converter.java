package com.smart.framework.layerm;

import sun.reflect.generics.tree.VoidDescriptor;

import java.util.Map;

/**
 * Created by Lishion on 2017/7/9.
 */

public interface Converter<FromType ,ToType> {

    ToType convert(FromType t);
}
