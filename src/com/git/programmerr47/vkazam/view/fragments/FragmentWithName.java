package com.git.programmerr47.vkazam.view.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.git.programmerr47.testhflbjcrhjggkth.R;

public class FragmentWithName extends Fragment {
	protected static final String ARG_NAME = "name_of_fragment";

	private String name;
	protected int icon = R.drawable.ic_action_mic;

	public String getFragmentName() {
		return name;
	}

	public Drawable getIcon() {
		return getActivity().getResources().getDrawable(icon);
	}

	protected void setFragmentName(String name) {
		this.name = name.toUpperCase();
	}

	protected void setFragmentIcon(int icon) {
		this.icon = icon;
	}

}
