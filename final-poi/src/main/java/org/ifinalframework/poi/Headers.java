package org.ifinalframework.poi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author likly
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public final class Headers {
    @Getter
    private final int row;
    private final Map<String,Integer> headerIndexMap = new LinkedHashMap<>();
    private final Map<Integer,String> indexHeaderMap = new LinkedHashMap<>();

    public void addHeader(Integer index,String header){
        headerIndexMap.put(header,index);
        indexHeaderMap.put(index,header);
    }

    public Integer getHeaderIndex(String header){
        return headerIndexMap.get(header);
    }

    public String getIndexHeader(Integer index){
        return indexHeaderMap.get(index);
    }

    public void foreach(BiConsumer<String,Integer> action){
        headerIndexMap.forEach(action);
    }
}
