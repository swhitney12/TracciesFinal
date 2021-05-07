package com.csci3397.mobileappdev.tracciesapplication;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CompanyPageFragment extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";



        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;
        ImageView cImage;
        TextView cName, cDescription, cSize, cType, cIndustry, cBenefits, cInterviews, cRating, cLocation;

    public CompanyPageFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompanySearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static CompanySearchFragment newInstance(String param1, String param2) {
            CompanySearchFragment fragment = new CompanySearchFragment();
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
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_company_page, container, false);

            cName = (TextView) view.findViewById(R.id.companyDetailsTitle);
            cRating = (TextView) view.findViewById(R.id.companyDetailsRate);
            cLocation = (TextView) view.findViewById(R.id.companyDetailsLocation);
            cDescription = (TextView) view.findViewById(R.id.companyDetailsDesc);
            cType = (TextView) view.findViewById(R.id.companyDetailsTypeText);
            cIndustry = (TextView) view.findViewById(R.id.companyDetailsIndustryText);
            cSize = (TextView) view.findViewById(R.id.companyDetailsSizeText);
            cBenefits = (TextView) view.findViewById(R.id.companyDetailsBeneftsText);
            cInterviews = (TextView) view.findViewById(R.id.companyDetailsInterviewsText);
            cImage = (ImageView) view.findViewById(R.id.companyDetailsLogo);


            Bundle bundle = this.getArguments();
            String company = bundle.getString("company_name");
            String description =  bundle.getString("description");
            String size = bundle.getString("size");
            String type = bundle.getString("type");
            String industry = bundle.getString("industry");
            String benefits = bundle.getString("benefits");
            String interviews = bundle.getString("interviews");
            String rating = bundle.getString("rating");
            String location = bundle.getString("location");
            int image = bundle.getInt("image");

            cName.setText(company);
            cLocation.setText(location);
            cRating.setText(rating);
            cDescription.setText(description);
            cType.setText(type);
            cIndustry.setText(industry);
            cSize.setText(size);
            cInterviews.setText(interviews);
            cBenefits.setText(benefits);

            if(cName.getText().equals("Figma")) cImage.setImageResource(R.drawable.figmalogo);
            if(cName.getText().equals("Dribbble")) cImage.setImageResource(R.drawable.dribbblelogo);
            if(cName.getText().equals("Glassdoor")) cImage.setImageResource(R.drawable.glassdoorlogo);


            return view;
        }
}
