package com.test;

import com.smart.framework.annotation.Bean;
import com.smart.framework.annotation.BeanType;

import static com.smart.framework.annotation.BeanType.Model;

/**
 * Created by Lishion on 2017/8/26.
 */
@Bean(BeanType.Model)
public class User {

    private String name;
    private String sex;
    private int age;


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
