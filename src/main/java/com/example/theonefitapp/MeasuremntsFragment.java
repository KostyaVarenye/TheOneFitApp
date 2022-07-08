package com.example.theonefitapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasuremntsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeasuremntsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MeasuremntsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeasuremntsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeasuremntsFragment newInstance(String param1, String param2) {
        MeasuremntsFragment fragment = new MeasuremntsFragment();
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
        View v = inflater.inflate(R.layout.fragment_measuremnts, container, false);
        //create order button logic
        Button btn_orderSupp = (Button) v.findViewById(R.id.btnOrderSupp);
        btn_orderSupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hang tight, the shop is about to open!", Toast.LENGTH_LONG).show();
            }
        });

        //set button logic for the bmi calculator
        Button btn_BMI = (Button) v.findViewById(R.id.btn_BMI);
        //creating an on click listener that redirects us to Bmi calculator activity
        btn_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalcBMI.class);
                startActivity(intent);
            }
        });
        return v;
    }
}