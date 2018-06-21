package com.example.bootstart.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.bootstart.dao.UserMSTMapper;
import com.example.bootstart.entity.UserMST;
import com.example.bootstart.entity.UserMSTExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private UserMSTMapper mapper;
    //访问地址localhost:808X/test.do
    @GetMapping("test.do")
    public String test(UserMST userMST){
        logger.trace("I am trace {}.",userMST.getUsername());
        logger.debug("I am debug log {}.",userMST.getUsername());
        logger.warn("I am warn log {}.",userMST.getUsername());
        logger.error("I am error log {}.",userMST.getUsername());
        if(mapper.selectByUserNameAndPassWord(userMST)==1){
            return "hello world "+userMST.getUsername();
        }
        return "login fail";
    }
    @GetMapping("getAll.do")
    public String getAllUser(int pageNum, int pageSize){
        UserMSTExample example = new UserMSTExample();
        example.createCriteria().andUsernameLike("%t%");
        PageHelper.startPage(pageNum, pageSize);
        Page<UserMST> userMSTList= (Page<UserMST>) mapper.selectByExample(example);
        userMSTList.setTotal(mapper.countByExample(example));
        PageHelper.clearPage();
        System.out.println(userMSTList.getTotal());
        return JSONObject.toJSONString(userMSTList);
    }
}
