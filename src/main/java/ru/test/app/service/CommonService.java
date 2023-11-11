package ru.test.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Common service containing shared functionality.
 * Author: Viacheslav Petrenko
 */
@Service
public class CommonService {


    @Autowired
    private ModelMapper modelMapper;


    /**
     * Maps a list of entities to a list of DTOs.
     *
     * @param entityList The list of entities.
     * @param dtoClass   The DTO class.
     * @param <E>        The entity type.
     * @param <D>        The DTO type.
     * @return A list of DTOs.
     */
    public <E, D> List<D> mapList(List<E> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Maps an entity to a DTO.
     *
     * @param entity   The entity.
     * @param dtoClass The DTO class.
     * @param <E>      The entity type.
     * @param <D>      The DTO type.
     * @return The mapped DTO.
     */
    public <E, D> D map(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}

