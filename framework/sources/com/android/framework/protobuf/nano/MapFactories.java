package com.android.framework.protobuf.nano;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public final class MapFactories {
    private static volatile MapFactory mapFactory = new DefaultMapFactory();

    public interface MapFactory {
        <K, V> Map<K, V> forMap(Map<K, V> map);
    }

    static void setMapFactory(MapFactory newMapFactory) {
        mapFactory = newMapFactory;
    }

    public static MapFactory getMapFactory() {
        return mapFactory;
    }

    private static class DefaultMapFactory implements MapFactory {
        private DefaultMapFactory() {
        }

        @Override // com.android.framework.protobuf.nano.MapFactories.MapFactory
        public <K, V> Map<K, V> forMap(Map<K, V> oldMap) {
            if (oldMap == null) {
                return new HashMap();
            }
            return oldMap;
        }
    }

    private MapFactories() {
    }
}
