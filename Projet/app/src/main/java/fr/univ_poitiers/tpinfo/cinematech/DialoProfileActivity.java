package fr.univ_poitiers.tpinfo.cinematech;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class DialoProfileActivity extends Dialog {
    interface FullNameListener {
        void fullNameEntered(String fullName);
    }
    public Context context;
    private DialoProfileActivity.FullNameListener listener;
    private EditText editTextFullName;
    private Button buttonOK;
    private Button buttonCancel;

    public DialoProfileActivity(Context context, DialoProfileActivity.FullNameListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_profile);

        this.editTextFullName = (EditText) findViewById(R.id.editTextTextPersonName);
        this.buttonOK = (Button) findViewById(R.id.ButtonOK);
        this.buttonCancel  = (Button) findViewById(R.id.ButtonCancel);

        this.buttonOK .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonOKClick()  {
        String fullName = this.editTextFullName.getText().toString();

        if(fullName== null || fullName.isEmpty())  {
            Toast.makeText(this.context, "Please enter a profile name", Toast.LENGTH_LONG).show();
            return;
        }
        this.dismiss(); // Close Dialog

        if(this.listener!= null)  {
            this.listener.fullNameEntered(fullName);
        }
    }

    // User click "Cancel" button.
    private void buttonCancelClick()  {
        this.dismiss();
    }

}
