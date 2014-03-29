package com.git.programmerr47.vkazam.model.observers;

public interface IRecognizeStatusObservable {
	
	void addRecognizeStatusObserver(IRecognizeStatusObserver o);
	void removeRecognizeStatusObserver(IRecognizeStatusObserver o);
	void notifyRecognizeStatusObservers(String status);
}