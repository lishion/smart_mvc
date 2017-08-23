package com.smart.framework.bean;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;


/**
 * Created by Lishion on 2017/7/11.
 */
public class BeansContainer {

    private Set<SmartBean> smartBeans = new HashSet<>();//存放所有bean的集合

    /**
     * 从class集合中生成bean容器
     * @param classContainer:class集合类
     * @return bean容器
     */
    public void load( ClassContainer classContainer) throws Exception {

        try {
            for(Class<?> clazz:classContainer.getBeanClass()){
               // smartBeans.add( BeanFactory.build(clazz) );
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("load beans error!!!");
        }
    }

    public Set<SmartBean> getSmartBeans() {
        return smartBeans;
    }

    public void visit(Consumer<SmartBean> consumer){
        smartBeans.forEach(consumer::accept);
    }
    /**
     * 通过类的class得到smart bean对象
     * @param clazz:类的class属性
     * @return smart bean对象
     */
    public SmartBean getBean(Class<?> clazz){
        for (SmartBean smartBean:smartBeans){
            if(smartBean.getProxyClazz()==clazz || smartBean.getClazz() == clazz){
                return smartBean;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "BeansContainer{" +
                "smartBeans=" + smartBeans +
                '}';
    }

}
