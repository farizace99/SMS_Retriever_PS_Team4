package com.myapplicationdev.android.sms_retriever_ps_team4;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
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
                int permissionCheck = PermissionChecker.checkSelfPermission
                        ((MainActivity) getActivity(), Manifest.permission.READ_SMS);

                if (permissionCheck != PermissionChecker.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((MainActivity) getActivity(),
                            new String[]{Manifest.permission.READ_SMS}, 0);
                    //stops the action from proceeding further as permission not
                    //granted yet
                    return;
                }
                //Create all message URI
                Uri uri = Uri.parse("content://sms");
                String[] reqCols = new String[]{"date","address","body","type"};

                ContentResolver cr = getActivity().getContentResolver();

                //The filter String
                String filter = "body LIKE ?";
                //the matches for the ?
                String sms = etSMS2.getText().toString();
                String[] filterArgs = {"%" + sms + "%"};
                Cursor cursor = cr.query(uri, reqCols, filter, filterArgs, null);
                String smsBody = "";
                if (cursor.moveToFirst()) {
                    do {
                        long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat
                                .format("dd MM yyyy h:mm:ss aa",dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);
                        if (type.equalsIgnoreCase("1")){
                            type = "Inbox:";
                        } else{
                            type = "Sent:";
                        }
                        smsBody += type + " " + address + "\n at " + date
                                + "\n\"" + body + "\"\n\n";
                    } while (cursor.moveToNext());
                }
                tvShowFrag2.setText(smsBody);

            }
        });
        // Advanced enhancement
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://sms");
                String[] reqCols = new String[]{"date", "address", "body", "type"};
                ContentResolver cr = getActivity().getContentResolver();

                //The filter String

                //the matches for the ?
                String filter = "body LIKE ?";
               String[] split = etSMS2.getText().toString().split("");
               String[] ar = new String[split.length];
               ar[0] = "%"+split[0]+"%";
                String sms = etSMS2.getText().toString();
                String[] smsA = sms.split(" ");
                for (int i = 1; i < split.length; i++) {
                    filter += " OR body LIKE ?";
                    ar[i] = "%" + split[i] + "%";
                }

                Cursor cursor = cr.query(uri, reqCols, filter, ar, null);
                String smsBody = "";
                if (cursor.moveToFirst()) {
                    do {
                        long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat
                                .format("dd MM yyyy h:mm:ss aa", dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);

                        smsBody += address + "\n at " + date
                                + "\n\"" + body + "\"\n\n";
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"18016012@myrp.edu.ag"});
                        email.putExtra(Intent.EXTRA_SUBJECT,"Test Email from C347");
                        email.putExtra(Intent.EXTRA_TEXT,smsBody);
                        email.setType("message/rfc822");
                        startActivity(Intent.createChooser(email,"Choose an Email client :"));

                    } while (cursor.moveToNext());
                }

            }
        });
        return view;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the read SMS
                    //  as if the btnRetrieve is clicked
                    btnEmail.performClick();

                } else {
                    // permission denied... notify user
                    Toast.makeText((MainActivity) getActivity(), "Permission not granted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}