package edu.fpt.lenovo.shoponline.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.adapter.LoaispAdapter;
import edu.fpt.lenovo.shoponline.model.Loaisp;
import edu.fpt.lenovo.shoponline.ultil.CheckConnetion;
import edu.fpt.lenovo.shoponline.ultil.server;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhanhloaisp="";
    private void ViewFlipper()
    {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://fptshop.com.vn/Uploads/images/2015/SANPHAM/MinhtriBin/11_02_dell-vostro-v5568i5-7200u-25ghz/dell-vostro-v5568i5-7200u-25ghz1.jpg");
        mangloaisp.add(0,new Loaisp(0,"Trang chủ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSezDEUYGChOZMdZHGM3BSsEGqzGiSgoT0YNrnGrKRVmm6Qj5OC"));
        mangloaisp.add(1,new Loaisp(1,"Điện thoại","https://hc.com.vn/i/ecommerce/media/ckeditor_3370949.jpg"));
        mangloaisp.add(2,new Loaisp(2,"Máy tính","https://azaudio.vn/wp-content/uploads/2024/01/asus-rog-zephyrus-g14-2023-4.jpg"));
        for(int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setInAnimation(animation_out);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewlipper);

    }
    private void Anhxa()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper)findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh = (RecyclerView)findViewById(R.id.recycleview);
        navigationView = (NavigationView)findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView)findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();

        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);

        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Anhxa();
        if(CheckConnetion.haveNetworkConnection(getApplicationContext()))
        {
            ActionBar();
            ViewFlipper();
            GetDuLieuLoaisp();
            GetOnClickItemListview();
        }

    }

    void GetOnClickItemListview() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        if(CheckConnetion.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiMainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnetion.ShowToastLong(getApplicationContext(),"khong co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnetion.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiMainActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(position).getId());
                            startActivity(intent);
                        }
                        else {
                            CheckConnetion.ShowToastLong(getApplicationContext(),"khong co mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.menugiohang:
                //Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }






    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.actionbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
            }
        });
    }
///--------------------
private void GetDuLieuLoaisp()//đoc dữ liệu từ mạng
{
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    //JsonArrayRequest(duongDan,thanhcong,thatbai)
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.Duongdanloaisp, new Response.Listener<JSONArray>() {
       //neu thanh cong
        @Override
        public void onResponse(JSONArray response) {
            if(response!=null)//neu ket qua lay ve khac null
            {
                for(int i=0;i<response.length();i++)//dua vao vong lap
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);//lay doi tuong
                        id=jsonObject.getInt("id");//lay id
                        tenloaisp=jsonObject.getString("tenloaisp");//lay loai
                        hinhanhloaisp = jsonObject.getString("hinhanhloaisanpham");//lay hinh anh
                        mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));//dua vao mang
                        loaispAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mangloaisp.add(3,new Loaisp(0,"Sale","http://naturalmarketplace.net/wp-content/uploads/2018/05/flash_sale_banner_6822057.jpg"));
               mangloaisp.add(4,new Loaisp(1,"Liên hệ","https://www.air-it.co.uk/wp-content/uploads/2015/02/kpi-icons-01.png"));
                mangloaisp.add(5,new Loaisp(2,"Thông tin","http://www.mobilegiving.ca/wp-content/uploads/2015/06/icon_info_lg2.png"));
            }
        }

    }, new Response.ErrorListener() {
        //neu that bai
        @Override
        public void onErrorResponse(VolleyError error) {
            CheckConnetion.ShowToastLong(getApplicationContext(),error.toString());
        }
    });
    requestQueue.add(jsonArrayRequest);
}

    ///-----------------------


}