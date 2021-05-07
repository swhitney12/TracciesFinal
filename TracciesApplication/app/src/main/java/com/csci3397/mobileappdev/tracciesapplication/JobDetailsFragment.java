package com.csci3397.mobileappdev.tracciesapplication;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobDetailsFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Dialog saveToListDialog;
    Button showSaveListDialog;
    TextView jobTitle, companyName, jobLocation, jobDescription;
    ImageView companyLogo;
    dbhelper db;
    SharedPreferences preferences;
    String jobLink, username;
    private ArrayList<String> listnames = new ArrayList<String>();


    public JobDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobDetailsFragment newInstance(String param1, String param2) {
        JobDetailsFragment fragment = new JobDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);
        saveToListDialog = new Dialog(getContext());
        showSaveListDialog = view.findViewById(R.id.jobDetailsSaveBtn);

        jobTitle = view.findViewById(R.id.jobDetailsTitle);
        companyLogo = view.findViewById(R.id.jobDetailsLogo);
        companyName = view.findViewById(R.id.jobDetailsCompanyName);
        jobLocation = view.findViewById(R.id.jobDetailsLocation);
        jobDescription = view.findViewById(R.id.jobDetailsDesc);

        Bundle bundle = this.getArguments();


        jobTitle.setText(bundle.getString("job_Title"));
        jobLink = bundle.get("apply_Link").toString();
        companyName.setText(bundle.getString("company_Name"));
        jobLocation.setText(bundle.getString("job_Location"));
        jobDescription.setText(bundle.getString("job_Description"));


        db = new dbhelper(getContext());

        preferences = getActivity().getSharedPreferences("Preferences", 0);
        username = preferences.getString("username", null);

        Cursor res = db.getListNames(username);
        while (res.moveToNext()){
            listnames.add(res.getString(0));
        }



        showSaveListDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSavetoListPopup(v);
            }
        });

        return view;
    }

    public void showSavetoListPopup(View v) {
        ImageView btnClose;
        ListView popUpListView;

        saveToListDialog.setContentView(R.layout.savetolistpopup);

        btnClose = (ImageView) saveToListDialog.findViewById(R.id.popupclosebuttonimg);
        popUpListView = (ListView) saveToListDialog.findViewById(R.id.popuplists);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, listnames);
        popUpListView.setAdapter(adapter);

        btnClose.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToListDialog.dismiss();
            }
        });

        popUpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Cursor res = db.getJobListID(username, selectedItem);

                db.createJob(res.getString(0), jobTitle.getText().toString(), "Not Started",
                        jobLink, "", companyName.getText().toString(), jobLocation.getText().toString(), jobDescription.getText().toString()
                );
            }
        });
        saveToListDialog.show();
    }
}