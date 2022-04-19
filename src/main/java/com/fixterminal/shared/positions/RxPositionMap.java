
package com.fixterminal.shared.positions;

import lombok.Getter;

import java.util.*;

public class RxPositionMap implements Map<String, RxPosition> {

    int lastIdx = 0;

    @Getter
    private Map<String, RxPosition>  map = new HashMap<>();

    public List<RxPosition> getList(){
        List<RxPosition> positionList = new ArrayList<>();
        map.forEach((s, rxPosition) -> positionList.add(rxPosition));
        return positionList;
    }

    public RxPosition getFirst(){
      if (map.size() == 0){
         return null;
      }

      for (RxPosition pos : getList()){
        if (pos.getIdx() == 1){
          return pos;
        }
      }

      throw new RuntimeException(" BAD 1 IDX od position");
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return map.containsValue(o);
    }

    @Override
    public RxPosition get(Object o) {
        return map.get(o);
    }

    @Override
    public RxPosition put(String s, RxPosition rxPosition) {
        rxPosition.setIdx(++lastIdx);
        return map.put(s,rxPosition);
    }

    @Override
    public RxPosition remove(Object o) {
        return map.remove(o);
    }

    @Override
    public void putAll(Map<? extends String, ? extends RxPosition> _map) {
       map.putAll(_map);
    }

    @Override
    public void clear() {
       map.clear();
        lastIdx = 0;
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<RxPosition> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, RxPosition>> entrySet() {
        return map.entrySet();
    }
}
