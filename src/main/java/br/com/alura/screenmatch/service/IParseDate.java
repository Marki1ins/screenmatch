package br.com.alura.screenmatch.service;

import java.util.List;

public interface IParseDate {
    <T> T getDatas(String address, Class<T> clazz);

    <T> List<T> getDatasList(String json, Class<T> clazz);
}
