package com.example.test.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.toolhelper.FileHelper;
import com.example.test.toolhelper.SDFileHelper;

import java.io.IOException;

public class FileReadWriteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtFileName;
    private EditText edtContent;
    private Button btnWrite;
    private Button btnRead;
    private FileHelper fileHelper;
    SDFileHelper sdFileHelper;
    private String fileName="test.txt";
    private String fileContent="";
    private Button btnWriteSD;
    private Button btnReadSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_read_write);

        fileHelper=new FileHelper(this);
        sdFileHelper=new SDFileHelper(this);
        edtFileName = ((EditText) findViewById(R.id.edtName));
        edtContent = ((EditText) findViewById(R.id.edtContent));
        btnWrite = ((Button) findViewById(R.id.btnWrite));
        btnRead = ((Button) findViewById(R.id.btnRead));
        btnWriteSD = ((Button) findViewById(R.id.btnWriteSD));
        btnReadSD = ((Button) findViewById(R.id.btnReadSD));
        btnRead.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnWriteSD.setOnClickListener(this);
        btnReadSD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWrite:
                fileName= edtFileName.getText().toString();
                fileContent = edtContent.getText().toString();
                try {
                    fileHelper.save(fileName,fileContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnRead:
                try {
                    String content = fileHelper.read(fileName);
                    Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case R.id.btnWriteSD:
                fileName= edtFileName.getText().toString();
                fileContent = edtContent.getText().toString();
                try {
                    sdFileHelper.savaFileToSD(fileName,fileContent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReadSD:
                try {
                    String content = sdFileHelper.readFromSD(fileName);
                    Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:break;
        }
    }
}
