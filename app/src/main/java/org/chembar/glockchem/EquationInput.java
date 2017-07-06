package org.chembar.glockchem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Toast.makeText(EquationInput.this, EquationInput.this.getString(R.string.clear_success), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
