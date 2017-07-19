package org.chembar.glockchem.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.chembar.glockchem.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    // NOTE: 这只是个makeshift
    private static final int MENU_EQUATION = 0;
    private static final int MENU_ABOUT = 1;
    private static final int MENU_GITHUB = 2;

    // TODO: 图文并茂处理
    private List<String> getListData() {
        List<String> list = new ArrayList<>();
        list.add("方程式配平与计算");
        list.add("关于本程序...");
        list.add("访问项目GitHub");

        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView listView = (ListView) findViewById(R.id.listViewMenu);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case MENU_EQUATION: {
                        Intent intent = new Intent(MenuActivity.this, EquationInput.class);
                        startActivity(intent);
                        break;
                    }
                    case MENU_ABOUT: {
                        Toast.makeText(MenuActivity.this, "GlockChem4Droid\n\nCredit to GlockChem Project Team:\n - DuckSoft\n - LionNatsu\n - EAirPeter\n - NAP64\n...and more", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case MENU_GITHUB: {
                        startActivity(new Intent()
                                .setAction("android.intent.action.VIEW")
                                .setData(Uri.parse("https://github.com/GlockChem/GlockChem4Droid")));
                        break;
                    }
                }
            }
        });
    }
}
