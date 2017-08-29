package com.smart.framework.core.param;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Lishion on 2017/8/28.
 */
public class MultipartFile {
    private FileItem fileItem;
    private String fieldName;
    public MultipartFile(FileItem fileItem) {
        this.fileItem = fileItem;
        fieldName = fileItem.getFieldName();
    }

    public void toFile(File file) throws Exception {

        fileItem.write(file);
        deleteTempFlie();
    }
    private void deleteTempFlie(){
        fileItem.delete();
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
