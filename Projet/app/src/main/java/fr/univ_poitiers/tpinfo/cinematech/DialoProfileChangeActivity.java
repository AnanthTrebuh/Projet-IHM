package fr.univ_poitiers.tpinfo.cinematech;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DialoProfileChangeActivity extends Dialog {
    interface ChangeNameListener {
        void fullNameSelected(String fullName);
    }

    public Context context;
    private DialoProfileChangeActivity.ChangeNameListener listener;
    private ListView listView;
    private HashSet<String> names;

    public DialoProfileChangeActivity(Context context, DialoProfileChangeActivity.ChangeNameListener listener, HashSet<String> users) {
        super(context);
        this.context = context;
        this.listener = listener;
        this.names = new HashSet<String>(users);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_profile_change);

        this.listView = findViewById(R.id.ListViewProfiles);

        ArrayList<String> list = new ArrayList<String>(names);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_list_item_1, list);

        this.listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                listener.fullNameSelected(listView.getItemAtPosition(position).toString());
                dismiss();
            }
        });
    }


}