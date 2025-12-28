package com.han.listview.Model;


/**列表分组标题*/
public class HeaderItemModel extends ListItemModel {
    private String letter;

    public HeaderItemModel(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }
}
