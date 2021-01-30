package com.mapk.kmapper;

import com.mapk.common.sources.FiveArgsSrc;
import com.mapk.common.targets.FiveArgs;
import org.mapstruct.Mapper;

@Mapper
public interface MapStructMapper {
    FiveArgs map(FiveArgsSrc src);
}
