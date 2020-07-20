package com.online.edu.ucenter.service;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.ucenter.entity.dto.Login;
import com.online.edu.ucenter.entity.dto.Register;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
public interface MemberService extends IService<Member> {

    Integer countRegister(String day);

    Boolean addMember(Register member);

    Member login(Login login);
}
