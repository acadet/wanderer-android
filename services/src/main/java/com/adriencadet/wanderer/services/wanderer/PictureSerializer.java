package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

/**
 * PictureSerializer
 * <p>
 */
class PictureSerializer implements IPictureSerializer {
    @Override
    public Picture fromWandererServer(PictureDTO source) {
        return new Picture()
            .setId(source.id)
            .setUrl(source.url);
    }

    @Override
    public List<Picture> fromWandererServer(List<PictureDTO> source) {
        return Stream.of(source).map(this::fromWandererServer).collect(Collectors.toList());
    }
}
