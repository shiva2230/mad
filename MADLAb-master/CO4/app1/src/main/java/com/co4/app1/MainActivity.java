package com.co4.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    private int[] imageIds = {R.drawable.img4, R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img2, R.drawable.img1, R.drawable.img4, R.drawable.img3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);

        ImageAdapter adapter = new ImageAdapter(this, imageIds);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((adapterView, view, position, id) -> showAlertDialog(position));
    }

    private void showAlertDialog(int selectedImage) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Image Selected")
                .setMessage("Image selected at position "+selectedImage)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}