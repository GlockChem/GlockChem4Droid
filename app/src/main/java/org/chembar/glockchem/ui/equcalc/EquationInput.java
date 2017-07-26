package org.chembar.glockchem.ui.equcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.Equation;
import org.chembar.glockchem.core.EquationBalancer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_input);

        Button btnClear = (Button) findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtEquation = (EditText) findViewById(R.id.editEquation);
                txtEquation.setText(null);
                Toast.makeText(EquationInput.this, getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
            }
        });

        ((EditText) findViewById(R.id.editEquation)).setFilters(new InputFilter[]{
                new InputFilter() {
                    // NOTE: 将来Equation更新时此处也需要更新
                    Pattern p = Pattern.compile("[^a-zA-Z0-9()\\-=>+*]");

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        Matcher m = p.matcher(source);
                        return m.replaceAll("");
                    }
                }
        });

        Button btnConfirm = (Button) findViewById(R.id.buttonConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取方程式字符串
                EditText txtEquation = (EditText) findViewById(R.id.editEquation);
                String strEquation = txtEquation.getText().toString();

                // 实现输入方程式的分析
                try {
                    // 尝试根据当前用户输入的方程式，创建一个新的Equation对象
                    Equation equation = new Equation(strEquation);
                    // 用高斯消元引擎进行配平
                    EquationBalancer equationBalancer = new EquationBalancer(equation);
                    if (equationBalancer.balanceGaussian()) {
                        // 配平成功，继续计算
                        Toast.makeText(EquationInput.this, getString(R.string.parse_success), Toast.LENGTH_SHORT).show();
                    } else {
                        // 配平失败，报错退出
                        Toast.makeText(EquationInput.this, getString(R.string.balance_failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 成功分析并配平

                    // 尝试启动
                    Intent intent = new Intent(EquationInput.this, ConditionInput.class);
                    intent.putExtra("equation", equation);
                    startActivity(intent);
                } catch (Exception e) {
                    String strErr = e.getMessage();
                    String strErrDisp;
                    // Equation异常信息的本地化
                    if (strErr.equals("equation:empty-input")) {
                        strErrDisp = getString(R.string.equation_empty_imput);
                    } else if (strErr.equals("equation:multi-part")) {
                        strErrDisp = getString(R.string.equation_multi_part);
                    } else if (strErr.equals("equation:empty-side")) {
                        strErrDisp = getString(R.string.equation_empty_side);
                    } else if (strErr.equals("equation:empty-first-item")) {
                        strErrDisp = getString(R.string.equation_empty_first_item);
                    } else if (strErr.equals("equation:empty-inner-item")) {
                        strErrDisp = getString(R.string.equation_empty_inner_item);
                    } else {
                        strErrDisp = strErr;
                    }
                    // 输出错误信息
                    Toast.makeText(EquationInput.this, getString(R.string.parse_error).replace("[errinfo]", strErrDisp), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
