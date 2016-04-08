package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.beans.beans.Picture;
import com.adriencadet.wanderer.dao.dto.PictureDAODTO;
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
    public Picture fromWandererServer(PictureWandererServerDTO source) {
        return new Picture()
            .setId(source.id)
            .setUrl(source.url);
    }

    @Override
    public List<Picture> fromWandererServer(List<PictureWandererServerDTO> source) {
        return Stream.of(source).map(this::fromWandererServer).collect(Collectors.toList());
    }
}
