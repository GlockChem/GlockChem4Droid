package org.chembar.glockchem.ui.equcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.AdvNum;
import org.chembar.glockchem.core.Equation;
import org.chembar.glockchem.core.EquationCalculator;
import org.chembar.glockchem.core.Formula;
import org.chembar.glockchem.core.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class ConditionInput extends AppCompatActivity {
    private Equation equInner;
    private int nReactants = 0; // 用于反应物的总数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_input);

        // 获取传递过来的Equation对象
        {
            Intent intent = getIntent();
            this.equInner = (Equation) intent.getSerializableExtra("equation");
        }
        // 显示方程式给用户
        {
            TextView textEquation = (TextView) findViewById(R.id.textEquation);
            textEquation.setText(getString(R.string.equation_hint).replace("[equation]", this.equInner.toString()));
        }

        // 显示可用的条件种类
        {
            Spinner spinnerConditionType = (Spinner) findViewById(R.id.spinnerConditionType);
            String[] stringsConditionType = {
                    getString(R.string.condition_type_mass),    // 质量
                    getString(R.string.condition_type_mole)     // 物质的量
            };
            ArrayAdapter<String> arrayAdapterConditionType = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringsConditionType);
            spinnerConditionType.setAdapter(arrayAdapterConditionType);
            spinnerConditionType.setSelection(0);
        }

        // 生成并显示反应物/生成物列表
        {
            // 生成反应物/生成物列表
            ArrayList<String> arraySubstance = new ArrayList<>();

            for (Pair<Formula, Integer> pair : this.equInner.reactant) {
                arraySubstance.add(pair.getL().getRawString());
                nReactants++;
            }
            for (Pair<Formula, Integer> pair : this.equInner.product) {
                arraySubstance.add(pair.getL().getRawString());
            }

            // 显示反应物/生成物列表
            ArrayAdapter<String> arrayAdapterSubstance = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arraySubstance);
            Spinner spinnerSubstance = (Spinner) findViewById(R.id.spinnerSubstance);
            spinnerSubstance.setAdapter(arrayAdapterSubstance);
        }

        // 复制方程式按钮
        {
            Button buttonCopyEquation = (Button)findViewById(R.id.buttonCopyEquation);
            buttonCopyEquation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 使用低版本剪贴板API以保持兼容性
                    ClipboardManager mClipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    mClipboardManager.setText(equInner.toString());
                    Toast.makeText(ConditionInput.this, getString(R.string.equation_copied), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 继续按钮
        {
            Button buttonContinue = (Button) findViewById(R.id.buttonContinue);
            buttonContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 确定被选项
                    Pair<Formula, Integer> pairCondition;
                    {
                        Spinner spinnerSubstance = (Spinner) findViewById(R.id.spinnerSubstance);
                        int pos = spinnerSubstance.getSelectedItemPosition();
                        if (pos < nReactants) {
                            pairCondition = equInner.reactant.get(pos);
                        } else {
                            pairCondition = equInner.product.get(pos - nReactants);
                        }
                    }

                    // 获取输入数值
                    AdvNum numInput;
                    {
                        EditText editConditionValue = (EditText) findViewById(R.id.editConditionValue);
                        double numInputRaw = Double.parseDouble(editConditionValue.getText().toString());
                        if (numInputRaw < 1e-7 && numInputRaw > -1e-7) {
                            // 若为0则报错退出
                            Toast.makeText(ConditionInput.this, getString(R.string.condition_zero), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        numInput = new AdvNum(numInputRaw);
                    }

                    // 建立计算条件
                    EquationCalculator.EquationCondition equationCondition;
                    {
                        Spinner spinnerConditionType = (Spinner) findViewById(R.id.spinnerConditionType);

                        // 判定条件类型
                        if (spinnerConditionType.getSelectedItemPosition() == 0) {
                            // 质量
                            equationCondition = new EquationCalculator.EquationConditionMass(pairCondition, numInput);
                        } else {
                            // 摩尔
                            equationCondition = new EquationCalculator.EquationConditionMole(pairCondition, numInput);
                        }
                    }

                    // 调用计算输出屏幕
                    Intent intent = new Intent(ConditionInput.this, CalculationOutput.class);
                    intent.putExtra("equation", equInner);
                    intent.putExtra("condition", (Serializable) equationCondition);
                    startActivity(intent);
                }
            });
        }

        // 返回按钮
        {
            Button buttonReturn = (Button) findViewById(R.id.buttonReturn);
            buttonReturn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConditionInput.this.finish();
                }
            });
        }

    }
}
