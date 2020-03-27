package com.sungness.framework.web.support.model;

import com.sungness.framework.web.support.enu.ItemTypeEnum;
import com.sungness.framework.web.support.annotation.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 管理系统模块信息Bean，从 @Module 注解解析出代码、名称、路径等信息，构造 ModuleInfo 对象。
 * Created by wanghongwei on 9/7/15.
 */
public class ModuleInfo extends BaseInfo {
    private static final long serialVersionUID = -4387449071436066470L;
    /** 所属菜单 */
    private MenuInfo menu;
    /** 包含命令列表 */
    private List<CommandInfo> commandList;
    /** 模块入口地址 */
    private String inletUri;

    /** 是否在顶部导航的菜单、侧边栏菜单显示，隐藏的模块只在某主模块内部使用，不显示在自动生成的导航中 */
    private boolean hideInMenu;

    public ModuleInfo() {
    }

    public ModuleInfo(Module module) {
        code = module.code();
        value = module.value();
        orderNumber = module.order();
        enable = module.enable();
        discard = false;
        hideInMenu = module.hideInMenu();
        icon = module.icon();
        commandList = new ArrayList<>();
        allowAccessAuthenticated = module.allowAccessAuthenticated();
    }

    public boolean equals(ModuleInfo o) {
        return super.equals(o)
                && Objects.equals(this.inletUri, o.inletUri)
                && Objects.equals(this.hideInMenu, o.hideInMenu);
    }

    public MenuInfo getMenu() {
        return menu;
    }

    public void setMenu(MenuInfo menu) {
        this.menu = menu;
    }

    public List<CommandInfo> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<CommandInfo> commandList) {
        this.commandList = commandList;
    }

    public void addCommand(CommandInfo commandInfo) {
        commandList.add(commandInfo);
    }

    public String getInletUri() {
        return inletUri;
    }

    public void setInletUri(String inletUri) {
        this.inletUri = inletUri;
    }

    public boolean isHideInMenu() {
        return hideInMenu;
    }

    public void setHideInMenu(boolean hideInMenu) {
        this.hideInMenu = hideInMenu;
    }

    /**
     * 判断模块下是否要在下级菜单中显示的命令
     * @return boolean 有则返回true, 否则返回false
     */
    public boolean haveSubMenuCommand() {
        for (CommandInfo commandInfo: commandList) {
            if (commandInfo.isShowInMenu()) {
                return true;
            }
        }
        return false;
    }

    public String getModuleKey() {
        return String.format("%s-%s", ItemTypeEnum.MODULE.getValue(), id);
    }

    public String getParentKey() {
        if (parentId != null && parentId > 0) {
            return String.format("%s-%s", ItemTypeEnum.MENU.getValue(), parentId);
        } else {
            return "0";
        }
    }
}
