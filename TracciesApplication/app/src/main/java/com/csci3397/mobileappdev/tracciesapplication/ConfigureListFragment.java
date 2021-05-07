package com.csci3397.mobileappdev.tracciesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigureListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigureListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText listName;
    Button createListButton;
    dbhelper db;
    SharedPreferences preferences;

    private ArrayList<String> listnames = new ArrayList<String>();
    ListView listListView;



    public ConfigureListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigureListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigureListFragment newInstance(String param1, String param2) {
        ConfigureListFragment fragment = new ConfigureListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_configure_list, container, false);

        listName = (EditText) root.findViewById(R.id.text);
        createListButton = (Button) root.findViewById(R.id.createListButton);
        db = new dbhelper(getContext());


        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferences = getActivity().getSharedPreferences("Preferences", 0);

                String username = preferences.getString("username", null);
                String list = listName.getText().toString();

                Boolean createListCheck = db.createList(username, list);
                if(createListCheck == true) {

                    Toast.makeText(getContext(), "List Created", Toast.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    Fragment fragment = new ListListsFragment();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentnav, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Cursor res = db.getListNames(username);
                    while (res.moveToNext()){
                        System.out.println("Configure List: " + res.getString(0));
                    }
                } else {
                    Toast.makeText(getContext(), "List Creation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}
