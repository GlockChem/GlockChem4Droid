package org.chembar.glockchem.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.chembar.glockchem.R;
import org.chembar.glockchem.core.AdvNum;
import org.chembar.glockchem.core.Equation;
import org.chembar.glockchem.core.EquationCalculator;
import org.chembar.glockchem.core.Formula;
import org.chembar.glockchem.core.Pair;
import org.chembar.glockchem.core.RMDatabase;

import java.text.DecimalFormat;

public class CalculationOutput extends Activity {
    Equation equInner;
    EquationCalculator.EquationCondition equationCondition;
    EditText editOutput;

    // 将AdvNum转为可读格式
    String parseAdvNum(AdvNum num) {
        DecimalFormat df = new DecimalFormat("#0.0000");

        String str = "";
        str += df.format(num.getNumInner());
        str += "-";
        str += df.format(num.getErrorMin());
        str += "+";
        str += df.format(num.getErrorMax());

        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_output);

        // 获取传递过来的Equation对象和计算条件
        {
            Intent intent = getIntent();
            this.equInner = (Equation) intent.getSerializableExtra("equation");
            this.equationCondition = (EquationCalculator.EquationCondition) intent.getSerializableExtra("condition");
        }

        // 取得editOutput对象
        editOutput = (EditText) findViewById(R.id.editOutput);

        // 进行方程式计算
        EquationCalculator equationCalculator;
        {
            try {
                equationCalculator = new EquationCalculator(equInner);
            } catch (Exception e) {
                // 不可能出现的，因为前面配平过了
                if (e.getMessage().equals("equcalc:cannot-balance")) {
                    editOutput.append(getString(R.string.balance_failed));
                    return;
                } else {
                    // 未知错误
                    editOutput.append(e.getMessage());
                    return;
                }
            }
        }

        // 输出计算结果
        {
            editOutput.append(getString(R.string.result_as_follows) + "\n");
            AdvNum numResult;

            try {
                for (Pair<Formula, Integer> pair : equInner.reactant) {
                    numResult = equationCalculator.calcMass(equationCondition, pair);
                    editOutput.append(pair.getL().getRawString() + ":\t" + parseAdvNum(numResult) + "\n");
                }
                editOutput.append("\n");
                for (Pair<Formula, Integer> pair : equInner.product) {
                    numResult = equationCalculator.calcMass(equationCondition, pair);
                    editOutput.append(pair.getL().getRawString() + ":\t" + parseAdvNum(numResult) + "\n");
                }

            } catch (RMDatabase.AtomNameNotFoundException e) {
                Toast.makeText(this, getString(R.string.unknown_atom) + e.getAtom(), Toast.LENGTH_SHORT).show();
            }
        }

        // 复制输出
        {
            Button buttonCopyOutput = (Button) findViewById(R.id.buttonCopyOutput);
            buttonCopyOutput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 使用低版本剪贴板API以保持兼容性
                    ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    mClipboardManager.setText(editOutput.getText());
                    Toast.makeText(CalculationOutput.this, getString(R.string.output_copied), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 新方程式
        {
            Button buttonReset = (Button) findViewById(R.id.buttonReset);
            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CalculationOutput.this, EquationInput.class);
                    startActivity(intent);
                    CalculationOutput.this.finish();
                }
            });
        }
    }

}
