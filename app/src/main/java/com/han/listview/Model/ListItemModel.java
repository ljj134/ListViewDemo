package com.han.listview.Model;

/**列表项*/
// 数据项基类
public abstract class ListItemModel {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTACT = 1;

    public abstract int getItemType();
}
