package com.csci3397.mobileappdev.tracciesapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobListDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobListDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView jobTitleTextView, companyNameTextView, jobLocationTextView, jobDescTextView;
    Button applyButton;



    public JobListDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobListDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobListDetailsFragment newInstance(String param1, String param2) {
        JobListDetailsFragment fragment = new JobListDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_list_details, container, false);

        jobTitleTextView = view.findViewById(R.id.jobListDetailsTitle);
        companyNameTextView = view.findViewById(R.id.jobListDetailsCompanyName);
        jobLocationTextView = view.findViewById(R.id.jobListDetailsLocation);
        jobDescTextView = view.findViewById(R.id.jobListDetailsDesc);

        applyButton = view.findViewById(R.id.jobListDetailsApplyBtn);

        Bundle bundle = this.getArguments();
        jobTitleTextView.setText(bundle.getString("job_Title"));
        companyNameTextView.setText(bundle.getString("company_Name"));
        jobLocationTextView.setText(bundle.getString("job_Location"));
        bundle.getString("job_ApplyLink");
        jobDescTextView.setText(bundle.getString("job_Description"));

        return view;
    }
}