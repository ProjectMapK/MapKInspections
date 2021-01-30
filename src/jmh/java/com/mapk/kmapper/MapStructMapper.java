package com.mapk.kmapper;

import com.mapk.common.sources.FiveArgsSrc;
import com.mapk.common.sources.TwentyArgsSrc;
import com.mapk.common.targets.FiveArgs;
import com.mapk.common.targets.TwentyArgs;
import org.mapstruct.Mapper;

@Mapper
public interface MapStructMapper {
    FiveArgs map(FiveArgsSrc src);

    TwentyArgs map(TwentyArgsSrc src);
}
