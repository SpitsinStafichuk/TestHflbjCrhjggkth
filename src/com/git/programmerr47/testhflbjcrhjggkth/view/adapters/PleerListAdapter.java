package com.git.programmerr47.testhflbjcrhjggkth.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.git.programmerr47.testhflbjcrhjggkth.R;
import com.git.programmerr47.testhflbjcrhjggkth.controllers.URLcontroller;
import com.git.programmerr47.testhflbjcrhjggkth.model.MicroScrobblerModel;
import com.git.programmerr47.testhflbjcrhjggkth.model.RecognizeServiceConnection;
import com.git.programmerr47.testhflbjcrhjggkth.model.SongData;
import com.git.programmerr47.testhflbjcrhjggkth.model.database.DatabaseSongData;
import com.git.programmerr47.testhflbjcrhjggkth.model.managers.SongManager;
import com.git.programmerr47.testhflbjcrhjggkth.model.pleer.api.Api;
import com.git.programmerr47.testhflbjcrhjggkth.model.pleer.api.Audio;
import com.git.programmerr47.testhflbjcrhjggkth.model.pleer.api.KException;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PleerListAdapter extends BaseAdapter {

    private MicroScrobblerModel model;
    private DatabaseSongData currentSongData;
    private View currentElement;
    private List<Audio> urls;
    private LayoutInflater inflater;
    private FragmentActivity activity;
    private int resLayout;
    private int endOfListResLayout;
    private int page = 1;
    private ProgressBar newSongLoadingBar;
    private boolean isFullList = false;
    private boolean isAllSongWithOriginArtistShown = false;
    private URLcontroller controller;

    public PleerListAdapter(final FragmentActivity activity, int resLayout, int endOfListResLayout) {
        this.activity = activity;
        this.resLayout = resLayout;
        this.endOfListResLayout = endOfListResLayout;
        controller = new URLcontroller(activity);
        model = RecognizeServiceConnection.getModel();
        currentSongData = model.getCurrentOpenSong();
        Log.v("PleerListAdapter", "Current Song url = " + currentSongData.getPleercomUrl());
        urls = new ArrayList<Audio>();
        updateSongsList();
        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return urls.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < urls.size())
            return urls.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        Log.v("PleerListAdapter", position + " - " + urls.size());
        if (position < urls.size()) {
            view = inflater.inflate(resLayout, parent, false);

            TextView textView;

            textView = (TextView) view.findViewById(R.id.ppUrlListItemArtist);
            textView.setText(urls.get(position).artist);

            textView = (TextView) view.findViewById(R.id.ppUrlListItemTitle);
            textView.setText(urls.get(position).title);

            textView = (TextView) view.findViewById(R.id.ppUrlListItemDuration);
            textView.setText(getStringTime(urls.get(position).duration));

            textView = (TextView) view.findViewById(R.id.ppUrlListItemBitRate);
            textView.setText(urls.get(position).bitrate);

            LinearLayout info = (LinearLayout) view.findViewById(R.id.ppUrlListItemInfo);
            LinearLayout numbers = (LinearLayout) view.findViewById(R.id.ppUrlListItemNumbers);
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.ppUrlListItemCheckbutton);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((currentSongData.getPleercomUrl() == null) || (!currentSongData.getPleercomUrl().equals(urls.get(position).url))) {
                        currentSongData.setPleercomUrl(urls.get(position).url);
                        currentSongData.setPpArtist(urls.get(position).artist);
                        currentSongData.setPpTitle(urls.get(position).title);
                        PleerListAdapter.this.notifyDataSetChanged();
                    }
                }
            };
            info.setOnClickListener(listener);
            numbers.setOnClickListener(listener);
            radioButton.setOnClickListener(listener);

            if ((currentSongData.getPleercomUrl() != null) && (currentSongData.getPleercomUrl().equals(urls.get(position).url))) {
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }
        } else {
            view = inflater.inflate(endOfListResLayout, parent, false);

            newSongLoadingBar = (ProgressBar) view.findViewById(R.id.moreListItemLoading);
            if (isFullList) {
                view.setVisibility(View.GONE);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page++;
                    updateSongsList();
                }
            });
        }

        if (position < urls.size()) {
            ImageButton playPause = (ImageButton) view.findViewById(R.id.ppUrlListItemPlayPauseButton);
            final View viewFinal = view;
            playPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	Log.v("PleerListAdapter", "View = " + v);
                    controller.setCurrentElement(viewFinal);
                    SongData tempInfo = new SongData(null, urls.get(position).artist, null, urls.get(position).title, null);
                    tempInfo.setPleercomUrl(urls.get(position).url);
                    controller.playPauseSong(new DatabaseSongData(position, null, tempInfo), -1, SongManager.PP_SONG);
                }
            });

            if (model.getSongManager().getSongData() != null) {
                SongData data = model.getSongManager().getSongData();
                if (data.getTrackId() == null) {
                    if ((data.getPleercomUrl() != null) &&
                        (data.getPleercomUrl().equals(urls.get(position).url)) &&
                        (model.getSongManager().isPlaying())) {
                        playPause.setImageResource(R.drawable.ic_media_pause);
                    } else {
                        playPause.setImageResource(R.drawable.ic_media_play);
                    }
                } else {
                    playPause.setImageResource(R.drawable.ic_media_play);
                }
            } else {
                playPause.setImageResource(R.drawable.ic_media_play);
            }
        }

        return view;
    }

    private synchronized void updateSongsList() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PleerListAdapter.this.newSongLoadingBar.setVisibility(View.VISIBLE);
                    }
                });
                try {
                    int listUpdate = urls.size();
                    if (!isAllSongWithOriginArtistShown) {
                        urls.addAll(Api.searchAudio(currentSongData.getArtist() + " " + currentSongData.getTitle(), 5, page));
                    } else {
                        if ((!currentSongData.getArtist().equals(currentSongData.getAlbumArtist())) &&
                            (currentSongData.getAlbumArtist() != null)) {
                            urls.addAll(Api.searchAudio(currentSongData.getAlbumArtist() + " " + currentSongData.getTitle(), 5, page));
                        }
                    }
                    Log.v("PleerListAdapter", "ANSWER FROM INTERNET");
                    final int listUpdateFinal = urls.size() - listUpdate;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("PleerListAdapter", "" + listUpdateFinal);
                            if (listUpdateFinal <= 0) {
                                if (!isAllSongWithOriginArtistShown) {
                                    isAllSongWithOriginArtistShown = true;
                                } else {
                                    isFullList = true;
                                    Toast.makeText(activity, activity.getString(R.string.no_more_songs), Toast.LENGTH_SHORT).show();
                                    Log.v("PleerListAdapter", "No more songs");
                                }
                            }
                            PleerListAdapter.this.notifyDataSetChanged();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (KException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PleerListAdapter.this.newSongLoadingBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        }).start();
    }

    private String getStringTime(long time) {
        String result = "" + time / 60 + ":";
        long seconds = time % 60;
        if (seconds < 10) {
            result += "0";
        }
        return result + seconds;
    }

    public void finish() {
        model.getSongManager().release();
        model.getSongManager().set(null, -1, model.getVkApi());
    }
}
