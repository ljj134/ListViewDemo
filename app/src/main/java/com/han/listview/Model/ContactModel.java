package com.han.listview.Model;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 联系人数据
 */
public class ContactModel extends ListItemModel {
    private String name;
    private String firstLetter;

    public ContactModel(String name) {
        this.name = name;
        this.firstLetter = getFirstLetter(name);
    }

    private String getFirstLetter(String name) {
        if (name == null || name.trim().isEmpty()) return "#";
        char c = name.charAt(0);
        if (c >= 'A' && c <= 'Z') return String.valueOf(c);
        if (c >= 'a' && c <= 'z') return String.valueOf((char) (c - 32));

        // 中文转拼音
        net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat format =
                new net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat();
        format.setCaseType(net.sourceforge.pinyin4j.format.HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(net.sourceforge.pinyin4j.format.HanyuPinyinToneType.WITHOUT_TONE);

        try {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
            if (pinyinArray != null && pinyinArray.length > 0) {
                return pinyinArray[0].substring(0, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "#";
    }

    public String getName() {
        return name;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    @Override
    public int getItemType() {
        return TYPE_CONTACT;
    }
}
