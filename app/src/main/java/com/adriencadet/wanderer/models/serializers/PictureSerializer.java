package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

/**
 * PictureSerializer
 * <p>
 */
class PictureSerializer implements IPictureSerializer {
    @Override
    public PictureBLLDTO serialize(PictureWandererServerDTO source) {
        return new PictureBLLDTO()
            .setId(source.id)
            .setUrl(source.url);
    }

    @Override
    public List<PictureBLLDTO> serialize(List<PictureWandererServerDTO> source) {
        return Stream.of(source).map(this::serialize).collect(Collectors.toList());
    }
}
