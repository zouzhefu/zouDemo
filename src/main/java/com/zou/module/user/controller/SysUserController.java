package com.zou.module.user.controller;

import com.zou.commons.web.WebContextFactory;
import com.zou.module.user.domain.SysUser;
import com.zou.module.user.service.ISysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzf on 2016/12/9.
 */
@Controller
@RequestMapping("/admin/sysuser")
public class SysUserController {
    @Resource
    private ISysUserService sysUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginToFront(String loginId, String password, HttpServletRequest request){
        SysUser sysUser = sysUserService.loginSysUser(loginId, password);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (sysUser!=null){
//           request.getSession().setAttribute("adminUser",sysUser);
            WebContextFactory.getWebContext().setSessionAttr("adminUser",sysUser);
            modelMap.put("success","Y");
        }else {
            modelMap.put("success","N");
        }
        return modelMap;
    }
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String showMain(ModelMap model){
        return "admin/main";
    }

    @ResponseBody
    @RequestMapping(value = "/findSysUserPage",method = RequestMethod.GET)
    public  Map<String, Object> findSysUserPage(Integer limit, Integer offset,String searchUser, Pageable pageable){
        PageRequest pageRequest = new PageRequest(offset/limit,limit,pageable.getSort());
        Page<SysUser> userPage = sysUserService.findSysUserPage(pageRequest, searchUser);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("rows",userPage.getContent());
        modelMap.put("total",userPage.getTotalElements());
        WebContextFactory.getWebContext().getSessionAttr("adminUser");
        return modelMap;
    }
    @RequestMapping(value = "/sysUserList",method = RequestMethod.GET)
    public String showUserList(ModelMap model){
        return "admin/sysUser/sysUserList";
    }

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public SysUser selectUser(Integer id){
        SysUser user = sysUserService.findSysUser(id);
        return user;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(ModelMap model, SysUser sysUser,HttpServletRequest request){
        SysUser adminUser = (SysUser) request.getSession().getAttribute("adminUser");
        sysUser.setCreateBy(adminUser.getLoginId());
        sysUser.setLastModifiedBy(adminUser.getLoginId());
        String userSuccess = sysUserService.addSysUser(sysUser);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userSuccess);
        return modelMap;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(Integer id){
        sysUserService.deleteSysUser(id);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("isDelete","Y");
        return modelMap;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(ModelMap model,SysUser sysUser,HttpServletRequest request){
        SysUser adminUser = (SysUser) request.getSession().getAttribute("adminUser");
        sysUser.setLastModifiedBy(adminUser.getLoginId());
        String userSuccess = sysUserService.updateSysUser(sysUser);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success",userSuccess);
        return modelMap;
    }

    @RequestMapping(value = "/alertPsw",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> alertPsw(Integer id,String userPsw){
        sysUserService.alertPsw(id,userPsw);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("isAlertPsw","Y");
        return modelMap;
    }
}
