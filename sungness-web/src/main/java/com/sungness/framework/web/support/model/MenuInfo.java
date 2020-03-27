package com.sungness.framework.web.support.model;

import com.sungness.framework.web.support.annotation.Menu;
import com.sungness.framework.web.support.enu.ItemTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理系统菜单信息Bean，从 @Menu 注解解析出代码、名称、包名等信息，构造MenuInfo对象。
 * Created by wanghongwei on 9/7/15.
 */
public class MenuInfo extends BaseInfo {
    private static final long serialVersionUID = 7077297694775753247L;
    /** 上级菜单 */
    private MenuInfo superMenu;
    /** 下级菜单列表 */
    private List<MenuInfo> subMenuList;
    /** 包含模块列表 */
    private List<ModuleInfo> moduleList;

    public MenuInfo() {
    }

    public MenuInfo(Menu menu) {
        code = menu.code();
        value = menu.value();
        orderNumber = menu.order();
        enable = menu.enable();
        discard = false;
        icon = menu.icon();
        subMenuList = new ArrayList<>();
        moduleList = new ArrayList<>();
        allowAccessAuthenticated = menu.allowAccessAuthenticated();
    }

    public MenuInfo getSuperMenu() {
        return superMenu;
    }

    public void setSuperMenu(MenuInfo superMenu) {
        this.superMenu = superMenu;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<MenuInfo> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<MenuInfo> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public void addSubMenu(MenuInfo menuInfo) {
        subMenuList.add(menuInfo);
    }

    public List<ModuleInfo> getModuleList() {
        return moduleList;
    }

    public void addModule(ModuleInfo moduleInfo) {
        moduleList.add(moduleInfo);
    }

    public void setModuleList(List<ModuleInfo> moduleList) {
        this.moduleList = moduleList;
    }

    public String getMenuKey() {
        return String.format("%s-%s", ItemTypeEnum.MENU.getValue(), id);
    }

    public String getParentKey() {
        if (parentId != null && parentId > 0) {
            return String.format("%s-%s", ItemTypeEnum.MENU.getValue(), parentId);
        } else {
            return "0";
        }
    }
}
