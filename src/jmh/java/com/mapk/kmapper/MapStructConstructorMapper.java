package com.mapk.kmapper;

import com.mapk.common.sources.FiveArgsSrc;
import com.mapk.common.targets.FiveArgs2;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MapStructConstructorMapper {
    FiveArgs2 map(FiveArgsSrc src);
}
