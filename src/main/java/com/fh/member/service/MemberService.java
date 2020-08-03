package com.fh.member.service;

import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

public interface MemberService {
    Member getUserByName(String name);

    Member getUserByphone(String phone);
    ServerResponse addUser(Member member);

    ServerResponse longin(Member member);
}
