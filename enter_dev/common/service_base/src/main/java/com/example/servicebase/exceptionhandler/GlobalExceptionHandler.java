package com.example.servicebase.exceptionhandler;

import com.example.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特殊异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

//    //自定义异常处理
//    @ExceptionHandler(GuliException.class)
//    @ResponseBody
//    public R error(GuliException e){
//        log.error(e.getMessage());
//        e.printStackTrace();
//        return R.error().message(e.getMsg()).code(e.getCode());
//    }
}
