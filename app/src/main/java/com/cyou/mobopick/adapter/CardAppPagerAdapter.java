package com.cyou.mobopick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.fragment.CardFragment;
import com.cyou.mobopick.util.AppTheme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chengfei on 14-10-6.
 */
public class CardAppPagerAdapter extends FragmentStatePagerAdapter {

    private List<AppModel> appmodels;
    private List<Fragment> mFragments = new ArrayList();

    public CardAppPagerAdapter(FragmentManager paramFragmentManager, List<AppModel> paramList) {
        super(paramFragmentManager);
        this.appmodels = paramList;
//        generateFragment(paramList);
    }

    public void addAppModels(List<AppModel> paramList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((AppModel)localIterator.next()));
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(localArrayList);
        this.appmodels.addAll(paramList);
    }

    public List<AppModel> getAppModels() {
        return this.appmodels;
    }

    public int getCount() {
        return this.appmodels.size();
    }
//
//    public List<Fragment> getFragments() {
//        return this.mFragments;
//    }

    public Fragment getItem(int paramInt) {
        AppModel app = appmodels.get(paramInt);
        app.themeColor = AppTheme.getBgColor(paramInt);
        return CardFragment.getInstance(app);
    }


    public void setAppModels(List<AppModel> paramList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((AppModel)localIterator.next()));
        this.mFragments = localArrayList;
        this.appmodels = paramList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }
}
