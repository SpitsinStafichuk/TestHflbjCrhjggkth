package com.github.spitsinstafichuk.vkazam.model.observers;

public interface ISongInfoObserverable {

    void addSongIngoObserver(ISongInfoObserver o);

    void removeSongIngoObserver(ISongInfoObserver o);

    void notifySongInfoObservers();
}
