package alura.paulo.literalura.service;

import java.util.List;

public interface InTratadorDeDados {

    <T> T convert(String json, Class<T> tClass);

    <T> List<T> convertList(String json, Class<T> tClass);
}