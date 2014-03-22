package com.git.programmerr47.vkazam.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.git.programmerr47.testhflbjcrhjggkth.R;
import com.git.programmerr47.vkazam.view.adapters.PleerListAdapter;

public class SongReplacePPFragment extends FragmentWithName {

	private ListView ppURLs;
	private PleerListAdapter adapter;
	private int position;

	public static SongReplacePPFragment newInstance(int position) {
		SongReplacePPFragment pageFragment = new SongReplacePPFragment();
		Bundle arguments = new Bundle();
		arguments.putInt("position", position);
		pageFragment.setArguments(arguments);
		pageFragment.setFragmentName("pleer");
		pageFragment.setFragmentIcon(R.drawable.ic_action_prostopleer);
		return pageFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt("position");
		adapter = new PleerListAdapter(this.getActivity(), position,
				R.layout.list_item_pp_url, R.layout.list_item_more_url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_history, null);

		ppURLs = (ListView) view.findViewById(R.id.listView);
		ppURLs.setAdapter(adapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		adapter.finish();
	}
}
