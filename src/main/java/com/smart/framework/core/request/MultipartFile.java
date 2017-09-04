package com.smart.framework.core.request;

import com.oreilly.servlet.multipart.FilePart;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Lishion on 2017/8/28.
 */
public class MultipartFile {
    private FilePart fileItem;
    private String fieldName;
    public MultipartFile(FilePart fileItem) {
        this.fileItem = fileItem;
        fieldName = fileItem.getName();
    }

    public void writeTo(File file) throws IOException {

        fileItem.writeTo(file);

    }
    public void writeTo(OutputStream os) throws IOException {
        fileItem.writeTo(os);
    }


    public String getFileName(){
        return fileItem.getName();
    }
    public InputStream getInputStream() throws IOException {
        return fileItem.getInputStream();
    }

    public String getFieldName() {
        return fieldName;
    }
}
