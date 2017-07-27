package com.smart.framework.bean;

import com.smart.framework.annotation.Inject;
import com.smart.framework.boot.Service;
import com.smart.framework.cache.DataPool;
import com.smart.framework.cache.DataPoolItem;
import com.smart.framework.utils.ReflectionUtil;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by Lishion on 2017/7/21.
 */
public class IOCService implements Service {


    @Override
    public void start() {
        BeansContainer beansContainer = (BeansContainer)DataPool.need(DataPoolItem.beanContainer);
        beansContainer.getSmartBeans().forEach(bean->{//遍历每一个smartBean
            bean.visitField((clazz,field)->{//变量每一个属性
                if(field.isAnnotationPresent(Inject.class)){//该属性是否需要注入
                    Inject inject = field.getAnnotation(Inject.class);
                    //是否按默认类型注入
                    Class<?> injectClass = inject.value() == Inject.class ? field.getType() : inject.value();
                    SmartBean smartBean = beansContainer.getBean(injectClass);
                    if( smartBean != null ){
                       try {
                           ReflectionUtil.setFiled( bean.getInstance(),field,smartBean.getInstance() );//注入实例
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
