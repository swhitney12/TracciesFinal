package com.csci3397.mobileappdev.tracciesapplication;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompanySearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompanySearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView companyListView;

    String fillCompanyName[] = {"Figma", "Glassdoor", "Dribbble"};
    String fillLocations[] = {"San Francisco, CA", "Mill Valley, CA", "Victoria, CA"};
    String jobSearchOutput;
    int fillImages[] = {R.drawable.figmalogo, R.drawable.glassdoorlogo, R.drawable.dribbblelogo};


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompanySearchFragment() {
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
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_company_search, container, false);

        companyListView = (ListView) view.findViewById(R.id.companylv);
        CompanySearchFragment.MyAdapter adapter = new MyAdapter(getActivity(),  fillCompanyName, fillLocations, fillImages);
        companyListView.setAdapter(adapter);

        Company Figma = new Company("Figma", "Figma is a design platform for teams who build products together." +
                " Born in the browser, Figma helps the entire product team create, test, and ship better designs, faster. " +
                "Whether you’re trying to consolidate tools, get more eyes on your work, or collaborate across time zones," +
                " Figma boosts creative productivity while keeping everyone on the same page.", "201 to 500", "\n" +
                "Company - Private", "Computer Hardware & Software", "401K \nHealth Insurance \nHSA \nLife Insurance",
                "One behavior interview.", "4.5 / 5.0", "San Francisco, CA", R.drawable.figmalogo);

        Company Glassdoor = new Company("Glassdoor", "We're on a mission to help people everywhere find a job and a " +
                "company they love. In the process, we're transforming an entire industry through the power of transparency. Join us!","501 to 1000",
                "Company - Private", "Internet", "401k \nMaternity & Paternity Leave \nHealth Insurance \nHSA \nLife Insurance",
                "On-site interview with coding questions.", "3.8 / 5.0", "Mill Valley, CA", R.drawable.glassdoorlogo);

        Company Dribbble = new Company("Dribbble", "We are a bootstrapped and profitable company helping the world’s design " +
                "talent share their creations and get hired. Dribbble has become a go-to resource for discovering and connecting with designers and creative talent around the globe" +
                "Dribbble helps some of the world’s best design-forward companies including Apple, Airbnb, IDEO, Facebook, Google, Dropbox, Slack, Shopify, Lyft get exposure for " +
                "their design teams and to help them hire expert creatives.","1 to 50",
                "Company - Private", "Staffing & Outsourcing", "Vacation \nPaid Holidays \nWork From Home \nHealth Insurance",
                "Three behavior interviews.", "4.5 / 5.0", "Victoria, CA", R.drawable.dribbblelogo);

        companyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                //System.out.println(selectedItem);

                Fragment fragment = new CompanyPageFragment();
                Bundle bundle = new Bundle();

                if(selectedItem == "Figma"){
                    bundle.putString("company_name", Figma.getCompanyTitle());
                    bundle.putString("description", Figma.getDescription());
                    bundle.putString("size", Figma.getSize());
                    bundle.putString("type", Figma.getType());
                    bundle.putString("industry", Figma.getIndustry());
                    bundle.putString("benefits", Figma.getBenefits());
                    bundle.putString("interviews", Figma.getInterviews());
                    bundle.putString("rating", Figma.getEmployeeRating());
                    bundle.putString("location", Figma.getLocation());
                    bundle.putInt("image", Figma.getImage());
                }

                if(selectedItem == "Glassdoor"){
                    bundle.putString("company_name", Glassdoor.getCompanyTitle());
                    bundle.putString("description", Glassdoor.getDescription());
                    bundle.putString("size", Glassdoor.getSize());
                    bundle.putString("type", Glassdoor.getType());
                    bundle.putString("industry", Glassdoor.getIndustry());
                    bundle.putString("benefits", Glassdoor.getBenefits());
                    bundle.putString("interviews", Glassdoor.getInterviews());
                    bundle.putString("rating", Glassdoor.getEmployeeRating());
                    bundle.putString("location", Glassdoor.getLocation());
                    bundle.putInt("image", Glassdoor.getImage());
                }

                if(selectedItem == "Dribbble"){
                    bundle.putString("company_name", Dribbble.getCompanyTitle());
                    bundle.putString("description", Dribbble.getDescription());
                    bundle.putString("size", Dribbble.getSize());
                    bundle.putString("type", Dribbble.getType());
                    bundle.putString("industry", Dribbble.getIndustry());
                    bundle.putString("benefits", Dribbble.getBenefits());
                    bundle.putString("interviews", Dribbble.getInterviews());
                    bundle.putString("rating", Dribbble.getEmployeeRating());
                    bundle.putString("location", Dribbble.getLocation());
                    bundle.putInt("image", Dribbble.getImage());
                }


                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentnav, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    class MyAdapter extends ArrayAdapter{
        Context context;
        String cCompany[];
        String cLoc[];
        int cImg[];
        int items[];

        MyAdapter(Context c, String company[], String loc[], int img[]) {
            super(c, R.layout.company_row, R.id.CSearchCompanyName, company);
            this.context = c;
            this.cCompany = company;
            this.cLoc =  loc;
            this.cImg = img;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.company_row, parent, false);
            ImageView images = row.findViewById(R.id.companylogoimage);
            TextView companyTitle = row.findViewById(R.id.CSearchCompanyName);
            TextView jobLoc = row.findViewById(R.id.CSearchCompanyLocation);

            images.setImageResource(cImg[position]);
            companyTitle.setText(cCompany[position]);
            jobLoc.setText(cLoc[position]);

            return row;
        }

    }
}