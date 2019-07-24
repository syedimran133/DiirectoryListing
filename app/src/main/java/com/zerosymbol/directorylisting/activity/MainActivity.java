package com.zerosymbol.directorylisting.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zerosymbol.directorylisting.R;
import com.zerosymbol.directorylisting.adapter.DrawerListAdapter;
import com.zerosymbol.directorylisting.constants.AppConstants;
import com.zerosymbol.directorylisting.databinding.ActivityMainBinding;
import com.zerosymbol.directorylisting.fragment.*;
import com.zerosymbol.directorylisting.models.ModelBase;
import com.zerosymbol.directorylisting.service.ApiServices;
import com.zerosymbol.directorylisting.service.ApiUtils;
import com.zerosymbol.directorylisting.service.RequestFormater;
import com.zerosymbol.directorylisting.support.AppPrefrence;
import com.zerosymbol.directorylisting.support.AppSingle;
import com.zerosymbol.directorylisting.support.FlowOrganizer;
import com.zerosymbol.directorylisting.support.NetworkUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements AppConstants.IPrefConstant, AppConstants.IAppUrls {

    private ActivityMainBinding binding;
    private ArrayList<String> navigation_items, navigation_itemsprelogin;
    private DrawerListAdapter drawerListAdapter;
    private ListView lv_drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle = null;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    protected Location lastLocation;
    private AddressResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AppSingle.getInstance().initActivity(this);
        AppPrefrence.getInstance().initPrefrence(this);
        FlowOrganizer.getInstance().initParentFrame(binding.frameParent);
        if (AppPrefrence.getInstance().getiSLunagang()) {
            if (AppPrefrence.getInstance().isActivityExecuted()) {
                if (AppSingle.getInstance().isSuccessLogin()) {
                    if (!AppPrefrence.getInstance().getPin().equalsIgnoreCase("NA"))
                        FlowOrganizer.getInstance().add(new FragmentHome(), true);
                    else
                        FlowOrganizer.getInstance().add(new FragmentLogin(), false);
                } else {
                    FlowOrganizer.getInstance().add(new FragmentLogin(), false);
                }
            } else {
                FlowOrganizer.getInstance().add(new FragmentLogin(), false);
            }
        } else
            FlowOrganizer.getInstance().add(new FragmentLunagang(), false);
        checkAndRequestPermissions();
        setdata();
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int version = pInfo.versionCode;
            AppSingle.getInstance().setAppVersion(String.valueOf(version));
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableDrawer(true);
        binding.btnImgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawer();
            }
        });
        binding.mapIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().add(new FragmentAboutus(), false);
            }
        });
        binding.drawerLayout.setScrimColor(getResources().getColor(R.color.white_transparent));
        actionBarDrawerToggle = new ActionBarDrawerToggle(AppSingle.getInstance().getActivity(), binding.drawerLayout, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //binding.bottomMenu.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //binding.bottomMenu.setVisibility(View.GONE);
                //binding.txtUserBalance.setText(AppPrefrence.getInstance().getUserBalance());
            }
        };
        binding.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //mAPIService = ApiUtils.getAPIService("MainActivity");

        //checkUpdate();
    }

    /*public void setUserName() {
        binding.txtViewUser.setText(AppSingle.getInstance().getUserName());
        binding.txtUserBalance.setText(AppPrefrence.getInstance().getUserBalance());
    }*/

    public void setdata() {

        navigation_items = new ArrayList<>();
        lv_drawer = binding.lvDrawer;
        navigation_items.add("List Now");
        navigation_items.add("My Listings");
        navigation_items.add("Chat");
        navigation_items.add("Favourites");
        navigation_items.add("Payments & Billing");
        navigation_items.add("Contact us");
        navigation_items.add("Notifications");
        navigation_items.add("Blogs");
        navigation_items.add("Language-" + AppPrefrence.getInstance().getLunagang());
        navigation_items.add("Logout >");

    }

    public boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("TAG", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("TAG", "location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("TAG", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                            //permission is denied (and never ask again is  checked) shouldShowRequestPermissionRationale will return false
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    public static String pgencrypt(String plainText, String key) {
        try {
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT)));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getUserJson(String BillingAddress, String BillingCity, String BillingPostalCode, String BillingState, String BillingCountry, String IMEI, String IP) {
        return "{" +
                "\t\"BillingAddress\": \"" + BillingAddress + "\"," +
                "\t\"BillingCity\": \"" + BillingCity + "\"," +
                "\t\"BillingPostalCode\": \"" + BillingPostalCode + "\"," +
                "\t\"BillingState\": \"" + BillingState + "\"," +
                "\t\"BillingCountry\": \"" + BillingCountry + "\"," +
                "\t\"IMEI\": \"" + IMEI + "\"," +
                "\t\"IP\": \"" + IP + "\"" +
                "}";

    }

    public void setbackbutton(boolean enable) {
        /*if (enable) {
            binding.llback.setVisibility(View.GONE);
        } else {
            binding.llback.setVisibility(View.VISIBLE);
        }*/
    }

    public void enableDrawer(boolean enable) {
        topdrwable();
        if (enable) {
            UnHideIcon();
            binding.mainTop.setVisibility(View.VISIBLE);
            // binding.bottomMenu.setVisibility(View.VISIBLE);
            binding.btnImgMenu.setVisibility(View.VISIBLE);
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            HideIcon();
            binding.mainTop.setVisibility(View.GONE);
            //binding.bottomMenu.setVisibility(View.GONE);
            binding.btnImgMenu.setVisibility(View.GONE);
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void setTitle(String message) {
        binding.txtViewTitlet.setVisibility(View.VISIBLE);
        binding.txtViewSubtitle.setVisibility(View.GONE);
        binding.txtViewTitlet.setText(message);
    }

    public void HideIcon() {
        binding.mapIcons.setVisibility(View.GONE);
    }

    public void UnHideIcon() {

        binding.mapIcons.setVisibility(View.VISIBLE);
    }

    public void setTitleWSub(String title, String subtitle) {
        binding.txtViewTitlet.setVisibility(View.VISIBLE);
        binding.txtViewSubtitle.setVisibility(View.VISIBLE);
        binding.txtViewTitlet.setText(title);
        binding.txtViewSubtitle.setText(subtitle);
        //if()
    }

    public String getTitles() {
        return binding.txtViewTitlet.getText().toString().trim();
        //if()
    }

    public void hideBottom() {
        // binding.bottomMenu.setVisibility(View.GONE);
        //if()
    }

    public void showBottom() {
        //binding.bottomMenu.setVisibility(View.VISIBLE);
    }

    private void SetDrawer() {

        drawerListAdapter = new DrawerListAdapter(MainActivity.this, navigation_items);
        lv_drawer.setAdapter(drawerListAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlowOrganizer.getInstance().popUpBackToMain();
                switch (position) {

                    case 0:
                        FragmentList fragmentList1 = new FragmentList();
                        FlowOrganizer.getInstance().add(fragmentList1, true);
                        break;
                    case 1:
                        FlowOrganizer.getInstance().add(new FragmentList(), true);
                        break;
                    case 2:
                        FlowOrganizer.getInstance().add(new FragmentChat(), true);
                        //Toast.makeText(MainActivity.this, "For sending the money needs to complete KYC",Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        FlowOrganizer.getInstance().add(new FragmentFavourites(), true);
                        break;
                    case 4:
                        FlowOrganizer.getInstance().add(new FragmentPaymentsBilling(), true);
                        break;
                    case 5:
                        FlowOrganizer.getInstance().add(new FragmentContactUs(), true);
                        break;
                    case 6:
                        FlowOrganizer.getInstance().add(new FragmentNotifications(), true);
                        break;
                    case 7:
                        FlowOrganizer.getInstance().add(new FragmentAboutus(), true);
                        break;
                    case 8:
                        FlowOrganizer.getInstance().add(new FragmentLunagang(), false);
                        break;
                    case 9:
                        logout();
                        break;
                }
                toggleDrawer();
            }
        });
    }

    public void hideSoftKeyboard() {
        try {
            if (AppSingle.getInstance().getActivity().getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) AppSingle.getInstance().getActivity().getSystemService(AppSingle.getInstance().getActivity().INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(AppSingle.getInstance().getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void topdrwable() {
        setdata();
        SetDrawer();
        binding.linearUser.setVisibility(View.VISIBLE);
        binding.linearUserPrelogin.setVisibility(View.GONE);

    }

    public void toggleDrawer() {
        hideKeyboard();
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            //binding.bottomMenu.setVisibility(View.VISIBLE);
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            //binding.bottomMenu.setVisibility(View.GONE);
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public void setProgressDialog(final String message) {
        AppSingle.getInstance().setAllowBack(false);
        AppSingle.getInstance().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideSoftKeyboard();
                binding.txtTitle.setText(message);
                binding.linearProgressBar.setVisibility(View.VISIBLE);
                binding.avi.show();
            }
        });
    }

    public void dismissProgressDialog() {
        AppSingle.getInstance().setAllowBack(true);
        AppSingle.getInstance().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.linearProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hidebottom() {
        //binding.bottomMenu.setVisibility(View.GONE);
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
/*            addressOutput = resultData.getString(AppConstants.IPrefLoc.RESULT_DATA_KEY);
            if (addressOutput == null) {
                addressOutput = "";
            }
            displayAddressOutput();*/

            // Show a toast message if an address was found.
            if (resultCode == AppConstants.IPrefLoc.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
            }

        }
    }

    public void logout() {
        try {
            ApiServices mAPIService = ApiUtils.getAPIService("MainActivity");
            if (NetworkUtil.isConnectivityAvailable(AppSingle.getInstance())) {
                setProgressDialog("Loging Out..");
                mAPIService.requestwsession(RequestFormater.formatLogoutRequest(AppPrefrence.getInstance().getsessionId()), AppPrefrence.getInstance().getInitiator(), AppSingle.getInstance().getAppVersion(), AppPrefrence.getInstance().getsessionId())
                        .enqueue(new Callback<ModelBase>() {
                            @Override
                            public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                                dismissProgressDialog();
                                ModelBase data = response.body();
                                if (data == null) {
                                    Toast.makeText(MainActivity.this, "Error occured when logging out", Toast.LENGTH_SHORT).show();
                                } else {
                                    String code = data.getResultCode();
                                    if (code.equalsIgnoreCase("0")) {
                                        logoutapi();
                                    } else {
                                        //showResponseDialog("Balance", data.getResultNamespace(), code + "", data.getTID() + "", data.getResultDescription(),null);
                                        Toast.makeText(MainActivity.this, "Error occured when logging out", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelBase> call, Throwable t) {
                                dismissProgressDialog();
                                Toast.makeText(MainActivity.this, "Error occured when logging out", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(AppSingle.getInstance(), AppConstants.IMessages.NO_INTERNET, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logoutapi() {
        AppPrefrence.getInstance().clearPrefrence();
        FlowOrganizer.getInstance().clearBackStack();
        AppSingle.getInstance().setSuccessLogin(false);
        FlowOrganizer.getInstance().add(new FragmentLogin());
        //AppDatabase.getInMemoryDatabase(AppSingle.getInstance().getActivity()).billListModel().deleteAll();
    }
}
