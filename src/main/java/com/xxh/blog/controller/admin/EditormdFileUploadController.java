package com.xxh.blog.controller.admin;



import com.xxh.blog.utils.FileUploadUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



@Controller
@RequestMapping(value = "/admin" )
public class EditormdFileUploadController {
   
    /**
     * 图片上传
     * @param request
     * @param response
     * @param file
     * @throws IOException
     */
    @RequestMapping(value = "/uploadfile" ,method = RequestMethod.POST)
    public void editorMD(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter wirte = null;
            JSONObject json = new JSONObject();
            try {
                wirte = response.getWriter();
                //文件存放的路径
                String path = request.getSession().getServletContext().getRealPath("upload");
                String url = "http://47.107.174.92:8080"
                        + request.getContextPath()
                        + "/upload/"
                        + new FileUploadUtil().upload(request, file, path);
                json.put("success", 1);
                json.put("message", "上传成功!");
                json.put("url", url);
            } catch (Exception e) {
                json.put("success",0);
            }finally{
                wirte.print(json);
                wirte.flush();
                wirte.close();
            }

    }

}