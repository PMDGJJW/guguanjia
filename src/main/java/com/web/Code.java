package com.web;

import com.google.code.kaptcha.servlet.KaptchaServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @auth jian j w
 * @date 2020/4/24 19:48
 * @Description
 */
@WebServlet(value = "/getCode.jpg",initParams = {
        @WebInitParam(name = "kaptcha.textproducer.char.length",value = "4"),
        @WebInitParam(name = "kaptcha.image.width",value = "120"),
        @WebInitParam(name = "kaptcha.image.height",value = "35"),
        @WebInitParam(name = "kaptcha.textproducer.font.color",value = "black"),
        @WebInitParam(name = "kaptcha.background.clear.from",value = "yellow"),
        @WebInitParam(name = "kaptcha.background.clear.to",value = "green"),
        @WebInitParam(name = "kaptcha.noise.color",value = "white"),
        @WebInitParam(name = "kaptcha.session.key",value = "checkCode"),
        @WebInitParam(name = "kaptcha.textproducer.font.size",value = "28"),
        @WebInitParam(name = "kaptcha.textproducer.char.string",value = "1234567890"),
        @WebInitParam(name = "kaptcha.textproducer.font.names",value = "Monospaced"),
        @WebInitParam(name = "kaptcha.obscurificator.impl",value = "com.google.code.kaptcha.impl.ShadowGimpy")
})
public class Code extends KaptchaServlet {


}
