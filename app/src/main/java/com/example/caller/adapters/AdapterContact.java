package com.example.caller.adapters;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.caller.R;
import com.example.caller.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.MyHolder>implements Filterable {
    private List<Contact> list;
    private List<Contact> listFull;

    public AdapterContact() {
    }

    @Override
    public Filter getFilter() {
        return listFilter ;
    }
    private Filter listFilter =new Filter () {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List <Contact> filterList=new ArrayList<> ();
            if (constraint==null||constraint.length ()==0){
                filterList.addAll (listFull);
            }
            else {
                String filterPattern =constraint.toString ().toLowerCase ().trim ();
                //toLowerCase>>يعني   M=m,A=a
                //search method
                for (Contact contact: listFull) {
                    if (contact.getName ().toLowerCase ().contains (filterPattern)
                            ||contact.getPhone ().toLowerCase ().contains (filterPattern)){
                        filterList.add (contact);
                    }

                }
            }
            FilterResults results =new FilterResults ();
            results.values=filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear ();
            list.addAll ((List)results.values);
            notifyDataSetChanged ();

        }
    };



    //onClick
    private OnItemClick onItemClick;
    public interface OnItemClick {
        void onClick(Contact contact);
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    private OnItemLongClick OnItemLongClick;




    public interface OnItemLongClick extends View.OnCreateContextMenuListener  {
        void onClick(int poss);
    }
    public void setOnItemLongClick(OnItemLongClick OnItemLongClick) {
        this.OnItemLongClick = OnItemLongClick;
    }

    public void setList(List<Contact> list) {
        this.list = list;
        this. listFull=new ArrayList<> (list);
        notifyDataSetChanged ();
    }




    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.contact_item, parent, false );
        return new MyHolder (view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView_name.setText ( list.get ( position ).getName () );
        holder.textView_phone.setText ( list.get ( position ).getPhone () );
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size ();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_name;
        TextView textView_phone;
        CardView cardView;


        MyHolder(@NonNull View itemView) {
            super (itemView);
            textView_name = itemView.findViewById (R.id.TV_name);
            textView_phone = itemView.findViewById (R.id.TV_phone);
            cardView=itemView.findViewById (R.id.mycard);
            cardView.setOnCreateContextMenuListener (this);

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    int index=getAdapterPosition ();
                    if (list != null && getAdapterPosition () != RecyclerView.NO_POSITION) {
                        onItemClick.onClick (list.get (index));
                    }


                }
            });

            itemView.setOnLongClickListener (new View.OnLongClickListener () {
                @Override
                public boolean onLongClick(View v) {
                    if (OnItemLongClick != null) {
                        OnItemLongClick.onClick (getAdapterPosition ());

                    }

                    return false;
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle ("select option");
            menu.add (this.getAdapterPosition (),1,0,"remove");
            menu.add (this.getAdapterPosition (),2,1,"info!");
            menu.add (this.getAdapterPosition (),3,2,"help!");
        }


    }


    public Contact getItemAt(int position){
        return list.get (position);
    }






}
