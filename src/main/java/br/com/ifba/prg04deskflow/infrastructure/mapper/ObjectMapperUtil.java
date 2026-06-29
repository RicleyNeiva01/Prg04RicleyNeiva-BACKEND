package br.com.ifba.prg04deskflow.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

//Classe que faz a conversão
@RequiredArgsConstructor
@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {
        return MODEL_MAPPER.map(object, clazz);
    }
}
