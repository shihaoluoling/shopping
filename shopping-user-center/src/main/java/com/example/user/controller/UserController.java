package com.example.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.example.user.dao.FileDescMapper;
import com.example.user.dao.HfUserMapper;
import com.example.user.model.FileDesc;
import com.example.user.model.HfUser;
import com.example.user.service.HfUserService;
import com.hanfu.common.service.FileMangeService;
import com.shopping.utils.response.handler.ResponseEntity;
import com.shopping.utils.response.handler.ResponseEntity.BodyBuilder;
import com.shopping.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.user.util.SmsDeo;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private HfUserMapper hfUserMapper;

    @Autowired
    private FileDescMapper fileDescMapper;

    @Autowired
    private HfUserService hfUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "获取短信验证", notes = "获取短信验证")
    @RequestMapping(value = "/getVerificationCode",method = RequestMethod.GET)
    public void getVerificationCode(@RequestParam(required = false,defaultValue = "") String phone) throws ClientException {

        SmsDeo sms = new SmsDeo();

        //判断收入的手机号是否正确
        String regExp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

        Pattern compile = Pattern.compile(regExp);

        Matcher matcher = compile.matcher(phone);

        boolean b = matcher.matches();

        //正确调用发送短信的工具类
        if(b){
            //获取返回的验证码
            Integer  code = sms.sendSms(phone);
            //存到redis
            stringRedisTemplate.opsForValue().set(phone+"",code+"",90, TimeUnit.SECONDS);
        }
    }

    @ApiOperation(value = "用户登录",notes = "用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(@RequestParam(required = false,defaultValue = "") String phone,
                        @RequestParam(required = false,defaultValue = "") String code){

        String redisCode = stringRedisTemplate.opsForValue().get(phone);

        //判断此用户是否已经注册  没有就让他先去注册
        Integer integer = hfUserService.hfUserIsExist(phone);

        if(integer!=0){
            //如果数据库有 再去比较验证码然后登录
            if(code.equals(redisCode)){
                System.out.println("登录成功");
                return "1";
            }else{
                System.out.println("登录失败");
                return "2";
            }
        }else{
            System.out.println("请先去注册");
            return "3";
        }
    }


    @ApiOperation(value = "用户注册",notes = "用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(
                            @RequestParam(required = false,defaultValue = "") String phone,
                            @RequestParam(required = false,defaultValue = "") String code) throws ClientException {

        System.out.println(phone);

        //判断此用户是否已经注册
        Integer integer = hfUserService.hfUserIsExist(phone);

        if(integer!=0){
            return "1";//已经存在，无法注册
        }else{
            //注册之前先获取页面的手机号和验证码  与数据库中的比较 比较成功注册
            String redisCode = stringRedisTemplate.opsForValue().get(phone);

            if(code.equals(redisCode)){
                Integer insert = hfUserService.register(phone);
                if (insert!=0){
                    System.out.println("注册成功");
                    return "4";
                }else {
                    System.out.println("注册失败");
                    return "5";
                }
            }else{
                return "3";//验证码不对
            }
        }
    }

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping(value = "/getImg")
    public ResponseEntity<JSONObject> getImg(MultipartFile file,Integer userId) throws IOException, JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        HfUser hfUser = new HfUser();
        try {
            FileMangeService fileMangeService = new FileMangeService();
            String arr[];
            arr = fileMangeService.uploadFile(file.getBytes(),String.valueOf(userId));
            FileDesc fileDesc = new FileDesc();
            fileDesc.setGroupName(arr[0]);
            fileDesc.setRemoteFilename(arr[1]);
            fileDesc.setFileName(file.getName());
            fileDesc.setUserId(userId);
            fileDesc.setCreateTime(LocalDateTime.now());
            fileDesc.setModifyTime(LocalDateTime.now());
            fileDesc.setIsDeleted((short) 0);
            fileDescMapper.insert(fileDesc);
            hfUser.setFileId(fileDesc.getId());
            hfUserMapper.insert(hfUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.body(ResponseUtils.getResponseBody(hfUserMapper.updateByPrimaryKeySelective(hfUser)));
    }




        @ApiOperation(value = "会员",notes = "会员")
        @GetMapping(value = "/member")
        public String member(@RequestParam(required = false,defaultValue = "") int id){
        //根据用户的ID查询是否是会员
        Integer userStatus = hfUserService.judgeUserById(id);
        //根据返回用户状态判断是不是会员
        if(userStatus==1){
            System.out.println("是会员");
            return "1";
        }else{
            System.out.println("不是会员");
            return "0";
        }
    }
}