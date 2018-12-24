package cn.com.likly.finalframework.logging.controller;

import cn.com.likly.finalframework.logging.LoggerService;
import cn.com.likly.finalframework.logging.entity.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-12 18:20:20
 * @since 1.0
 */
@RestController
@RequestMapping("/logger")
public class LoggerController {

    @Resource
    private LoggerService loggerService;

    @GetMapping("/reset")
    public List<Logger> reset() {
        return loggerService.resetLoggers();
    }

    @GetMapping("/list")
    public List<Logger> list() {
        return loggerService.getLoggers();
    }


}
