package io.mvvm.wechat.mp.manager;

/**
 * @description: 用户管理
 * @author: Pan
 **/
public interface IUserManager extends IBaseBean {

    String createTag(String appId, String tagName);

}
