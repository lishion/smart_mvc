package com.test;

import com.smart.framework.core.request.MultipartFile;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lishion on 2017/7/29.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        MultipartFile[] files = new MultipartFile[5];
        List<String> filess = new LinkedList<>();
        System.out.println(files instanceof MultipartFile[]);

    }
}
