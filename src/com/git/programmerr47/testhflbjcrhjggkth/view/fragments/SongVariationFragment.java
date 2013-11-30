package com.git.programmerr47.testhflbjcrhjggkth.view.fragments;

import com.git.programmerr47.testhflbjcrhjggkth.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SongVariationFragment extends FragmentWithName{
	
	public static SongVariationFragment newInstance(String name, Context context, int resDraw) {
		SongVariationFragment pageFragment = new SongVariationFragment();
        Bundle arguments = new Bundle();
        pageFragment.setArguments(arguments);
        pageFragment.setFragmentName(name);
        pageFragment.setContext(context);
        pageFragment.setFragmentIcon(resDraw);
        return pageFragment;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment, null);
        return view;
    }
}
