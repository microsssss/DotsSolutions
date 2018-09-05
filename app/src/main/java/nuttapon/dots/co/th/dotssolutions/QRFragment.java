package nuttapon.dots.co.th.dotssolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import me.dm7.barcodescanner.zxing.ZXingScannerView.ResultHandler;

public class QRFragment extends Fragment implements ResultHandler{

    private ZXingScannerView zXingScannerView;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        zXingScannerView=new ZXingScannerView(getActivity());
        return zXingScannerView;
    }

    @Override
    public void onResume() {     // เป็นฟังชั้นที่ป้องกันการ Interrrub  พอโทรเสร็จ จะกลับมาเปิด โปปรแกรมใหม่
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {    // เป็นฟังชั้นที่ป้องกันการ Interrrub เวลาคนโทรมาจะ หยุด Program
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        String resultString = result.getText().toString().trim();
        Log.d("5SepV3","QR result ==> " + resultString);

        if(!resultString.isEmpty()){    // สิ่งที่อ่านได้เป็นว่างเปล่า F  ใส่ ! ทำให้สลับกลับเป็น True แล้วส่งค่าออกไป

            resultString= resultString.substring(17,28);  // ใช้ตัดคำตั้งแต่ตำแหน่งที่ 17-28

            Intent intent=new Intent(getActivity(), ServiceActivity.class);
            intent.putExtra("QRcode", resultString);
            getActivity().setResult(100, intent);
            getActivity().finish();

        }


    }
}
