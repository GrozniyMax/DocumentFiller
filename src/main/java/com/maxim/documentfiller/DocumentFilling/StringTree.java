package com.maxim.documentfiller.DocumentFilling;
import com.maxim.documentfiller.DocumentFilling.Exceptions.IncorrectFilePropertiesException;
import lombok.Setter;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class created to transfer filled file properties to JSON
 * @author Taranenko Maxim
 */
public class StringTree {

    private Map<String, Object> data = new HashMap<>();

    @Setter
    private String pathSeparator="\\.+";

    public void put(String path, String value) throws IncorrectFilePropertiesException {
        List<String> splittedPath = Arrays.stream(path.split(pathSeparator)).collect(Collectors.toCollection(ArrayList::new));
        String lastKey = splittedPath.get(splittedPath.size()-1);
        splittedPath.remove(splittedPath.size()-1);
        Map<String, Object> current = data;
        for (String key: splittedPath){
            if (current.containsKey(key)){
                if (!(current.get(key) instanceof Map)) throw new IncorrectFilePropertiesException(path,key);
                current = (Map<String, Object>) current.get(key);
            }else {
                current.put(key, new HashMap<String, Object>());
                current = (Map<String, Object>) current.get(key);
            }
        }
        current.put(lastKey,value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        mapToJsonLile(builder, data);
        return builder.toString();
    }


    public List<Pair<String,String>> toJSONStrings(){
        List<Pair<String,String>> output = new ArrayList<>();
        StringBuilder builder;
        String key;
        for (var entry: data.entrySet()){
            builder = new StringBuilder();
            key = entry.getKey();
            if (entry.getValue() instanceof String){
                builder.append('{').append('"').append(entry.getKey()).append('"').append(":")
                        .append('"').append(entry.getValue()).append('"').append('}');
            }else {
                builder.append('{').append('"').append(entry.getKey()).append('"').append(":");
                mapToJsonLile(builder, (Map<String, Object>) entry.getValue());
                builder.append('}');
            }
            output.add(new Pair<>(key, builder.toString()));
        }
        return output;
    }

    public void mapToJsonLile(StringBuilder builder, Map<String, Object> current){
        builder.append('{').append('\n');
        var currentList = new ArrayList<Map.Entry<String, Object>>(current.entrySet());
        for (int i = 0; i < currentList.size()-1; i++) {
            var entry = currentList.get(i);
            builder.append('"').append(entry.getKey()).append('"').append(":");
            if (entry.getValue() instanceof Map){
                mapToJsonLile(builder, (Map<String, Object>) entry.getValue());
            }else {
                builder.append('"').append(entry.getValue()).append('"');
            }
            builder.append(',');
        }
        var entry = currentList.get(currentList.size()-1);
        builder.append('"').append(entry.getKey()).append('"').append(":");
        if (entry.getValue() instanceof Map){
            mapToJsonLile(builder, (Map<String, Object>) entry.getValue());
        }else {
            builder.append('"').append(entry.getValue()).append('"');
        }
        builder.append("}");
    }
}
