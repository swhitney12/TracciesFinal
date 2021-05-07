package com.csci3397.mobileappdev.tracciesapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListJobsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListJobsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView listNameTextView;
    ListView jobListView;
    ArrayList<String> fillJobTitle = new ArrayList<String>();
    ArrayList<String> fillCompanyName = new ArrayList<String>();
    ArrayList<String> fillLocations = new ArrayList<String>();
    ArrayList<String> fillImages = new ArrayList<String>();
    dbhelper db;
    SharedPreferences preferences;
    String username;


    public ListJobsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListJobsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListJobsFragment newInstance(String param1, String param2) {
        ListJobsFragment fragment = new ListJobsFragment();
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

        View view = inflater.inflate(R.layout.fragment_list_jobs, container, false);
        listNameTextView = (TextView) view.findViewById(R.id.textView);
        jobListView = (ListView) view.findViewById(R.id.jobListView);

        Bundle bundle = this.getArguments();

        listNameTextView.setText(bundle.getString("ListName"));

        db = new dbhelper(getContext());

        preferences = getActivity().getSharedPreferences("Preferences", 0);
        username = preferences.getString("username", null);

        Cursor listIdRes = db.getJobListID(username, listNameTextView.getText().toString());
        Cursor joblistres = db.getJobListData(listIdRes.getString(0));

        while(joblistres.moveToNext()) {
            System.out.println(joblistres.getString(0));
            fillJobTitle.add(joblistres.getString(0));
            fillCompanyName.add(joblistres.getString(1));
            fillLocations.add(joblistres.getString(2));
        }

        MyAdapter adapter = new MyAdapter(getActivity(), fillJobTitle, fillCompanyName, fillLocations, fillImages);

        jobListView.setAdapter(adapter);

        jobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Cursor jobres = db.getJobFromList(listIdRes.getString(0), selectedItem);

                Fragment fragment = new JobListDetailsFragment();

                Bundle bundle = new Bundle();

                bundle.putString("job_Title", jobres.getString(0));
                bundle.putString("company_Name", jobres.getString(1));
                bundle.putString("job_Location", jobres.getString(2));
                bundle.putString("job_ApplyLink", jobres.getString(3));
                bundle.putString("job_Description", jobres.getString(4));

                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentnav, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> jTitle;
        ArrayList<String> jCompany;
        ArrayList<String> jLoc;

        MyAdapter(Context c, ArrayList<String> title, ArrayList<String> company, ArrayList<String> loc, ArrayList<String> img) {
            super(c, R.layout.joblist_row, R.id.JListJobTitle, title);
            this.context = c;
            this.jTitle = title;
            this.jCompany = company;
            this.jLoc =  loc;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.joblist_row, parent, false);
            ImageView images = row.findViewById(R.id.logoimagejoblist);
            TextView jobTitle = row.findViewById(R.id.JListJobTitle);
            TextView companyTitle = row.findViewById(R.id.JListCompanyName);
            TextView jobLoc = row.findViewById(R.id.JListLocation);

            jobTitle.setText(jTitle.get(position));
            companyTitle.setText(jCompany.get(position));
            jobLoc.setText(jLoc.get(position));

            return row;
        }
    }
}