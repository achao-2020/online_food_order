package com.achao.sdk.utils;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author aChao
 * @create: 2021-05-19 23:34
 */
@Slf4j
public class FileUtil {
    /**
     * 指定路径保存文件
     * @param file
     * @param path
     * @return
     */
    public static boolean saveFile(MultipartFile file, String path) throws IOException {
        File target = new File(path);
        // 检查存储目录是否为空
        if (!target.exists()) {
            target.mkdirs();
        }
        long size = file.getSize();
        if (size > 1024 * 1024) {
            log.error("上传的图片大小不能超过1M！");
            return false;
        }
        File targetFile = new File(path);
        file.transferTo(targetFile);
        return true;
    }
}
