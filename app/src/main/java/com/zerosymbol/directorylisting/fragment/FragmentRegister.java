package com.zerosymbol.directorylisting.fragment;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.AdapterCategoryList;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.FragmentRegisterBinding;
import com.zerosymbol.directorylisting.listner.IDialogEventListener;
import com.zerosymbol.directorylisting.listner.IDialogListClickListener;
import com.zerosymbol.directorylisting.listner.IDialogUserTypeListener;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.models.UserType;
import com.zerosymbol.directorylisting.models.UserTypeData;
import com.zerosymbol.directorylisting.service.ApiServices;
import com.zerosymbol.directorylisting.service.ApiUtils;
import com.zerosymbol.directorylisting.service.RequestFormater;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.AppValidate;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.NetworkUtil;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRegister extends BaseFragment {

    private FragmentRegisterBinding binding;
    private ApiServices mAPIService;
    private int genderPos = -1, docPos = -1, userTypePos = -1;
    private List<String> listGender, listDocument, listDocRegex;
    private List<UserType> listUserType;
    private String name, mobile, email, docNo, address = "NA", dob;
    private final int INT_DOB = 0;
    private boolean isTnC;
    private int Date_day, Date_month, Date_year, selectedDateOption;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        AppSingle.getInstance().getActivity().enableDrawer(false);
        AppSingle.getInstance().getActivity().setTitle("Register");
        mAPIService = ApiUtils.getAPIService("FragmentRegister");
        hitforusertype();
       /* binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etName.getText().toString().equalsIgnoreCase("")) {
                    if (!binding.etMobile.getText().toString().equalsIgnoreCase("")) {
                        //FlowOrganizer.getInstance().add(new FragmentRegOTP(), false);
                        hitforregistor();
                    } else {
                        Toast.makeText(AppSingle.getInstance().getActivity(), "Mobile Number field is empty", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AppSingle.getInstance().getActivity(), "Name field is empty", Toast.LENGTH_LONG).show();
                }
            }
        });*/
        binding.linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new FragmentLogin(), false);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        binding.btnSignup.setOnClickListener(listener);
        binding.selectDocument.setOnClickListener(listener);
        binding.selectUserType.setOnClickListener(listener);
        binding.selectDob.setOnClickListener(listener);
        binding.etGender.setOnClickListener(listener);
        binding.linkLogin.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.select_user_type:
                    if (listUserType != null || listUserType.size() > 0) {
                        openListDialogUserType("Select User Type", userTypePos, listUserType, new IDialogUserTypeListener() {
                            @Override
                            public void onClick(UserType data, int selectedPos) {
                                userTypePos = selectedPos;
                                binding.selectUserType.setText(data.getName());
                            }
                        });
                    } else {
                        showToast("User Type is empty");
                    }
                    break;
                case R.id.select_document:
                    openListDialog2("Select Document", docPos, listDocument, new IDialogListClickListener() {

                        @Override
                        public void onClick(String data, int selectedPos) {
                            binding.documentNumber.setVisibility(View.VISIBLE);
                            if (docPos != selectedPos) {
                                binding.documentNumber.setText("");
                                binding.documentNumber.setError(null);
                            }
                            docPos = selectedPos;
                            binding.selectDocument.setText(data);
                            setDocumentFormat();
                        }

                        @Override
                        public void onClick(int data, int selectedPos) {

                        }
                    });
                    break;
                case R.id.et_gender:
                    openListDialog2("Select Gender", genderPos, listGender, new IDialogListClickListener() {

                        @Override
                        public void onClick(String data, int selectedPos) {
                            genderPos = selectedPos;
                            binding.etGender.setText(data);
                        }

                        @Override
                        public void onClick(int data, int selectedPos) {

                        }
                    });
                    break;

                case R.id.select_dob:
                    showDatePicker(INT_DOB);
                    break;

                case R.id.link_login:
                    FlowOrganizer.getInstance().add(new FragmentLogin(), false);
                    break;

                case R.id.link_skip:
                    isTnC = !isTnC;
                    //binding.checkboxTnc.setChecked(isTnC);
                    break;
                case R.id.btn_signup:
                    callAsync();
                    break;
            }
        }
    };

    private void showDatePicker(int type) {
        try {
            selectedDateOption = type;
            resetDateToToday();
            getDateDialog(type).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetDateToToday() {
        final Calendar c1 = Calendar.getInstance();
        Date_year = c1.get(Calendar.YEAR);
        Date_month = c1.get(Calendar.MONTH);
        Date_day = c1.get(Calendar.DAY_OF_MONTH);
    }

    private DatePickerDialog getDateDialog(int type) {
        switch (type) {
            case INT_DOB:
                DatePickerDialog dialog = new DatePickerDialog(AppSingle.getInstance().getActivity(),
                        pickerListener, Date_year, Date_month, Date_day);
                try {
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 18 * 365 * 24 * 60 * 60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return dialog;
            default:
                return null;
        }
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

            Date_year = selectedYear;
            Date_month = selectedMonth;
            Date_day = selectedDay;
            String day1, month1;
            if (-1 < Date_month && Date_month < 9) {
                month1 = "0" + (Date_month + 1);
            } else {
                month1 = String.valueOf(Date_month + 1);
            }
            if (0 < Date_day && Date_day <= 9) {
                day1 = "0" + Date_day;
            } else {
                day1 = String.valueOf(Date_day);
            }
            String year1 = String.valueOf(Date_year);
            final Calendar c = Calendar.getInstance();
            String c_date = (new StringBuilder().append(c.get(Calendar.YEAR)).append("-").append(c.get(Calendar.MONTH) + 1).append("-").append(c.get(Calendar.DAY_OF_MONTH)).append(" ")).toString();
            try {
                StringBuffer Date = new StringBuffer(day1).append("-").append(month1).append("-").append(year1);//(day1+"-"+month1+"-"+year1).toString();

                switch (selectedDateOption) {
                    case INT_DOB:
                        String st = (new StringBuilder().append(Date_year).append("-").append(Date_month + 1).append("-").append(Date_day).append(" ")).toString();
                        java.util.Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(st);
                        if (AppValidate.dateComp(d2)) {
                            binding.selectDob.setText(st.trim());
                        } else {
                            Toast.makeText(AppSingle.getInstance().getActivity(), "Minimum 18 years old", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void hitforregistor() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            setProgressDialog("Registration is in progress..");
            int doc;
            if (docPos == -1) {
                doc = 0;
                docNo = "NA";
            } else
                doc = docPos + 1;
            mAPIService.request(RequestFormater.formatRegisterRequest("" + listUserType.get(userTypePos).getId(), name, mobile, email, "" + doc, docNo, dob, listGender.get(genderPos).substring(0, 1), "NA"), "0000000000",
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<ModelBase>() {
                        @Override
                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                            dismissProgressDialog();
                            ModelBase data = response.body();
                            try {
                                if (data == null) {
                                    showResponseDialog("Register", "No details found from server.");
                                } else {
                                    if (data.getResultCode().equalsIgnoreCase("0")) {
                                        FlowOrganizer.getInstance().popUpBackToMain();
                                        if (data.getStatus() != null) {
                                            if (data.getResultDescription() != null)
                                                showResponseDialog("Register", data.getResultDescription(), data.getTID());
                                            else {
                                                Log.e("Register :: ", data.getResultDescription());
                                                showResponseDialog("Register", data.getResultNamespace(),
                                                        data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                            }
                                        }
                                    } else {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Register", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Register", data.getResultNamespace(),
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Toast.makeText(AppSingle.getInstance().getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelBase> call, Throwable e) {
                            dismissProgressDialog();
                         /*   if (e instanceof SocketTimeoutException)
                                showResponseDialog("Other Bill Verify", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Other Bill Verify", SERVER_GOT_ERROR);
                            Log.e("onError", "yes ");*/
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }

    private void setDocumentFormat() {
        switch (docPos) {
            case 0:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                //InputFilter[] fArray = new InputFilter[1];
                //fArray[0] = new InputFilter.LengthFilter(10);
                //binding.edtTxtDocNo.setFilters(fArray);
                //binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz"));
                break;
            case 1:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                // InputFilter[] fArray2 = new InputFilter[1];
                //fArray2[0] = new InputFilter.LengthFilter(12);
                //binding.edtTxtDocNo.setFilters(fArray2);
                //binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                break;
            case 2:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                //InputFilter[] fArray3 = new InputFilter[1];
                // fArray3[0] = new InputFilter.LengthFilter(10);
                // binding.edtTxtDocNo.setFilters(fArray3);
                //binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz"));
                break;
            case 3:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                //InputFilter[] fArray4 = new InputFilter[1];
                //fArray4[0] = new InputFilter.LengthFilter(30);
                //binding.edtTxtDocNo.setFilters(fArray4);
                //binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz-/"));
                break;
            case 4:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                //InputFilter[] fArray5 = new InputFilter[1];
                //fArray5[0] = new InputFilter.LengthFilter(10);
                // binding.edtTxtDocNo.setFilters(fArray5);
                // binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz"));
                break;
            case 5:
                binding.documentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                //InputFilter[] fArray6 = new InputFilter[1];
                // fArray6[0] = new InputFilter.LengthFilter(30);
                // binding.edtTxtDocNo.setFilters(fArray6);
                //binding.edtTxtDocNo.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz-/"));
                break;
        }
    }

    private void initList() {
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        listDocument = new ArrayList<>();
        listDocument.add("PAN Card");
        listDocument.add("UID/Aadhaar Card");
        listDocument.add("Passport");
        listDocument.add("Driving License");
        listDocument.add("Voter ID Card");
        listDocument.add("Man Rega Card");
        listDocRegex = new ArrayList<>();
        listDocRegex.add("[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}");
        listDocRegex.add("[0-9]{12}");
        listDocRegex.add(".{8,10}");
        listDocRegex.add(".{10,30}");
        listDocRegex.add("[a-zA-Z0-9]{10}");
        listDocRegex.add(".{8,30}");
    }

    private void callAsync() {
        EditText edt_txt_name = binding.etName;
        EditText edt_txt_no = binding.etMobile;
        EditText edt_txt_email = binding.etEmail;
        EditText edt_txt_doc_no = binding.documentNumber;
        name = edt_txt_name.getText().toString();
        mobile = edt_txt_no.getText().toString();
        email = edt_txt_email.getText().toString();
        docNo = edt_txt_doc_no.getText().toString();
        dob = binding.selectDob.getText().toString();
        if (AppValidate.isValidString(name))
            if (AppValidate.isValidMobileNo(mobile))
                if (genderPos != -1)
                    if (AppValidate.isValidEmail(email))
                        if (docPos >= 0)
                            if (AppValidate.isToMatchRegEx(listDocRegex.get(docPos), docNo))
                                if (AppValidate.isValidString(dob)) {
                                    if (listUserType.size() > 0 || userTypePos != -1)
                                        hitforregistor();
                                    else
                                        showToast("User Type not selected");
                                } else
                                    showToast(SELECT_DOB);
                            else
                                showToast(ENTER_VALID + listDocument.get(docPos) + " Number");
                        else {
                            if (AppValidate.isValidString(dob))
                                hitforregistor();
                            else
                                showToast(SELECT_DOB);
                        }
                    else
                        showToast(ENTER_VALID_EMAIL);
                else
                    showToast(SELECT_GENDER);
            else
                showToast(VALID_10);
        else
            showToast(VALID_NAME);
    }

    public void hitforusertype() {
        if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
            setProgressDialog("Getting User Type..");
            mAPIService.request_user_type(RequestFormater.formatUserTypeRequest(), "0000000000",
                    AppSingle.getInstance().getAppVersion())
                    .enqueue(new Callback<UserTypeData>() {
                        @Override
                        public void onResponse(Call<UserTypeData> call, Response<UserTypeData> response) {
                            dismissProgressDialog();
                            UserTypeData data = response.body();
                            try {
                                if (data == null) {
                                    Log.e("onResponse :: ", "response is null");
                                    FlowOrganizer.getInstance().popUpBackToMain();
                                    Toast.makeText(AppSingle.getInstance().getActivity(), "response is null", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (data.getStatus().contains("error")) {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Home", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Home", "searchiigo",
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                        FlowOrganizer.getInstance().popUpBackToMain();
                                    } else if (data.getStatus().contains("success")) {
                                        listUserType = data.getData();
                                    } else {
                                        if (data.getResultDescription() != null)
                                            showResponseDialog("Home", data.getResultDescription(), data.getTID());
                                        else {
                                            showResponseDialog("Home", "searchiigo",
                                                    data.getResultCode(), data.getTID(), data.getResultDescription(), null);
                                        }
                                        FlowOrganizer.getInstance().popUpBackToMain();
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Exception in Login :: ", e.toString());
                                Toast.makeText(AppSingle.getInstance().getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserTypeData> call, Throwable e) {
                            dismissProgressDialog();
                            if (e instanceof SocketTimeoutException)
                                showResponseDialog("Home", SERVER_TIMEOUT);
                            else
                                showResponseDialog("Home", SERVER_GOT_ERROR);
                            FlowOrganizer.getInstance().popUpBackToMain();
                            Log.e("onError", "yes ");
                            e.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
        }
    }
}
