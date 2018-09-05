package nuttapon.dots.co.th.dotssolutions;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DassBoardFragment extends Fragment {

    private MyConstant myConstant=new MyConstant();
    private String[] columnTcust=myConstant.getColumnTcust();  // ประกาศตัวแปลเพื่อรับค่าจาก JSON
    private String[]    valueUserStrings=new String[columnTcust.length];
    private String balanceAString;     // เพราะสิ่งที่ดึงมาจาก json เป็นตัวอักษร
    private String bananceBString="0";
    private String totalString;


    public DassBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        GetValue SharePreFerence

        getValueFromSharePreference();


//        Show Balance
        showBalance();



    }  // Main Method

    private void getValueFromSharePreference() {
        SharedPreferences sharedPreferences=getActivity()
                .getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String jsonUser = sharedPreferences.getString("User","");  // ถ้าดึงมาไม่ได้ให้เอาค่าว่างเปล่ามาแสดง

       try {
           JSONArray jsonArray=new JSONArray(jsonUser);
           JSONObject jsonObject=jsonArray.getJSONObject(0);
           for (int i=0; i<columnTcust.length; i+=1){
               valueUserStrings[i]=jsonObject.getString(columnTcust[i]);
               Log.d("5SepV1","valueUser[" + i + "] ==> " + valueUserStrings[i]);
           }

       }catch (Exception e) {
           e.printStackTrace();
       }
    }

    private void showBalance() {
        TextView companyATextView=getView().findViewById(R.id.txtBalanceA);
        TextView companyBTextView=getView().findViewById(R.id.txtBalanceB);
        TextView totalTextView=getView().findViewById(R.id.txtTotal);

        try {
        //สำหรับค้นหา balanceAString
            GetUserWhereIdCustomer getUserWhereIdCustomer=new GetUserWhereIdCustomer(getActivity());  //ทำการเชื่อมต่อท่อ
            getUserWhereIdCustomer.execute(valueUserStrings[0], myConstant.getUrlGetBalanceWhereCustIDAnIsCanael());

            String resultJSON =getUserWhereIdCustomer.get();
            Log.d("5SepV1", "resultJSON ==> " + resultJSON);

            JSONArray jsonArray=new JSONArray(resultJSON);
            Log.d("5SepV2", "จำนวนข้อมูล ==> " + jsonArray.length());   // นับจำนวนบันทัด

            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length()-1);

            balanceAString=jsonObject.getString("NetTotal");
            companyATextView.setText(balanceAString+" THB.");

            //สำหรับค้นหา balanceBString
            bananceBString="0";
            companyATextView.setText(balanceAString + " THB.");

            //สำหรับค้นหา Total
            int totalInt = Integer.parseInt(balanceAString.trim())+Integer.parseInt(bananceBString.trim());  // แปลง ตัวอักษรเป็นตัวเลข และตัดช่องว่างออก
            totalString=Integer.toString(totalInt);  // แปลงตัวเลขกลับไปเป็นตัวอักษรเพื่อแสดงใน Textview
            totalTextView.setText(totalString + " THB.");

        }catch (Exception e) {  // กัน Error

        e.printStackTrace();
        }

    }  //ShowBalance

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dass_board, container, false);
    }

}
