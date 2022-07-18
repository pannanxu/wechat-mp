package io.mvvm.wechat.mp.manager.model;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: Pan
 **/
public class SelfMenuInfo extends Menu {

    @SerializedName("is_menu_open")
    private Integer isMenuOpen;
    @SerializedName("selfmenu_info")
    private Info    info;

    public static class Info {

        private List<Button> button;

    }

    public static class Button {

        private String name;
        private String key;
        private String type;

    }
}
