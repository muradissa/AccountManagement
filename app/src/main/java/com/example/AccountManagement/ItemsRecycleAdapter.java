package com.example.AccountManagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.AccountManagement.DBHolder.AppDB;

import java.util.ArrayList;
import java.util.List;

public class ItemsRecycleAdapter extends RecyclerView.Adapter<ItemsRecycleAdapter.MyViewHolder> {
    public ArrayList<Transaction> transactionList;
    TransactionListFrag.TransactionListFragListener fragListner1;
    Context con;
    AppDB database;
    AlertDialog dialog;

    public static ItemsRecycleAdapter listner;

    public ItemsRecycleAdapter(ArrayList<Transaction> transactionList, Context conn) {
        this.transactionList = transactionList;
        Log.d("transanction list", String.valueOf(this.transactionList.size()));
        fragListner1 = TransactionListFrag.fragListner;
        con = conn;
        database = AppDB.getInstance(con);
        listner = this;

        AppDB abc = AppDB.getInstance(con);
        List<Transaction> all = abc.TransactionDB().getAll();

        this.transactionList = new ArrayList<>();
        for(Transaction temp : all) {
            this.transactionList.add(temp);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder evh = new MyViewHolder(view, null);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.titleitem.setText(transaction.getTitle());
        holder.describeItem.setText(transaction.getDescription());
        holder.dateItem.setText(transaction.getDay()+"/"+ transaction.getMonth()+"/"+ transaction.getYear());
        if(transaction.getMinute()<10)
            holder.itemTime.setText(transaction.getHour()+":0"+ transaction.getMinute());
        else
            holder.itemTime.setText(transaction.getHour()+":"+ transaction.getMinute());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleitem, describeItem, dateItem,itemTime;
        Button itemEditButton;

        public MyViewHolder(@NonNull View itemView, Object o) {
            super(itemView);
            titleitem = itemView.findViewById(R.id.titleitem);
            describeItem = itemView.findViewById(R.id.describeItem);
            dateItem = itemView.findViewById(R.id.dateItem);
            itemEditButton = itemView.findViewById(R.id.itemEditButton);
            itemTime = itemView.findViewById(R.id.itemTime);

            itemEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    fragListner1.DoInEditEvent(transactionList.get(pos));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();

                    dialog = new AlertDialog.Builder(con)
                            .setTitle("WARNING")
                            .setMessage("Are you sure?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (pos != RecyclerView.NO_POSITION) {
                                        database.TransactionDB().delete(transactionList.get(pos));
                                        transactionList.remove(pos);
                                    }
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    fragListner1.DoInViewEvent(transactionList.get(pos));
                }
            });
        }
    }

    public void UpdateTaskList() {
        AppDB abc = AppDB.getInstance(con);
        List<Transaction> all = abc.TransactionDB().getAll();
        ItemsRecycleAdapter.listner.transactionList = new ArrayList<>();
        for(Transaction temp : all) {
            ItemsRecycleAdapter.listner.transactionList.add(temp);
        }
        ItemsRecycleAdapter.listner.notifyDataSetChanged();
    }

    public  void removeTask(Transaction transaction){
        for(int i = 0; i< transactionList.size() ; i++)
            if(transactionList.get(i).equals(transaction))
                transactionList.remove(i);
        notifyDataSetChanged();
    }

}
