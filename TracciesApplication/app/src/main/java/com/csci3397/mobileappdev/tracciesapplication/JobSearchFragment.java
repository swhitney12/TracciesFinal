package com.csci3397.mobileappdev.tracciesapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobSearchFragment extends Fragment {
    ListView jobListView;
    private ArrayList<Job> jobList = new ArrayList<Job>();
    ArrayList<String> fillJobTitle = new ArrayList<String>();
    ArrayList<String> fillCompanyName = new ArrayList<String>();
    ArrayList<String> fillLocations = new ArrayList<String>();
    String jobSearchOutput;
    ArrayList<String> fillImages = new ArrayList<String>();

    private LocationManager locationManager;
    private LocationListener locationListener;
    private String userLocation;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button button;

    public JobSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobSearchFragment newInstance(String param1, String param2) {
        JobSearchFragment fragment = new JobSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_search, container, false);
        jobListView = (ListView) view.findViewById(R.id.jobListView);

       /////////////////////////////////////// //DO A CATCH FOR IF INTERNET IS INACCESSIBLE //////////////////////////////////////////////////////////
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://jobs.github.com/positions.json?description=python&full_time=true")
                .method("GET", null)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //on internet error, show dialog
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
                    throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    jobSearchOutput = responseBody.string();

                    //creating jobs
                    String[] jobsStrings = jobSearchOutput.split("\\{\"id");


                    for (int i = 0; i < jobsStrings.length; i++) {
                        jobsStrings[i] = "\"id" + jobsStrings[i];
                    }

                    for (int i = 1; i < jobsStrings.length; i++) {
                        Job newJob;
                        String[] parseJobFields = jobsStrings[i].split("\",\"");

                        for (int j = 0; j < parseJobFields.length; j++) {
                            parseJobFields[j] = parseJobFields[j].substring(parseJobFields[j].indexOf("\":\"") + 3);

                        }

                        if (parseJobFields.length == 11) {
                            newJob = new Job(parseJobFields[0], parseJobFields[1], parseJobFields[2], parseJobFields[3], parseJobFields[4], parseJobFields[5], parseJobFields[6], parseJobFields[7], parseJobFields[8], parseJobFields[10]);
                        } else {
                            if (!parseJobFields[5].startsWith("https:")) {
                                newJob = new Job(parseJobFields[0], parseJobFields[1], parseJobFields[2], parseJobFields[3], parseJobFields[4], "no Site", parseJobFields[5], parseJobFields[6], parseJobFields[7], parseJobFields[8]);
                            } else {
                                newJob = new Job(parseJobFields[0], parseJobFields[1], parseJobFields[2], parseJobFields[3], parseJobFields[4], parseJobFields[5], parseJobFields[6], parseJobFields[7], parseJobFields[8], "NoImg");
                            }
                        }

                        jobList.add(newJob);
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable(){
                        @Override
                        public void run() {
                            /*LOCATION SERVICE*/
                            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);

                            }else{
                                Location loc = locationManager.getLastKnownLocation("gps");
                                userLocation = loc.getLongitude() + "," + loc.getLatitude();
                                jobList = sortJobsByLocations(jobList, loc.getLatitude(), loc.getLongitude());
                                for(int k = 0; k < jobList.size(); k++) {
                                    fillCompanyName.add(jobList.get(k).getCompanyTitle());
                                    fillJobTitle.add(jobList.get(k).getJobTitle());
                                    fillLocations.add(jobList.get(k).getLocation());
                                }
                            }
                        }
                    });



                    MyAdapter adapter = new MyAdapter(getActivity(), fillJobTitle, fillCompanyName, fillLocations, fillImages);

                    jobListView.post(new Runnable() {
                        public void run() {
                            jobListView.setAdapter(adapter);
                        }
                    });
                }
            }
        });

        jobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job selectedItem = jobList.get(position);

                Fragment fragment = new JobDetailsFragment();
                Bundle bundle = new Bundle();

                bundle.putString("job_Title", selectedItem.getJobTitle());
                bundle.putString("apply_Link", selectedItem.getUrl());
                bundle.putString("logo_Image", selectedItem.getCompanyLogo());
                bundle.putString("company_Name", selectedItem.getCompanyTitle());
                bundle.putString("job_Location", selectedItem.getLocation());
                bundle.putString("job_Description", selectedItem.getJobDescription());

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


    public ArrayList<Job> sortJobsByLocations(ArrayList<Job> list, final double myLatitude, final double myLongitude) {
        Comparator comp = new Comparator<Job>() {
            @Override
            public int compare(Job o, Job o2) {
                String location1Str = getLocationFromAddress(getActivity(), o.getLocation()).toString().replace("(", "").replace(")", "").replace("lat/lng: ", "");
                String location2Str = getLocationFromAddress(getActivity(), o2.getLocation()).toString().replace("(", "").replace(")", "").replace("lat/lng: ", "");
                double location1Lat = Double.parseDouble(location1Str.substring(0, location1Str.indexOf(",")));
                double location1Long = Double.parseDouble(location1Str.substring(location1Str.indexOf(",")+1, location1Str.length()));

                double location2Lat = Double.parseDouble(location2Str.substring(0, location2Str.indexOf(",")));
                double location2Long = Double.parseDouble(location2Str.substring(location2Str.indexOf(",")+1, location2Str.length()));


                Location loc1 = new Location(LocationManager.GPS_PROVIDER);
                loc1.setLatitude(location1Lat);
                loc1.setLongitude(location1Long);

                Location loc2 = new Location(LocationManager.GPS_PROVIDER);
                loc2.setLatitude(location2Lat);
                loc2.setLongitude(location2Long);

                float[] result1 = new float[3];
                Location.distanceBetween(myLatitude, myLongitude, loc1.getLatitude(), loc1.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, loc2.getLatitude(), loc2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };


        Collections.sort(list, comp);
        return list;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            if(!address.isEmpty()) {
                Address location = address.get(0);
                p1 = new LatLng(location.getLatitude(), location.getLongitude() );
            } else p1 = new LatLng(0,0);


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


    private double getDifference(double x1, double x2, double y1, double y2) {
        double diff = 0;

        diff = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

        return diff;
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> jTitle;
        ArrayList<String> jCompany;
        ArrayList<String> jLoc;

        MyAdapter(Context c, ArrayList<String> title, ArrayList<String> company, ArrayList<String> loc, ArrayList<String> img) {
            super(c, R.layout.job_row, R.id.JSearchJobTitle, title);
            this.context = c;
            this.jTitle = title;
            this.jCompany = company;
            this.jLoc =  loc;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.job_row, parent, false);
            ImageView images = row.findViewById(R.id.logoimage);
            TextView jobTitle = row.findViewById(R.id.JSearchJobTitle);
            TextView companyTitle = row.findViewById(R.id.JSearchCompanyName);
            TextView jobLoc = row.findViewById(R.id.JSearchLocation);

            jobTitle.setText(jTitle.get(position));
            companyTitle.setText(jCompany.get(position));
            jobLoc.setText(jLoc.get(position));

            return row;
        }
    }
}




