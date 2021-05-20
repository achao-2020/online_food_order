package com.achao.sdk.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author aChao
 * @create: 2021-05-19 23:23
 */
@ApiModel(value = "文件传输类")
@ToString
@Data
public class FileUploadDTO {
    @ApiModelProperty(value = "path", notes = "文件上传路径（可以为空）")
    private String path;
    @ApiModelProperty(value = "fileName", notes = "文件名称")
    private String fileName;
    @ApiModelProperty(value = "file", notes = "文件流")
    private MultipartFile file;
}
