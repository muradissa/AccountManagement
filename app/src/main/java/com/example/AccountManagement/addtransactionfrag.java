package com.example.AccountManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.AccountManagement.DBHolder.AppDB;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class addtransactionfrag extends Fragment {
    EditText AddTransaction, AddDescTransaction;
    Button addTransactionButton;
    Context conn;
    AppDB DataBase;
    public static addtransactionfrag addnotef;
    boolean timeselected , dateselected;

    public addtransactionfrag(Context con) {
        conn = con;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addnotef = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addtransactionfrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Refactor XML
        AddTransaction = view.findViewById(R.id.Addtransaction);
        AddDescTransaction = view.findViewById(R.id.AddDescreptionTransaction);
        addTransactionButton = view.findViewById(R.id.addTransactionButton);

        //time = view.findViewById(R.id.time);
        //date.setOnClickListener(new DateButtonClass());

        timeselected = false;
        dateselected = false;

        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount,desc;
                amount = AddTransaction.getText().toString();
                desc = AddDescTransaction.getText().toString();

                /** current time and date **/
                ZonedDateTime tt = ZonedDateTime.now();
                ZonedDateTime IsraelDateTime = tt.withZoneSameInstant(ZoneId.of("Asia/Jerusalem"));
                int hour = IsraelDateTime.getHour();
                int minute = IsraelDateTime.getMinute();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                String day=date.substring(0, 2) , mounth=date.substring(3, 5) , year=date.substring(6, 10);

                if(!amount.equals("")){
                    //Adding to SQL
                    DataBase = AppDB.getInstance(getContext());
                    Transaction newTransaction = new Transaction(
                            amount,
                            desc,
                            Integer.parseInt(day),
                            Integer.parseInt(mounth),
                            Integer.parseInt(year),
                            hour,
                            Calendar.getInstance().getTime().getMinutes());
                    DataBase.TransactionDB().insert(newTransaction);
                    ItemsRecycleAdapter.listner.transactionList.add(newTransaction);
                    ItemsRecycleAdapter.listner.notifyDataSetChanged();
                    /*
                    add toast
                     */
                } else {
                    Toast.makeText(getContext(), "All data is required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



/****************************************** inner class listner to date button ****************************************************/
//    class DateButtonClass implements View.OnClickListener{
//
//        @Override
//        public void onClick(View view) {
//            LocalDate lt = LocalDate.now();
//            int year = lt.getYear();
//            int month = lt.getMonthValue()-1;
//            int day = lt.getDayOfMonth();
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Dialog_MinWidth
//                    ,onDateSetListener,year,month,day);
//            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            datePickerDialog.show();
//        }
//    }
}