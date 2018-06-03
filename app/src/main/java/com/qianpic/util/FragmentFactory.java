package com.qianpic.util;

import com.qianpic.BaseFragment;
import com.qianpic.fragment.StockingsFragment;
import com.qianpic.fragment.HighDefinitionFragment;
import com.qianpic.fragment.SexyFragment;
import com.qianpic.fragment.PureFragment;

import java.util.HashMap;

/**
 * Created by admin on 2018/4/1.
 */

public class FragmentFactory {
    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<Integer, BaseFragment>();

    public static BaseFragment createFragment(int pos) {
        BaseFragment baseFragment = mBaseFragments.get(pos);

        if (baseFragment == null) {
            switch (pos) {
                case 0:
                    baseFragment = new PureFragment();//青纯美女
                    break;
                case 1:
                    baseFragment = new HighDefinitionFragment();//高清美女
                    break;
                case 2:
                    baseFragment = new StockingsFragment();//丝袜美女
                    break;
                case 3:
                    baseFragment = new SexyFragment();//性感美女
                    break;

            }
            mBaseFragments.put(pos, baseFragment);
        }
        return baseFragment;
    }
}