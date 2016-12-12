package com.hm.project_glue.util.write;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.util.write.data.GroupListResults;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity implements WritePresenter.View  {
    Button btnWrite, btnWriteBack,btnGroupSelect;
    ImageButton btnGallery;
    EditText mEditText;
    HorizontalListView horizontalListView;
    ListView groupListView;
    View groupView;
    Context context;
    PhotosListAdapter listAdapter;
    PopupListAdapter popupAdapter;
    ArrayList<String> photosDatas;
    ArrayList<GroupListResults> groupListDatas;
    AlertDialog.Builder groupDialog =null;
    AlertDialog viewgroupDialog;
    int REQ_CODE_IMAGE = 1;
    WritePresenterImpl writePresenter;
    private String selectGroupId ="0";
    private String selectGroupName ="GROUP";
    public final static String TAG= "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        writePresenter = new WritePresenterImpl(this);
        writePresenter.setView(this);
        photosDatas = new ArrayList<>();
        groupListDatas = new ArrayList<>();
        this.context = this;
        btnWrite        =   (Button)findViewById(R.id.btnWrite);
        btnWriteBack    =   (Button)findViewById(R.id.btnWriteBack);
        btnGroupSelect  =   (Button)findViewById(R.id.btnGroupSelect);
        btnGallery      =   (ImageButton)findViewById(R.id.btnGallery);
        mEditText       =   (EditText) findViewById(R.id.mEditText);
        horizontalListView = (HorizontalListView) findViewById(R.id.horizontalListView);
        //TODO 완성되면 주석 풀기
//        selectGroupId = getGroupId();
//        selectGroupName = getGroupName();
        btnGallery.setOnClickListener(v ->{
            doTakeAlbum();
        });
        btnGroupSelect.setOnClickListener(v ->{
            groupView = getLayoutInflater().inflate(R.layout.popupgroup, null);
            groupListView = (ListView)groupView.findViewById(R.id.popupListView);
            groupListView.setAdapter(popupAdapter);
            groupDialog = new AlertDialog.Builder(this);
            groupDialog.setView(groupView);
            groupDialog.create();
            viewgroupDialog = groupDialog.show();
        });
        btnWrite.setOnClickListener(v -> {
            //TODO
            // 사진 체크, 값 보내기
            String content = mEditText.getText().toString();
            if(content.equals("")){
                Toast.makeText(this,"input content",Toast.LENGTH_SHORT).show();
            }else {
                writePresenter.httpPosting(photosDatas, selectGroupId, content);
            }
        });
        btnWriteBack.setOnClickListener(v -> {
            this.finish();
        });
        popupAdapter = new PopupListAdapter(this, groupListDatas);
        listAdapter = new PhotosListAdapter(
                this, photosDatas, R.layout.write_photos_list_item);
        horizontalListView.setAdapter(listAdapter);
    }
    public void doTakeAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, REQ_CODE_IMAGE);
    }
    public void checkPermissions(){
            //TODO
    }
    @Override // 이미지 결과 출력
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQ_CODE_IMAGE && data != null){
            Uri imageUri = data.getData();    // Intent에서 받아온 갤러리 URI
            String selections[] = { MediaStore.Images.Media.DATA}; // 실제 이미지 패스 데이터
            if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                Log.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
            }
            else {
                checkPermissions();
                Log.i(TAG, "Build.VERSION.SDK_INT > Build.VERSION_CODES.M");
            }
            Cursor cursor = getContentResolver().query(imageUri, selections, null,null,null);
            if(cursor.moveToNext()){
                String imagePath = cursor.getString(0);
                Log.i("imagePath : ", imagePath);
                photosDatas.add(imagePath);
            }
            if(photosDatas.size() > 0){
                horizontalListView.setVisibility(View.VISIBLE);
            }else{
                horizontalListView.setVisibility(View.GONE);
            }
            listAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void groupChanged(String groupId, String groupName) {
        btnGroupSelect.setText(groupName);
        selectGroupId = groupId;
        selectGroupName = groupName;
        viewgroupDialog.dismiss();
    }
    @Override
    public void setGroupListChanged(ArrayList<GroupListResults> results) {
        groupListDatas.addAll(results);
        popupAdapter.notifyDataSetChanged();
    }
    @Override
    public void writeResult(int code){
        Log.i(TAG, "writeResult:"+code);
        switch (code){
            case 200 :
                Toast.makeText(context,"Write Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 201 :
                Toast.makeText(context,"Write Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 400 :
                Toast.makeText(context,"Write Fail 400",Toast.LENGTH_LONG).show();
                break;
            case 403 :
                Toast.makeText(context,"page permission denied",Toast.LENGTH_LONG).show();
                break;
            case 404 :
                Toast.makeText(context,"page not found 404",Toast.LENGTH_LONG).show();
                break;
            case 500 :
                Toast.makeText(context,"Server Error 500",Toast.LENGTH_LONG).show();
                break;

            default: Toast.makeText(context,"Write Fail",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
