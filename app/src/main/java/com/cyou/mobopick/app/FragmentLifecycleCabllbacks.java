package com.cyou.mobopick.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author fei.cheng
 */
public interface FragmentLifecycleCabllbacks extends ActivityLifecycleCallbacks {

    public void onViewCreated(View view, Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState);

    public void onAttach(Activity activity);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void onDestroyView();

    public void onDetach();

    public void onSaveInstanceState(Bundle outState);

}
