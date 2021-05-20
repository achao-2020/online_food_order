package com.achao.service;

import com.achao.redis.RedisService;
import com.achao.sdk.pojo.constant.Constant;
import com.achao.sdk.pojo.constant.HttpStatus;
import com.achao.sdk.pojo.dto.DishesDTO;
import com.achao.sdk.pojo.dto.FileUploadDTO;
import com.achao.sdk.pojo.dto.QueryPageDTO;
import com.achao.sdk.pojo.po.DishesPO;
import com.achao.sdk.pojo.vo.DishesVO;
import com.achao.sdk.pojo.vo.PageVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.sdk.utils.DateUtil;
import com.achao.sdk.utils.FileUtil;
import com.achao.sdk.utils.ResponseUtil;
import com.achao.service.mapper.DisheMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.FileImageSource;
import sun.awt.image.InputStreamImageSource;

import java.io.*;
import java.util.*;

/**
 * @author achao
 */
@Slf4j
@Service("dishesService")
public class DishesService extends BaseService<DisheMapper, DishesPO, DishesVO> {

    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<List<DishesVO>> createOrder(List<DishesDTO> dtos) {
        List<DishesVO> info = new ArrayList<>();
        dtos.forEach(dto -> {
            // 如果id为null或者为空，则增加一个dishes
            DishesPO dishesPO = (DishesPO) super.getTo(new DishesPO(), dto);
            if (StringUtils.isBlank(dto.getId())) {
                dishesPO.setId("dis" + DateUtil.format(new Date()));
                super.createCurrency(dishesPO);
                DishesVO dishesVO = (DishesVO) super.getVo(new DishesVO(), dishesPO);
                info.add(dishesVO);
            } else {
                // 不为空的时候，按照id更新
                int success = this.baseMapper.updateById(dishesPO);
                if (success <= 0) {
                    log.error("数据" + dto.getId() + "更新失败");
                }
                DishesVO dishesVO = (DishesVO) super.getVo(new DishesVO(), dishesPO);
                info.add(dishesVO);
            }
        });
        return ResponseUtil.simpleSuccessInfo(info);
    }

    public void deleteById(String id) {
        int success = this.baseMapper.deleteById(id);
        if (success <= 0) {
            log.error("删除订单失败，order id:" + id);
        }
    }

    public Result<DishesVO> queryById(String id) {
        DishesPO dishesPO = this.baseMapper.selectById(id);
        if (dishesPO == null) {
            log.error("dishes查询为空！");
        }
        DishesVO dishesVO = new DishesVO();
        BeanUtils.copyProperties(dishesPO, dishesVO);
        return ResponseUtil.simpleSuccessInfo(dishesVO);
    }

    public Result<PageVO> queryPage(QueryPageDTO request) {
        return super.searchPageCurrency(request, DishesPO.class, DishesVO.class);
    }

    /**
     * 根据搜索前缀补充后面的字符串
     * @param prefix
     * @return
     */
    public Set<Object> queryByName(String prefix) {
        String key = Constant.HOST_DISH_NAME + prefix;
        return redisService.queryHostName(key);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Result<String> uploadPhoto(MultipartFile file, String dishesId) {
        String storeId = this.queryById(dishesId).getInfo().getStoreId();
        String parentPath = this.getClass().getClassLoader().getResource("").getPath();
        String filename = file.getOriginalFilename();
        String path = parentPath + "photo/dishes/" + storeId + "/"+ filename;
        try {
            if (!FileUtil.saveFile(file, path)) {
                return ResponseUtil.simpleFail(HttpStatus.BAD_REQUEST, "上传的图片大小不能超过1M", "失败");
            }
            DishesPO dishesPO = new DishesPO();
            dishesPO.setId(dishesId);
            dishesPO.setPhoto(path);
            this.updateCurrency(dishesPO);
        } catch (IOException e) {
            log.error("文件上传失败！", e);
        }
        return ResponseUtil.simpleSuccessInfo("成功！");
    }

    public ResponseEntity<byte[]> downPhoto(String dishesId) {
        String path = this.queryById(dishesId).getInfo().getPhoto();
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
        } catch (IOException e) {
            log.error("图片获取出错！", e);
        }
        return null;
    }
}
