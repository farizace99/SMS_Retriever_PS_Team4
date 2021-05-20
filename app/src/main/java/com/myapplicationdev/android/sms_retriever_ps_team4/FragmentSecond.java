package com.myapplicationdev.android.sms_retriever_ps_team4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSecond#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSecond extends Fragment {
    Button btnEmail, btnGetSMSFrag2;
    EditText etSMS2;
    TextView tvShowFrag2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSecond() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSecond.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSecond newInstance(String param1, String param2) {
        FragmentSecond fragment = new FragmentSecond();
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

    //Advanced Enhancement
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        etSMS2 = view.findViewById(R.id.etSMS2);
        tvShowFrag2 = view.findViewById(R.id.tvShowFrag2);
        btnGetSMSFrag2 = view.findViewById(R.id.btnGetSMSFrag2);
        btnEmail = view
                .findViewById(R.id.btnEmailSMSContent);
        btnGetSMSFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = tvShowFrag2.getText().toString() + "\n" + "New Data F2";
                tvShowFrag2.setText(data);
            }
        });

//        btnEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int permissionCheck = PermissionChecker.checkSelfPermission
//                        (FragmentSecond.this, Manifest.permission.READ_SMS);
//
//                if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(FragmentSecond.this,
//                            new String[]{Manifest.permission.READ_SMS}, 0);
//                    // stops the action from proceeding further as permission not
//                    //  granted yet
//                    return;
//                }
//            }
//        });

        return view;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//
//        switch (requestCode) {
//            case 0: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the read SMS
//                    //  as if the btnRetrieve is clicked
//                    btnEmail.performClick();
//
//                } else {
//                    // permission denied... notify user
//                    Toast.makeText(MainActivity.this, "Permission not granted",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//    }
}