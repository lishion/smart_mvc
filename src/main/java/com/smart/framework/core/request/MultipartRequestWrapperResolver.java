package com.smart.framework.core.request;

import com.smart.framework.core.param.MultiPartFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Lishion on 2017/8/28.
 */
public class MultipartRequestResolver extends DefaultRequestWrapperResolver {
    @Override
    public RequestWrapperResolver resolve(HttpServletRequest request) {
        super.resolve(request);
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        FileUpload fileUpload = new FileUpload(fileItemFactory);
        try {
                List<FileItem> fileItems = fileUpload.parseRequest(new ServletRequestContext(request));
                fileItems.forEach(fileItem -> {
                    if(fileItem.isFormField()){
                        this.paramMap.put(fileItem.getFieldName(),fileItem.getString());
                    }else {
                        this.fileList.add(new MultiPartFile(fileItem));
                    }
                });
        }catch (FileUploadException e){
            System.err.println("upload file error!!");
            e.printStackTrace();
        }
        return this;
    }
}
