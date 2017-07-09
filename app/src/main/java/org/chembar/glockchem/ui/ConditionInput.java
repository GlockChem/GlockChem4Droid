package org.chembar.glockchem.ui;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.Equation;

public class ConditionInput extends AppCompatActivity {
    Equation equInner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_input);

        // 获取传递过来的Equation对象
        Intent intent = getIntent();
        this.equInner = (Equation) intent.getSerializableExtra("equation");

        // 显示方程式给用户
        TextView textEquation = (TextView) findViewById(R.id.textEquation);
        textEquation.setText(getString(R.string.equation_hint).replace("[equation]", this.equInner.toString()));

        // 显示可用的条件种类
        Spinner spinnerConditionType = (Spinner) findViewById(R.id.spinnerConditionType);
        String[] stringsConditionType = {
                getString(R.string.condition_type_mass),    // 质量
                getString(R.string.condition_type_mole)     // 物质的量
        };
        ArrayAdapter<String> arrayAdapterConditionType = new ArrayAdapter<String>(this, R.layout.activity_condition_input, stringsConditionType);
        // FIXME: 绑定Adapter时自动退出
        spinnerConditionType.setAdapter(arrayAdapterConditionType);

        // TODO: Spinner显示反应物/生成物
    }
}
