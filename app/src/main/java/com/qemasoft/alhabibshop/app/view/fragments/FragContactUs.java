package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.CONTACT_US_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.REGISTER_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;


/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragContactUs extends MyBaseFragment {
    
    private Button contactUsBtn;
    private EditText nameET, emailET, contactET, enquiryET;
    
    
    public FragContactUs() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_contact_us, container, false);
        initUtils();
        initViews(view);
        
        
        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                
                String nameVal = nameET.getText().toString().trim();
                String emailVal = emailET.getText().toString().trim();
                String contactVal = contactET.getText().toString().trim();
                String enquiryVal = enquiryET.getText().toString().trim();
                
                
                if (!nameVal.isEmpty() && !emailVal.isEmpty()
                        && !contactVal.isEmpty() && !enquiryVal.isEmpty()) {
                    utils.printLog("InsideLoginClicked = ", "Inside if");
                    
                    AppConstants.setMidFixApi("contact");
                    
                    Map<String, String> map = new HashMap<>();
                    
                    map.put("email", emailVal);
                    map.put("phone", contactVal);
                    map.put("enquiry", enquiryVal);
                    
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, CONTACT_US_REQUEST_CODE);
                    
                } else {
                    // set empty field error message
                    utils.showErrorDialog(findStringByName("empty_fields"));
                }
            }
        });
        
        return view;
    }
    
    private void initViews(View view) {
        contactUsBtn = view.findViewById(R.id.submit_contact_btn);
        nameET = view.findViewById(R.id.name_et);
        emailET = view.findViewById(R.id.email_et);
        contactET = view.findViewById(R.id.phone_et);
        enquiryET = view.findViewById(R.id.inquiry_et);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    
                    String msg = response.optString("message");
                    if (!msg.isEmpty())
                        utils.showAlertDialog(findStringByName("information_text"), msg);
                    utils.switchFragment(new MainFrag());
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCED_CANCEL) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("message");
                    if (!error.isEmpty()) {
                        utils.showErrorDialog(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog(findStringByName("error_fetching_data"));
            }
        }
    }
    
}