package com.sungness.framework.web.support.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * 模块树，保存系统启动时整个系统的所有模块，以树形结构组织而成。
 * Created by wanghongwei on 9/8/15.
 */
public class ModuleTree implements Serializable {
    private static final long serialVersionUID = 6968504090236544323L;

    private static final Logger log = LoggerFactory.getLogger(ModuleTree.class);

    /**
     * 菜单Map，<id, 菜单对象>
     */
    private Map<Long, MenuInfo> menuMapWithLongKey;

    /**
     * 菜单Map，<pkgName, 菜单对象>
     */
    private Map<String, MenuInfo> menuMapWithStringKey;

    /**
     * 模块Map，<id, 模块对象>
     */
    private Map<Long, ModuleInfo> moduleMap;

    /**
     * 命令Map，<path, 命令对象>
     */
    private Map<String, CommandInfo> commandMap;

    /** 根菜单列表 */
    private List<MenuInfo> rootMenuList;

    /** 根模块列表，没有上级菜单的模块 */
    private List<ModuleInfo> rootModuleList;

    public ModuleTree() {
        menuMapWithLongKey = new HashMap<>();
        menuMapWithStringKey = new HashMap<>();
        moduleMap = new HashMap<>();
        commandMap = new HashMap<>();
        rootMenuList = new ArrayList<>();
        rootModuleList = new ArrayList<>();
    }

    /**
     * 遍历菜单Map，根据parentId互设上下级，并将根菜单加入rootMenuList
     */
    public void buildMenuTree() {
        for (MenuInfo menuInfo: menuMapWithLongKey.values()) {
            if (menuInfo.getParentId() == 0L) {
                addMenuToRootList(menuInfo);
            } else {
                MenuInfo parentMenu = menuMapWithLongKey.get(menuInfo.getParentId());
                if (parentMenu != null) {
                    menuInfo.setSuperMenu(parentMenu);
                    parentMenu.addSubMenu(menuInfo);
                } else {
                    log.warn("Can not get parent menu for id:" + menuInfo.getParentId());
                }
            }
        }
    }

    public void sort() {
        sortMenu(rootMenuList);
        sortModule(rootModuleList);
    }

    private void sortMenu(List<MenuInfo> menuInfoList) {
        Collections.sort(menuInfoList);
        for (MenuInfo menuInfo: menuInfoList) {
            sortMenu(menuInfo.getSubMenuList());
            sortModule(menuInfo.getModuleList());
        }
    }

    private void sortModule(List<ModuleInfo> moduleInfoList) {
        Collections.sort(moduleInfoList);
        for (ModuleInfo moduleInfo: moduleInfoList) {
            Collections.sort(moduleInfo.getCommandList());
        }
    }

    public boolean containsMenu(Long id) {
        return menuMapWithLongKey.containsKey(id);
    }

    public void addMenuToMap(MenuInfo menuInfo) {
        menuMapWithLongKey.put(menuInfo.getId(), menuInfo);
        menuMapWithStringKey.put(menuInfo.getPkgName(), menuInfo);
    }

    public void addMenuToRootList(MenuInfo menuInfo) {
        rootMenuList.add(menuInfo);
    }

    public Set<Long> menuKeySet() {
        return menuMapWithLongKey.keySet();
    }

    public MenuInfo getMenu(Long id) {
        return menuMapWithLongKey.get(id);
    }

    public MenuInfo getMenu(String pkgName) {
        return menuMapWithStringKey.get(pkgName);
    }

    public boolean containsModule(Long id) {
        return moduleMap.containsKey(id);
    }

    public void addModuleToMap(ModuleInfo moduleInfo) {
        moduleMap.put(moduleInfo.getId(), moduleInfo);
    }

    public void addModuleToRootList(ModuleInfo moduleInfo) {
        rootModuleList.add(moduleInfo);
    }

    public void addCommandToMap(CommandInfo commandInfo) {
        commandMap.put(commandInfo.getPath(), commandInfo);
    }

    public Map<String, CommandInfo> getCommandMap() {
        return commandMap;
    }

    public CommandInfo getCommand(String path) {
        return commandMap.get(path);
    }

    public void setCommandMap(Map<String, CommandInfo> commandMap) {
        this.commandMap = commandMap;
    }

    public List<MenuInfo> getRootMenuList() {
        return rootMenuList;
    }

    public void setRootMenuList(List<MenuInfo> rootMenuList) {
        this.rootMenuList = rootMenuList;
    }

    public List<ModuleInfo> getRootModuleList() {
        return rootModuleList;
    }

    public void setRootModuleList(List<ModuleInfo> rootModuleList) {
        this.rootModuleList = rootModuleList;
    }

    public Map<Long, MenuInfo> getMenuMap() {
        return menuMapWithLongKey;
    }

    public void setMenuMap(Map<Long, MenuInfo> menuMap) {
        this.menuMapWithLongKey = menuMap;
    }
}
