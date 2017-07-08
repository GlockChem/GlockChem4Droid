package org.chembar.glockchem.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chembar.glockchem.R;

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

                // TODO: 实现输入方程式的分析
            }
        });
    }
}
