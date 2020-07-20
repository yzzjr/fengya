package com.online.edu.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.handler.EduException;
import com.online.edu.ucenter.entity.Member;
import com.online.edu.ucenter.entity.dto.Login;
import com.online.edu.ucenter.entity.dto.Register;
import com.online.edu.ucenter.mapper.MemberMapper;
import com.online.edu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zhouzhou
 * @since 2020-04-10
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public Integer countRegister(String day) {

        return baseMapper.countRegister(day);

    }

    @Override
    public Boolean addMember(Register register) {
        Member member = new Member();
        BeanUtils.copyProperties(register,member);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/MQ7qUmCprK9am16M1Ia1Cs3RK0qiarRrl9y8gsssBjIZeS2GwKSrnq7ZYhmrzuzDwBxSMMAofrXeLic9IBlW4M3Q/132");
        int result = baseMapper.insert(member);
        if(result ==0){
            throw  new EduException(20001,"用户注册失败");
        }
        return true;
    }

    @Override
    public Member login(Login login) {

        String mobile = login.getMobile();
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member member = baseMapper.selectOne(wrapper);
        System.out.println(member.getPassword());
        if(member.getPassword().equals(login.getPassword())){

            return member;
        }
        else{return null;}
    }
}
