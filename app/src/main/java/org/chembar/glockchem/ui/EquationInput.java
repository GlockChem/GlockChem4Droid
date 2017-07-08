package org.chembar.glockchem.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.Equation;

public class EquationInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation_input);

        Button btnClear = (Button)findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtEquation = (EditText)findViewById(R.id.editEquation);
                txtEquation.setText(null);
                Toast.makeText(EquationInput.this, getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
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
                    // 能走到这里，说明至少分析成功了
                    Toast.makeText(EquationInput.this, getString(R.string.parse_success), Toast.LENGTH_SHORT).show();
                    // TODO: 方程式条件的输入和处理
                } catch (Exception e) {
                    // 输出错误信息
                    Toast.makeText(EquationInput.this, getString(R.string.parse_error).replace("[errinfo]", e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
