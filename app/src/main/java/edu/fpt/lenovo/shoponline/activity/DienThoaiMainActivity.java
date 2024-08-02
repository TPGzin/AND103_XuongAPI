package edu.fpt.lenovo.shoponline.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.fpt.lenovo.shoponline.ultil.CheckConnetion;
import edu.fpt.lenovo.shoponline.ultil.server;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.adapter.DienthoaiAdapter;
import edu.fpt.lenovo.shoponline.model.Sanpham;

public class DienThoaiMainActivity extends AppCompatActivity {
    Toolbar  toolbar;
    ListView listView;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> mangdienthoai;
    int idDienThoai=0;
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();
        ActiontoolbarDienThoai();
        GetIDLoaiSanPham();
        GetData(page);
    }

     void GetData(int page) {
         RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
         String path = server.Duongdandienthoai+String.valueOf(page);
         StringRequest stringRequest= new StringRequest(Request.Method.POST, path,
                 new Response.Listener<String>() {
                     @Override
                     public void onResponse(String response) {
                         int id=0;
                         String Tendt="";
                         int Giatd=0;
                         String Hinhanhdt="";
                         String Mota="";
                         int IdsanphamDT=0;
                         if(response!=null){
                             try {
                                 JSONArray jsonArray= new JSONArray(response);
                                 for (int i=0;i<jsonArray.length();i++){
                                     JSONObject jsonObject=jsonArray.getJSONObject(i);
                                     id=jsonObject.getInt("id");
                                     Tendt=jsonObject.getString("tensp");
                                     Giatd=jsonObject.getInt("giasp");
                                     Hinhanhdt=jsonObject.getString("hinhanhsp");
                                     Mota=jsonObject.getString("motasp");
                                     IdsanphamDT=jsonObject.getInt("idsanpham");
                                     mangdienthoai.add(new Sanpham(id,Tendt,Giatd,Hinhanhdt,Mota,IdsanphamDT));
                                     dienthoaiAdapter.notifyDataSetChanged();
                                 }
                             } catch (JSONException e) {
                                 throw new RuntimeException(e);
                             }
                         }
                         else {
                             CheckConnetion.ShowToastLong(getApplicationContext(),"khong co mang");
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {


             }
         }){
             @Nullable
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 HashMap<String,String> param = new HashMap<>();
                 param.put("idsanpham",String.valueOf(idDienThoai));

                  return super.getParams();
             }
         };
         requestQueue.add(stringRequest);
    }

    ;

      void GetIDLoaiSanPham() {
        idDienThoai = getIntent().getIntExtra("idloaisanpham",-1);
    }

    void ActiontoolbarDienThoai() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void Anhxa(){
     toolbar=findViewById(R.id.toolbardienthoai);
     listView=findViewById(R.id.listviewdienthoai);
     mangdienthoai=new ArrayList<>();
     dienthoaiAdapter=new DienthoaiAdapter(mangdienthoai,getApplicationContext());
     listView.setAdapter(dienthoaiAdapter);
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

         }
     });
    }
}