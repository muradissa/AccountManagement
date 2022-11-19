package com.example.AccountManagement;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AccountManagement.DBHolder.AppDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TransactionListFrag extends Fragment {

    float summ =0;
    TextView accountBalance;
    RecyclerView recycleView;
    private ArrayList<Transaction> transactionList;
    private RecyclerView.LayoutManager layoutManager;
    FloatingActionButton fbutton;
    public static TransactionListFragListener fragListner;

    public TransactionListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycleView = view.findViewById(R.id.recycleView);
        recycleView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        transactionList = new ArrayList<>();

        ItemsRecycleAdapter madapter = new ItemsRecycleAdapter(transactionList, getContext());

        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(madapter);

        //Calculate Balance ..
        for (Transaction item : madapter.transactionList) {
            summ+=Float.parseFloat(item.title);
        }
        accountBalance = view.findViewById(R.id.BalanceSum);
        accountBalance.setText("$ "+String.valueOf(summ) );

        fbutton = view.findViewById(R.id.add_btn);
        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    addtransactionfrag fragB = new addtransactionfrag(getContext());
                    MainActivity.getactiv().getSupportFragmentManager().beginTransaction().
                            add(R.id.fragContainer, fragB).//add on top of the static fragment
                            addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                            commit();
                    MainActivity.getactiv().getSupportFragmentManager().executePendingTransactions();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_list, container, false);
    }

   /**************************************** interface for talking with other fragments ****************************************/
    public interface TransactionListFragListener {
        public void DoInEditEvent(Transaction transaction);
       public void DoInViewEvent(Transaction transaction);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            this.fragListner = (TransactionListFragListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " + context.getClass().getName() + " must implements the interface 'FragAListener'");
        }
        super.onAttach(context);
    }
}