package com.example.travel.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.travel.cache.CacheManager;
import com.example.travel.dao.UserMapper;
import com.example.travel.dao.entity.OrderDO;
import com.example.travel.dao.entity.UserDO;
import com.example.travel.dto.DistributionDTO;
import com.example.travel.dto.UserDTO;
import com.example.travel.dto.WxUserDTO;
import com.example.travel.enums.OrderStatusEnum;
import com.example.travel.param.SelUserListParam;
import com.example.travel.service.DistributionAuditService;
import com.example.travel.util.AppInfoEnum;
import com.example.travel.util.BaseRespResult;
import com.example.travel.util.GenerateCodeUtil;
import com.example.travel.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * @author yijiyin
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    private static final String loginUrl = "https://api.weixin.qq.com/sns/jscode2session";


    @Resource
    private DistributionAuditService distributionAuditService;

    @Override
    public Page<UserDTO> getUserPage(SelUserListParam param) {
        Page<UserDTO> pageDTO = new Page(param.getCurrent(),param.getSize());

        Page<UserDO> page = new Page(param.getCurrent(),param.getSize());
        LambdaQueryWrapper<UserDO> wrapper = Wrappers.<UserDO>lambdaQuery()
                .like(StringUtils.isNotEmpty(param.getPhone()),UserDO::getPhone,param.getPhone())
                .eq(param.getDistributionIs() != null,UserDO::getFxsIs,param.getDistributionIs());

        Page<UserDO> doPage = page(page,wrapper);
        log.info("用户总数：{}",doPage.getTotal());
        pageDTO.setTotal(doPage.getTotal());
        List<UserDTO> userDTOS = new ArrayList<>();
        for (UserDO userDO :doPage.getRecords()){
            UserDTO userDTO = new UserDTO();
            userDTO.setFxsCode(userDO.getFxsCode());
            userDTO.setName(userDO.getName());
            userDTO.setOpenId(userDO.getOpenId());
            userDTO.setPhone(userDO.getPhone());
            userDTO.setCreateDate(userDO.getCreateDate());
            userDTO.setFxsSetDay(userDO.getFxsSetDay());
            userDTOS.add(userDTO);
        }
        pageDTO.setRecords(userDTOS);


        return pageDTO;
    }

    @Override
    public String wxLogin(String code,String name,String phone) {
        log.info("登录信息code:{}",code);
        HashMap map = new HashMap(4);
        map.put("appid",AppInfoEnum.APP_ID.getValue());
        map.put("secret", AppInfoEnum.APP_SECRET.getValue());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String loginReturnString = HttpClientUtil.doGet(loginUrl,map);
        log.info("返回数据：{}",loginReturnString);
        JSONObject jsonObject = JSON.parseObject(loginReturnString);
        log.info("返回数据：{}",jsonObject);

        // 小程序用户数据
        String openId = jsonObject.getString("openid");
        String unionId = jsonObject.getString("unionId");
        String sessionKey = jsonObject.getString("session_key");

        // 通过openId查询验证用户是否存在
        UserDO user = getUserInfoByOpenId(openId);
        if (user == null) {
            user = new UserDO();
            user.setOpenId(openId);
            user.setUnionId(unionId);
            user.setName(name);
            user.setPhone(phone);
            user.insert();
        }
        // 生成以sessionKey和token为对应的token编码
        String token = GenerateCodeUtil.createCode(20);
        log.info("生成的token:{}",token);
        // 将生成的token编码和openId,sessionKey关联存储 session,全局Map或redis
        CacheManager.put(token,openId);
        // 返回给前端
        return token;
    }

    @Override
    public BaseRespResult getwxacodeunlimit(String openId) {

        try {
            UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
            String filePath="/data/fxImg/"+userDO.getId()+".png";
            File file = new File(filePath);
            if(file.exists()){
                return BaseRespResult.successResult(file);
            }


            OutputStream os = new FileOutputStream(file);

            HashMap map = new HashMap(4);
            map.put("appid",AppInfoEnum.APP_ID.getValue());
            map.put("secret", AppInfoEnum.APP_SECRET.getValue());
            map.put("grant_type","client_credential");
            String tokenReturnString = HttpClientUtil.doGet("https://api.weixin.qq.com/cgi-bin/token",map);
            log.info("token返回数据：{}",tokenReturnString);
            JSONObject jsonObject = JSON.parseObject(tokenReturnString);
            String access_token = (String) jsonObject.get("access_token");



            //调用微信接口生成二维码
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="  + access_token);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            //这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("scene", "id="+userDO.getId());
            //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 430);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();

            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
            bis.close();
            log.info("生成二维码成功,{}", filePath);





            return BaseRespResult.successResult(openId+".png");
            // 返回给前端
        }catch (Exception e){
            e.printStackTrace();

        }
        return BaseRespResult.errorResult("二维码分享失败");
    }

    @Override
    public WxUserDTO getUserByFxId(Long id) {
        WxUserDTO wxUserDTO = new WxUserDTO();
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getId, id));

        // 分销商申请状态
        DistributionDTO dto =  distributionAuditService.getListByOpenId(userDO.getOpenId());
        if (dto != null) {
            wxUserDTO.setFxsRequestStatus(dto.getStatus());
            if (OrderStatusEnum.ALREADY_PAY.getStatus().equals(dto.getStatus())){
                if (userDO.getFxsIs() != null && userDO.getFxsIs()) {
                    wxUserDTO.setFxsCode(userDO.getFxsCode());
                } else {
                    wxUserDTO.setFxsRequestStatus(4);
                }
            }
        } else {
            wxUserDTO.setFxsRequestStatus(4);
        }

        // 绑定的分销商编码
        if (userDO.getBindFxsEndTime() != null && userDO.getBindFxsEndTime().after(new Date())) {
            wxUserDTO.setBindfxsCode(userDO.getBindFxsCode());
        }
        return wxUserDTO;
    }

    @Override
    public String login(String userName, String userPassword) {
        log.info("登录信息：{}",userName);
        // 验证后台用户名密码 返回token
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUnionId, userName));
        if (userDO != null) {
            if (userPassword.equals(userDO.getPassword())) {
                String token = GenerateCodeUtil.createCode(20);
                CacheManager.put(token,userDO.getOpenId());
                return token;
            }
            return null;
        }
        return null;
    }

    @Override
    public void updateFXS(String openId, String fxsCode,String phone) {
        baseMapper.updateFXS(openId,fxsCode,phone);
    }

    @Override
    public void updateByIds(UserDO userDO) {
        userDO.updateById();
    }

    @Override
    public WxUserDTO getUserInfo(String openId) {
        WxUserDTO wxUserDTO = new WxUserDTO();
        UserDO userDO = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));

        // 分销商申请状态
        DistributionDTO dto =  distributionAuditService.getListByOpenId(openId);
        if (dto != null) {
            wxUserDTO.setFxsRequestStatus(dto.getStatus());
            if (OrderStatusEnum.ALREADY_PAY.getStatus().equals(dto.getStatus())){
                if (userDO.getFxsIs() != null && userDO.getFxsIs()) {
                    wxUserDTO.setFxsCode(userDO.getFxsCode());
                } else {
                    wxUserDTO.setFxsRequestStatus(4);
                }
            }
        } else {
            wxUserDTO.setFxsRequestStatus(4);
        }

        // 绑定的分销商编码
        if (userDO.getBindFxsEndTime() != null && userDO.getBindFxsEndTime().after(new Date())) {
            wxUserDTO.setBindfxsCode(userDO.getBindFxsCode());
        }
        return wxUserDTO;
    }

    @Override
    public UserDO getUserInfoByFxsCode(String fxsCode) {

        UserDO one = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getFxsCode, fxsCode).eq(UserDO::getFxsIs,true));
        log.info("one {}",JSON.toJSONString(one));
        return one;
    }

    @Override
    public boolean deleteDistribution(String openId) {
        baseMapper.deleteDistribution(openId);
        return true;
    }

    @Override
    public boolean updateFxsSetDay(String openId, Integer fxsSetDay) {
        baseMapper.updateFxsSetDay(openId,fxsSetDay);
        return true;
    }

    @Override
    public boolean setUserfxsCode(String openId, String fxsCode) {
        try {
            UserDO one = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getFxsCode, fxsCode).eq(UserDO::getFxsIs,true));
            if(Objects.isNull(one)||Objects.isNull(one.getFxsSetDay())){
                one.setFxsSetDay(0);
            }
            baseMapper.setUserfxsCode(openId,fxsCode,one.getFxsSetDay());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public UserDO getUserInfoByOpenId(String openId){
        log.info("getUserInfoByOpenId openId：{}",openId);

        UserDO one = getOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, openId));
        log.info("one {}",JSON.toJSONString(one));
        return one;
    }
}
