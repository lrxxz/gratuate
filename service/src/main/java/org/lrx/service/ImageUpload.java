package org.lrx.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ImageUpload {

    // 公共资源访问（服务器允许访问目录）
    @Value("${lrx.com.fileSpace}")
    public String FILE_SPACE;

    /**
     * 资源文件上传(文件的批量上传)
     *
     * @param file
     * @param fileSpace
     * @param uploadPathDB
     * @return
     */
    public String uploadResources(MultipartFile file, String fileSpace, String uploadPathDB) {

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null) {
                //生成文件名称
                String FileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //文件名不为空
                if (!StringUtils.isEmpty(FileName)) {
                    // 文件上传的最终保存路径
                    String finalPath = FILE_SPACE + fileSpace + uploadPathDB + "/" + FileName;
                    // 设置数据库保存的路径
                    uploadPathDB = fileSpace + uploadPathDB + "/" + FileName;
                    File outFile = new File(finalPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }
            return uploadPathDB;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String uploadImg(MultipartFile img) {
        //获取当前日期
        Date now = new Date();
        // 设定日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //定义上传图片的文件命名空间
        String fileSpace = "/community";
        // 保存到数据库的相对路径
        String uploadPathDB = "/" + dateFormat.format(now) + "/Imgs";
        // 上传图片
        return uploadResources(img, fileSpace, uploadPathDB);
    }
}
