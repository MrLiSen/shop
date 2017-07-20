package com.shop.dao;

import com.shop.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by 73121 on 2017/7/19.
 */
@Repository
public interface MemberDao {
    @Select("select id, username from xx_member where ${columnName} = #{columnValue}")
    Member findByColumn(@Param(value = "columnName") String columnName, @Param(value = "columnValue")String columnValue);

    @Insert("INSERT INTO xx_member (username, password, email, phone, gender, name, mobile, create_date, modify_date, version, "
            + " amount,balance, is_enabled, is_locked, lock_key, login_failure_count, point, register_ip, member_rank) "
            + " VALUES (#{username}, #{password}, #{email}, #{phone}, #{gender}, #{name}, #{mobile}, now(), now(), 0, 0, 0, 1, 0, '', 0, 0, '',1)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Member member);



    @Select("select id, username,password from xx_member where username = #{username}")
    Member findByName(@Param(value = "username") String username);
}
