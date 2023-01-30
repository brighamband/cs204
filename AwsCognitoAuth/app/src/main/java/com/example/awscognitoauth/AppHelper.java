package com.example.awscognitoauth;

import android.content.Context;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

/**
 * This class is to help set attributes for a user pool
 * and create an instance of CognitoUserPool
 */
public class AppHelper {

    private static AppHelper sharedInstance = null;

    private AppHelper() { }

    public static AppHelper getInstance() {
        if (sharedInstance == null)
            sharedInstance = new AppHelper();

        return sharedInstance;
    }

    private CognitoUserPool userPool;
    ClientConfiguration clientConfiguration = new ClientConfiguration();

    /**
     * Add your pool id here
     */
    //TODO: Set your pool id here (find this by going under general settings in your user pool)
    private final String userPoolId = System.getenv(USER_POOL_ID);

    /**
     * Add your app id here
     */
    //TODO: Set your client id here (find this by going under app clients in your user pool)
    private final String clientId = System.getenv(CLIENT_ID);

    /**
     * Add your client secret here
     */
    //TODO: Set your client secret here (find this by going under app clients and INTO show Details in your user pool)
    private final String clientSecret = System.getenv(CLIENT_SECRET);

    /**
     * Set Your User Pools region here
     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
     */
    //TODO: Set your cognito region here by looking at which AWS region your user pool is in and using that
    private final Regions cognitoRegion = Regions.US_EAST_2;


    public void init(Context context) {
        //TODO: Create user pool here
        userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, clientConfiguration, cognitoRegion);
    }

    public CognitoUserPool getUserPool() {
        return userPool;
    }


}
