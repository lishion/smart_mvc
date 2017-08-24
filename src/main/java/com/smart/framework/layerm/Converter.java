package com.smart.framework.layerm;

import sun.reflect.generics.tree.VoidDescriptor;

/**
 * Created by Lishion on 2017/7/9.
 */

public interface Converter<FromType ,ToType> {
    ToType convert(FromType t);
}
