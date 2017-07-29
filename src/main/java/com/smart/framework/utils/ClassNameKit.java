package com.smart.framework.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Lishion on 2017/7/17.
 */
public class ClassNameKit {

    final static char separator = File.separatorChar;
    final static String classPath = ClassKit.getClassPath();

    /**
     * 得到一个或多个包下的所有文件
     * @param packages:包名
     * @return 该包名(及其子包)下的所有class文件
     */
    public static List<String> getAllClassName(List<String> packages){
        List<String> ss = new ArrayList<>();
        packages.forEach(s->{
            try {
                ss.addAll( getClassName(s) );
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        return  ss;
    }

    /**
     * 将包名转换为时间的文件目录
     * @param topPackageName:包名
     * @return 转换后的文件目录
     */
    private  static String convertPackageToUrl(String topPackageName){

        return    classPath // 真实路径 = classPath + 分隔符 + 包路径
                + separator
                + topPackageName.replace('.',separator);

    }

    /**
     * 将路径转换为包名+类名
     * @param url: 文件路径
     * @return 包名+类名
     */
    private static String convertUrlToPackage(String url){
        return url.replace( classPath + separator," ")//去掉classPath 并将 分隔符替换为. 将路径转换为包名
                  .replace(separator,'.');
    }

    /**
     *
     * @param url:需要遍历的文件夹目录
     * @param filter:过滤器
     * @return 符合条件的文件名集合
     * @throws IOException
     */
    private static List<String> getFilesName(String url,Predicate<Path> filter) throws IOException{
        List<String> names = new LinkedList<>();
        Files.walkFileTree( Paths.get(url), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(filter.test(file) )  {
                    names.add( file.getParent().toString() + separator + file.getFileName().toString() );
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return names;
    }

    /**
     * 得到一个包下的所有class文件
     * @param packageName:包名
     * @return class文件名集合
     * @throws IOException
     */
    public static List<String> getClassName(String packageName) throws IOException {
        String url = convertPackageToUrl(packageName);//将包名转换为实际的文件路径
        List<String> fileNames = getFilesName(url,path -> path.getFileName().toString().endsWith(".class"));
        List<String> classNames = new LinkedList<>();
        fileNames.forEach( name->{
            String fileWithOutPostPostfix  = name.substring( 0,name.lastIndexOf(".") );//去掉后缀
            String className =  convertUrlToPackage(fileWithOutPostPostfix);//将路径转换为包名+类名
            classNames.add(className);
        });
        return classNames;
    }





}
