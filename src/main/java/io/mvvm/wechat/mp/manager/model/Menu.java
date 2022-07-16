package io.mvvm.wechat.mp.manager.model;

import com.google.gson.annotations.SerializedName;
import io.mvvm.wechat.mp.infra.Gsons;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Pan
 **/
@Data
public class Menu {

    /**
     * 一级菜单数组，个数应为1~3个
     */
    private List<Button> button;

    @Data
    public static class Button {
        /**
         * 说明: 菜单标题，不超过16个字节，子菜单不超过60个字节
         * <p>
         * 是否必须: 是
         */
        private String       name;
        /**
         * 说明: 菜单的响应动作类型
         *
         * <ul>
         *     <li>view: 表示网页类型</li>
         *     <li>click: 表示点击类型</li>
         *     <li>miniprogram: 表示小程序类型</li>
         *     <li>scancode_waitmsg: 扫码带提示</li>
         *     <li>scancode_push: 扫码推事件</li>
         *     <li>pic_sysphoto: 系统拍照发图</li>
         *     <li>pic_photo_or_album: 拍照或者相册发图</li>
         *     <li>pic_weixin: 微信相册发图</li>
         *     <li>location_select: 发送位置</li>
         *     <li>media_id: 图片</li>
         *     <li>view_limited: 图文消息</li>
         *     <li>article_id: 发布后的图文消息</li>
         *     <li>article_view_limited: 发布后的图文消息</li>
         * </ul>
         * <p>
         * 是否必须: 是
         */
        private String       type;
        /**
         * 说明: 菜单 KEY 值，用于消息接口推送，不超过128字节
         * <p>
         * 是否必须: click等点击类型必须
         */
        private String       key;
        /**
         * 说明: 二级菜单数组，个数应为1~5个
         * <p>
         * 是否必须: 否
         */
        @SerializedName("sub_button")
        private List<Button> subButton;
        /**
         * 说明: 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为 miniprogram 时，不支持小程序的老版本客户端将打开本url。
         * <p>
         * 是否必须: view、miniprogram类型必须
         */
        private String       url;
        /**
         * 说明: 调用新增永久素材接口返回的合法media_id
         * <p>
         * 是否必须: media_id类型和view_limited类型必须
         */
        @SerializedName("media_id")
        private String       mediaId;
        /**
         * 说明: 小程序的appid（仅认证公众号可配置）
         * <p>
         * 是否必须: miniprogram类型必须
         */
        @SerializedName("appid")
        private String       appId;
        /**
         * 说明: 小程序的页面路径
         * <p>
         * 是否必须: miniprogram类型必须
         */
        @SerializedName("pagepath")
        private String       pagePath;
        /**
         * 说明: 发布后获得的合法 article_id
         * <p>
         * 是否必须: article_id类型和article_view_limited类型必须
         */
        @SerializedName("article_id")
        private String       articleId;

    }

    @Override
    public String toString() {
        return Gsons.toJsonString(this);
    }

}
