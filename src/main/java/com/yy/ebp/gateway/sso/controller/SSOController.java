package com.yy.ebp.gateway.sso.controller;

import com.yy.framework.core.reponse.Response;
import com.yy.framework.core.reponse.ResponseCallBack;
import com.yy.framework.core.reponse.ResponseCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "单点登录相关接口")
@RestController
@RequestMapping(value = "/sso")
public class SSOController {

    private final Logger logger = LoggerFactory.getLogger(SSOController.class);

    @ApiOperation(value = "手机登录接口")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response login(@ApiParam(value = "手机号") @RequestParam final String phoneNo,
                          @ApiParam(value = "验证码") @RequestParam final String code,
                          final HttpServletRequest request) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... obj) {
                logger.debug("login, phoneNo:{}, code:{}", phoneNo, code);
                criteria.addSingleResult(true);
            }
        }.sendRequest();
    }
}
