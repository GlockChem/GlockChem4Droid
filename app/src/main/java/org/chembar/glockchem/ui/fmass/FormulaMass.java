package org.chembar.glockchem.ui.fmass;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.AdvNum;
import org.chembar.glockchem.core.Formula;
import org.chembar.glockchem.core.RMDatabase;

public class FormulaMass extends AppCompatActivity {
    // 分子量数据库
    RMDatabase rmDatabase = new RMDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_mass);

        // 返回按钮
        findViewById(R.id.buttonReturnToMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 复制输出按钮
        findViewById(R.id.buttonCopyResult).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                mClipboardManager.setText(((TextView) findViewById(R.id.textResult)).getText().toString());
                Toast.makeText(FormulaMass.this, getString(R.string.output_copied), Toast.LENGTH_SHORT).show();
            }
        });

        // 计算按钮
        findViewById(R.id.buttonCalcFormula).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取结果输出控件
                TextView textView = (TextView) findViewById(R.id.textResult);
                // 清空输出
                textView.setText(null);

                // 获取输入的化学式
                String strInput = ((EditText) findViewById(R.id.editFormulaText)).getText().toString();

                // 判断输入是否为空
                if (strInput.isEmpty()) {
                    // 为空则输出警告信息
                    Toast.makeText(FormulaMass.this, getString(R.string.err_empty_input), Toast.LENGTH_SHORT).show();
                    return;
                } else {    // 否则
                    try {   // 尝试分析化学式
                        // 建立对象
                        Formula formula = new Formula(strInput);
                        // 查询分子量
                        AdvNum molarMass = rmDatabase.queryMolarMass(formula);
                        // TODO: 数据显示
                        Toast.makeText(FormulaMass.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(FormulaMass.this, getString(R.string.formula_parse_failed), Toast.LENGTH_SHORT).show();
                        return;
                    }


                }
            }
        });


    }
}
