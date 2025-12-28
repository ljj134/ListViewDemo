package com.han.listview;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.han.listview.Adapter.MyAdapter;
import com.han.listview.Model.ContactModel;
import com.han.listview.Model.HeaderItemModel;
import com.han.listview.Model.ListItemModel;
import com.han.listview.View.LetterSideBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private LetterSideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        sideBar = findViewById(R.id.sideBar);
        init();
    }


    private void init() {
        // 构建分组数据
        List<String> names = Arrays.asList("张三", "李四", "王五", "Apple", "Bob", "赵六",
                "韩正", "何佳", "刘丹", "马上", "麻烦",
                "构建", "侧边", "好的", "监听","控制");
        List<ContactModel> contacts = buildContactSortedList(names);
        List<ListItemModel> groupedItems = buildGroupedList(contacts);

        // 提取所有唯一的首字母（按顺序）
        LinkedHashSet<String> letterSet = new LinkedHashSet<>(); // 保持插入顺序 + 去重
        for (ContactModel item : contacts) {
            letterSet.add(item.getFirstLetter());
        }
        String[] sidebarLetters = letterSet.toArray(new String[0]);

        MyAdapter adapter = new MyAdapter(this, groupedItems);
        listView.setAdapter(adapter);

        sideBar.setLetters(sidebarLetters);

        // 侧边栏监听
        sideBar.setOnLetterChangeListener(letter -> {
            for (int i = 0; i < groupedItems.size(); i++) {
                ListItemModel item = groupedItems.get(i);
                if (item instanceof HeaderItemModel) {
                    if (((HeaderItemModel) item).getLetter().equals(letter)) {
                        listView.setSelection(i);
                        break;
                    }
                }
            }
        });
    }

    private List<ContactModel> buildContactSortedList(List<String> rawNames) {
        // 1. 转为 ContactItem 并排序
        List<ContactModel> contacts = new ArrayList<>();
        for (String name : rawNames) {
            contacts.add(new ContactModel(name));
        }
        contacts.sort(Comparator.comparing(ContactModel::getFirstLetter));
        return contacts;
    }


    private List<ListItemModel> buildGroupedList(List<ContactModel> contacts) {

        // 2. 按首字母分组，插入 HeaderItem
        List<ListItemModel> result = new ArrayList<>();
        String lastLetter = "";
        for (ContactModel item : contacts) {
            String letter = item.getFirstLetter();
            if (!letter.equals(lastLetter)) {
                result.add(new HeaderItemModel(letter));
                lastLetter = letter;
            }
            result.add(item);
        }

        return result;
    }
}