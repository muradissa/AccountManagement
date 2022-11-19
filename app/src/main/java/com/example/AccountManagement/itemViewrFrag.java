package com.example.AccountManagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class itemViewrFrag extends Fragment {
    Transaction selectedTransaction;
    TextView transactionviewr,desViewer;

    public void EditSelectedTask(Transaction transaction){
        selectedTransaction = transaction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transactionviewr = view.findViewById(R.id.amountviewr);
        desViewer = view.findViewById(R.id.desViewer);
        Edit_widget();
    }

    public void Edit_widget(){
        transactionviewr.setText(selectedTransaction.getTitle());
        desViewer.setText(selectedTransaction.getDescription());
    }
}