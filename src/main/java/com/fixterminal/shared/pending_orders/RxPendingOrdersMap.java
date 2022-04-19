package com.fixterminal.shared.pending_orders;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RxPendingOrdersMap implements Map<String, RxPendingOrder> {
    @Override
    public int size() {
        return size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        return false;
    }

    @Override
    public RxPendingOrder get(Object o) {
        return get(o);
    }

    @Override
    public RxPendingOrder put(String s, RxPendingOrder rxPendingOrder) {
        put(s,rxPendingOrder);
        return rxPendingOrder;
    }

    @Override
    public RxPendingOrder remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends RxPendingOrder> map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<RxPendingOrder> values() {
        return null;
    }

    @Override
    public Set<Entry<String, RxPendingOrder>> entrySet() {
        return null;
    }
}
