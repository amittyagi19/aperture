package com.gspann.aperture.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import org.yaml.snakeyaml.Yaml;


public class YamlReader {
    public static void main(String[] args) throws IOException {

        Yaml yaml = new Yaml();
        try (InputStream in = Files.newInputStream(Paths.get("/Users/vinay.malik/eclipse-workspace/automation-msl/src/test/resources/data/Search/search.yaml"))) {

            TreeMap<String, Map<String, Object>> config = yaml.loadAs(in, TreeMap.class);
           // System.out.println(String.format("%s%n\nConverts to Properties:%n%n%s", config.toString(), toProperties(config)));
            System.out.println(toProperties(config));
        }catch(Exception e) {
        	System.out.println(e.getStackTrace());
        }
    }

    private static String toProperties(TreeMap<String, Map<String, Object>> config) {

        StringBuilder sb = new StringBuilder();

        for (String key : config.keySet()) {
        	//System.out.println(config.get(key).get);
            sb.append(convertToString(key, config.get(key)));
        }

        return sb.toString();
    }

    private static String convertToString(String key, Map<String, Object> map) {

        StringBuilder sb = new StringBuilder();

        for (String mapKey : map.keySet()) {

            if (map.get(mapKey) instanceof Map) {
                sb.append(convertToString(String.format("%s.%s", key, mapKey), (Map<String, Object>) map.get(mapKey)));
            } else {
                sb.append(String.format("%s.%s=%s%n", key, mapKey, map.get(mapKey).toString()));
            }
        }

        return sb.toString();
    }
}
