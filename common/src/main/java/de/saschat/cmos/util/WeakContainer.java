package de.saschat.cmos.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class WeakContainer<T> {
    List<WeakReference<T>> weaks = new ArrayList<>();

    private void sanityCheck() {
        Set<WeakReference<T>> set = new HashSet<>();
        for (WeakReference<T> item : weaks) {
            if(item.refersTo(null)) set.add(item);
        }
        for (WeakReference<T> x : set) {
            weaks.remove(x);
        }
    }

    public void add(T peripheral) {
        weaks.add(new WeakReference<>(peripheral));
        sanityCheck();
    }
    public Set<T> get() {
        sanityCheck();
        return new HashSet(weaks.stream().map(WeakReference::get).toList());
    }
    public void remove(T peripheral) {
        weaks.removeIf(a -> a.refersTo(peripheral));
    }

    public void operate(Consumer<T> operation) {
        for (T t : get()) {
            operation.accept(t);
        }
    }
}
