package br.com.alura.screenmatch.service;

public interface IParseDate {
    <T> T getDatas(String address, Class<T> clazz);
}
