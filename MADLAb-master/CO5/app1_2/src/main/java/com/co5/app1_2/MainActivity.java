package com.co5.app1_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private DBHelper dbHelper;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;
    private TextView sts;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listview);
        sts = findViewById(R.id.status);

        fab.setOnClickListener( view -> {
            View customView = getLayoutInflater().inflate(R.layout.dialog_insert, null);

            new MaterialAlertDialogBuilder(this)
                    .setView(customView)
                    .setPositiveButton("Insert", (dialog, which) -> {
                        EditText etName = customView.findViewById(R.id.et_name);
                        if (!TextUtils.isEmpty(etName.getText().toString())) {
                            dbHelper.insertData(etName.getText().toString().trim());
                            dataList.add(etName.getText().toString().trim());
                            if (!dataList.isEmpty()) {
                                sts.setVisibility(View.GONE);
                            } else {
                                sts.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            etName.requestFocus();
                            etName.setError("Enter name");
                        }
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        if (!dbHelper.isTableEmpty()) {
            sts.setVisibility(View.GONE);
            displayData();
        } else {
            sts.setVisibility(View.VISIBLE);
        }

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            registerForContextMenu(listView);
            openContextMenu(listView);
            return true;
        });
    }

    private void displayData() {
        Cursor cursor = dbHelper.getAllData();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
                dataList.add(name);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_edit) {
            View customView = getLayoutInflater().inflate(R.layout.dialog_insert, null);
            EditText etName = customView.findViewById(R.id.et_name);
            etName.setText(dataList.get(selectedPosition));

            new MaterialAlertDialogBuilder(this)
                    .setView(customView)
                    .setPositiveButton("Update", (dialog, which) -> {
                        dbHelper.updateDataByName(dataList.get(selectedPosition), etName.getText().toString());
                        dataList.set(selectedPosition, etName.getText().toString());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        } else if (item.getItemId() == R.id.item_delete) {
            String deletedName = dataList.get(selectedPosition);
            dbHelper.deleteDataByName(deletedName);
            dataList.remove(selectedPosition);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();

            if (dataList.isEmpty()) {
                sts.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }
}