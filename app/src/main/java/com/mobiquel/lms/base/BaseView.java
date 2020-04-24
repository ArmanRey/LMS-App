package com.mobiquel.lms.base;


import com.mobiquel.lms.pojo.FailureResponse;

/**
 * Created by Navjot Singh
 * on 2/3/19
 */

public interface BaseView {

    void showNoNetworkError();

    void showProgressBar();

    void hideProgressBar();

    boolean isNetworkAvailable();

    void showSnackBar(String message);

    void showSpecificError(FailureResponse failureResponse);

}