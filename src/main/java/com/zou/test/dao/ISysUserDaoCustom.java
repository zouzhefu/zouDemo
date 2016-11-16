package com.zou.test.dao;

import com.zou.test.domain.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzf on 2016/11/3.
 */
public interface ISysUserDaoCustom {
    Page<SysUser> query(Pageable pageable, String userName);
}