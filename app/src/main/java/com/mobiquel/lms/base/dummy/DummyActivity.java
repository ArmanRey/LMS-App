package com.mobiquel.lms.base.dummy;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobiquel.lms.base.BaseActivity;

public class DummyActivity extends BaseActivity implements DummyView{

    private DummyPresenter mPresenter;


    @Override
    protected int getResourceId() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DummyPresenter(this);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void setListeners() {

    }
}
