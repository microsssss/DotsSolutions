package nuttapon.dots.co.th.dotssolutions;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DassBoardFragment extends Fragment {

    private MyConstant myConstant=new MyConstant();
    private String[] columnTcust=myConstant.getColumnTcust();  // ประกาศตัวแปลเพื่อรับค่าจาก JSON



    public DassBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Show Balance
        showBalance();



    }  // Main Method

    private void showBalance() {
        TextView companyATextView=getView().findViewById(R.id.txtBalanceA);
        TextView companyBTextView=getView().findViewById(R.id.txtBalanceB);
        TextView totalTextView=getView().findViewById(R.id.txtTotal);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dass_board, container, false);
    }

}
