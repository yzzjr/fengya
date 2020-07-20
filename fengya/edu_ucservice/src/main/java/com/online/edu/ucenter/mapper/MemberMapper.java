package com.online.edu.ucenter.mapper;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
public interface MemberMapper extends BaseMapper<Member> {
     Integer countRegister(String day);
}
