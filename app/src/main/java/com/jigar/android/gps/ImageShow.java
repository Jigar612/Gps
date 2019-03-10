package com.jigar.android.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImageShow extends AppCompatActivity  {

    Button btn_add_photo,btn_upload;
    RecyclerView recyclerView;
    File direct;
   private ArrayList<String> fileList = new ArrayList<String>();
    Adapter_images recyclerAdapter;


    public  static int CAMERA_PIC_REQUEST=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        direct = new File(getFilesDir() + "/CAT_IMG/");
        btn_add_photo = (Button)findViewById(R.id.btn_camera);
        btn_upload = (Button)findViewById(R.id.btn_upload);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Used for Open a camera.
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
                }
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File root = new File(direct.getAbsolutePath());
                File[] Files = root.listFiles();
                if(Files != null) {
                    int j;
                    for(j = 0; j < Files.length; j++) {
                        Files[j].delete();

                    }
                    fileList.clear();
                    recyclerAdapter.notifyDataSetChanged();

                }

            }
        });

        getfile(direct);

        recyclerView.addOnItemTouchListener(new MyRecyclerItemClickListener(getApplicationContext(), recyclerView, new MyRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ImageShow.this, "Click - Pos - "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {


                final AlertDialog alertDialog =new AlertDialog.Builder(ImageShow.this).create();
                alertDialog.setTitle("Are you want to delete this?");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("By deleting this, item will permanently be deleted. Are you still want to delete this?");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File fdelete = new File(fileList.get(position));
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {

                                Toast.makeText(ImageShow.this, "file is deleted", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(ImageShow.this, "file not deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                        fileList.remove(position);
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            }
        }));


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            createDirectoryAndSaveFile(photo);
            Log.d("URI", data.getExtras().get("data") + "");

        }
    }
    private void createDirectoryAndSaveFile(Bitmap imageToSave) {

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String fileName = "fav" + timeStamp + ".jpg";
        if (!direct.exists()) {
            // File wallpaperDirectory = new File("/CAT_IMG");
            direct.mkdir();
        }
        File file = new File(direct, fileName);
//        if (file.exists()) {
//            file.delete();
//        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getfile(direct);
    }
    public ArrayList<String> getfile(File dir) {
        fileList.clear();
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].getName().endsWith(".jpg"))

                {
                    //String stringFile = listFile[i].getName();
                    String file_path = listFile[i].toString();

                        fileList.add(file_path);
                }
            }
             recyclerAdapter = new Adapter_images(fileList,ImageShow.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recyclerAdapter); // set the Adapter to RecyclerView


        }
        return fileList;
    }
}
