package com.artist.myworld.controller;

import com.artist.myworld.dao.BankCardDao;
import com.artist.myworld.domain.BankCard;
import com.dili.http.okhttp.OkHttpUtils;
import com.dili.http.okhttp.callback.StringCallback;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by asiamastor on 2016/12/21.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private BankCardDao bankCardDao;

    @ApiOperation(value="展示用户详细信息", notes="根据id来指定对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    })
        @RequestMapping("")
        public String index(Long id, ModelMap model) throws IOException {
            System.out.println("进入index,id:"+id);
            BankCard bankCard = bankCardDao.get(1l);
            System.out.println("bankCard"+bankCard);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        String url = "http://localhost/api/login/buyerLogin";
        Response rep = OkHttpUtils
                .postString()
                .url(url).content("{\"username\":\"王宓\", \"password\":\"123\"}")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute();
        model.put("data",rep.body().string());
            return "index";
        }


}
