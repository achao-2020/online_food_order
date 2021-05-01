package com.achao.online_food_order;

import com.achao.sdk.pojo.dto.AdminDTO;
import com.achao.sdk.pojo.vo.AdminVO;
import com.achao.sdk.pojo.vo.Result;
import com.achao.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Test
    public void createAdminTest() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setId("");
        adminDTO.setAccount("wangyi");
        adminDTO.setPassword("123456");
        adminDTO.setName("王一");
        adminDTO.setRoot("1");
    }
}
