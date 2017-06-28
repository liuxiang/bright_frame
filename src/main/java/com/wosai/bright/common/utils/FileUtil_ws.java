package com.wosai.bright.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class FileUtil_ws {

    public static String uploadFileCheckImgAndLocal(HttpServletRequest request, MultipartFile file, String fileDir) throws Exception {
        if (file == null) {
            return null;
        }
        String[] fileType = file.getContentType().split("/");
        if (!"image".equals(fileType[0])) {
            throw new Exception("上传的文件类型必须为图片");
        }
        return uploadFileLocal(request, file, fileDir);
    }

    /**
     * 本地上传文件
     *
     * @param request
     * @param file
     * @param fileDir
     * @return
     * @throws IOException
     */
    public static String uploadFileLocal(HttpServletRequest request, MultipartFile file, String fileDir) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath(fileDir);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String imageFile = time + "_" + rannum + "_" + file.getOriginalFilename();

        if (imageFile.indexOf('.') == -1) {
            imageFile += ".jpg";// 暂时默认jpg,实际可依据file.getContentType()判定
        }
        File saveFile = new File(realPath, imageFile);
        org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);

        return fileDir + "/" + imageFile;
    }
}
