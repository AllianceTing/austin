package com.java3y.austin.web.controller;


import cn.hutool.core.util.StrUtil;
import com.java3y.austin.common.constant.AustinConstant;
import com.java3y.austin.common.vo.BasicResultVO;
import com.java3y.austin.support.domain.ChannelAccount;
import com.java3y.austin.web.service.ChannelAccountService;
import com.java3y.austin.web.vo.amis.CommonAmisVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 渠道账号管理接口
 *
 * @author 3y
 */
@Slf4j
@RestController
@RequestMapping("/account")
@Api("渠道账号管理接口")
@CrossOrigin(origins = AustinConstant.ORIGIN_VALUE, allowCredentials = "true", allowedHeaders = "*")
public class ChannelAccountController {

    @Autowired
    private ChannelAccountService channelAccountService;


    /**
     * 如果Id存在，则修改
     * 如果Id不存在，则保存
     */
    @PostMapping("/save")
    @ApiOperation("/保存数据")
    public BasicResultVO saveOrUpdate(@RequestBody ChannelAccount channelAccount) {
        return BasicResultVO.success(channelAccountService.save(channelAccount));
    }

    /**
     * 根据渠道标识查询渠道账号相关的信息
     */
    @GetMapping("/queryByChannelType")
    @ApiOperation("/根据渠道标识查询相关的记录")
    public BasicResultVO query(Integer channelType) {
        List<CommonAmisVo> result = new ArrayList<>();

        List<ChannelAccount> channelAccounts = channelAccountService.queryByChannelType(channelType);
        for (ChannelAccount channelAccount : channelAccounts) {
            CommonAmisVo commonAmisVo = CommonAmisVo.builder().label(channelAccount.getName()).value(String.valueOf(channelAccount.getId())).build();
            result.add(commonAmisVo);
        }
        return BasicResultVO.success(result);
    }

    /**
     * 所有的渠道账号信息
     */
    @GetMapping("/list")
    @ApiOperation("/渠道账号列表信息")
    public BasicResultVO list() {
        return BasicResultVO.success(channelAccountService.list());
    }

    /**
     * 根据Id删除
     * id多个用逗号分隔开
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation("/根据Ids删除")
    public BasicResultVO deleteByIds(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            List<Long> idList = Arrays.stream(id.split(StrUtil.COMMA)).map(s -> Long.valueOf(s)).collect(Collectors.toList());
            channelAccountService.deleteByIds(idList);
            return BasicResultVO.success();
        }
        return BasicResultVO.fail();
    }

}
