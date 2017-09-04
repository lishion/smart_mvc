package com.smart.framework.core.request;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Lishion on 2017/8/28.
 */
public class MultipartRequestWrapperResolver extends DefaultRequestWrapperResolver {
    @Override
    public RequestWrapperResolver resolve(HttpServletRequest request) throws Exception {
        super.resolve(request);
        try {
            MultipartParser multipartParser = new MultipartParser(request,100*1024*1024);
            multipartParser.setEncoding(request.getCharacterEncoding());
            Part part = multipartParser.readNextPart();
            while (part!=null){
                if(part.isParam()){
                    this.paramMap.put(part.getName(),((ParamPart)part).getStringValue());
                }else{
                    this.fileList.add(new MultipartFile((FilePart)part));
                }
                part = multipartParser.readNextPart();
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new Exception("upload file error!!!");
        }


        return this;
    }
}
