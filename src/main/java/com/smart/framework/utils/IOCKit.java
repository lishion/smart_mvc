package com.smart.framework.utils;

import com.smart.framework.annotation.Inject;
import com.smart.framework.bean.BeansContainer;
import com.smart.framework.bean.SmartBean;
import com.smart.framework.utils.ReflectionKit;

/**
 * Created by Lishion on 2017/7/28.
 */
public class IOCKit {

    public static void inject(BeansContainer beansContainer){

        beansContainer.getSmartBeans().forEach(bean->{//遍历每一个smartBean
            bean.visitField((clazz,field)->{//变量每一个属性
                if(field.isAnnotationPresent(Inject.class)){//该属性是否需要注入
                    Inject inject = field.getAnnotation(Inject.class);
                    //是否按默认类型注入
                    Class<?> injectClass = inject.value() == Inject.class ? field.getType() : inject.value();
                    SmartBean smartBean = beansContainer.getBean(injectClass);
                    if( smartBean != null ){
                        try {
                            ReflectionKit.setFiled( bean.getInstance(),field,smartBean.getInstance() );//注入实例
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("set filed error when Inject for filed:"+field.getName());
                        }
                    }else{
                        System.err.println( " can't find instance for class: " +  injectClass.getTypeName() );
                    }
                }
            });
        });
    }

}
