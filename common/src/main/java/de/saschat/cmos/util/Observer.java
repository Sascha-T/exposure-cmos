package de.saschat.cmos.util;

public interface Observer<T> {
    void notify(T value);
}
