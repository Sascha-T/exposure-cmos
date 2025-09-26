package de.saschat.cmos.util;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void notifyObservers(T event) {
        for (Observer<T> observer : observers) {
            observer.notify(event);
        }
    }
}

