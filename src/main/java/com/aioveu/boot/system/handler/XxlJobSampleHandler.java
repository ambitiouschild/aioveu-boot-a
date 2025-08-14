package com.aioveu.boot.system.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xxl-job 测试示例（Bean模式）
 */
@Component
@Slf4j
public class XxlJobSampleHandler {

    @XxlJob("demoJobHandler")
    public void demoJobHandler() {
        log.info("XXL-JOB, Hello World. 我是可我不敌可爱, 以中有足乐者，不止口体之奉不若人也");
    }

}
