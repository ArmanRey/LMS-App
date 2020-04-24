package com.mobiquel.lms.base;


import com.mobiquel.lms.pojo.FailureResponse;

/**
 * Created by Navjot Singh
 * on 2/3/19.
 */

public interface BaseModelListener {
    void noNetworkError();

    void onErrorOccurred(FailureResponse failureResponse);

}