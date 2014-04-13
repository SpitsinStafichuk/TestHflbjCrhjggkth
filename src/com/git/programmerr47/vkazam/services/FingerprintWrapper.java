package com.git.programmerr47.vkazam.services;

import com.git.programmerr47.vkazam.model.FingerprintData;

/**
 * Wrapper that contains fingerprint and
 * client that want to recieve information
 * about it.
 *
 * @author Michael Spitsin
 * @since 2014-03-12
 */
public class FingerprintWrapper {

    public static final int RECOGNIZE_PRIORITY_HIGH = 1;
    public static final int RECOGNIZE_PRIORITY_LOW = 0;

    private FingerprintData fingerprint;
    private OnStatusChangedListener listener;

//    priority of recognizing fingerprint. If there is queue on recognizing
//    low priority finger will be added to the end of queue
//    and hight priority finger will be added to the beginning of queue
    private int fingerprintPriority;

    public FingerprintWrapper(FingerprintData fingerprint, OnStatusChangedListener listener) {
        if (fingerprint == null) {
            throw new NullPointerException();
        }

        this.fingerprint = fingerprint;
        this.listener = listener;
    }

    public OnStatusChangedListener getFingerprintListener() {
        return listener;
    }

    public FingerprintData getFingerprintData() {
        return fingerprint;
    }

    public int getPriority() {
        return fingerprintPriority;
    }
}
