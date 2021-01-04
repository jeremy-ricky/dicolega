package com.getsoft.dicolega;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DictionnairekFragment extends Fragment {

    // Declaration des objets
    private String value = "Samba balega !";
    private FragmentListener listener;
    ArrayAdapter<String> adapter;
    ListView dicList;
    private ArrayList<String> mSource = new ArrayList<>();

    public DictionnairekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionnairek, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dicList = view.findViewById(R.id.dictionnaryList);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mSource);
        dicList.setAdapter(adapter);
        dicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.onItemClick(mSource.get(position));
            }
        });
    }


    public void resetDataSource(ArrayList<String> source){
        mSource = source;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mSource);
        dicList.setAdapter(adapter);
    }


    public void filterValue(String value){
        //adapter.getFilter().filter(value);
        int size = adapter.getCount();
        for (int i = 0; i < size; i++ ){
            if (adapter.getItem(i).startsWith(value)){
                dicList.setSelection(i);
                break;
            }
        }
    }


    String[] getListOfWords(){
        String[] source = new String[]{

                "a"
                ,"abandon"
                ,"abandoned"
                ,"ability"
                ,"able"
                ,"about"
                ,"above"
                ,"abroad"
                ,"absence"
                ,"absent"
                ,"absolute"
                ,"absolutely"
                ,"ansorb"
                ,"abuse"
                ,"abise"
                ,"academique"
                ,"accent"
                ,"accept"
                ,"acceptable"
                ,"access"
                ,"accident"
                ,"accomodation"
                ,"accompany"

        };

        return source;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }

}