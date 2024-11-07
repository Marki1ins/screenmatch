package br.com.alura.screenmatch.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ParseData implements IParseDate {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getDatas(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> getDatasList(String json, Class<T> clazz) {
        CollectionType list = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

        try {
            return mapper.readValue(json, list);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
