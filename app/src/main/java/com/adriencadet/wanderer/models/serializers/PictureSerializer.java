package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.beans.Picture;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
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

    @Override
    public Picture fromDAO(PictureDAODTO source) {
        return new Picture()
            .setId(source.getId())
            .setUrl(source.getUrl());
    }

    @Override
    public List<Picture> fromDAO(List<PictureDAODTO> source) {
        return Stream.of(source).map(this::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PictureDAODTO toDAO(Picture source) {
        PictureDAODTO outcome = new PictureDAODTO();

        outcome.setId(source.getId());
        outcome.setUrl(source.getUrl());

        return outcome;
    }

    @Override
    public List<PictureDAODTO> toDAO(List<Picture> source) {
        return Stream.of(source).map(this::toDAO).collect(Collectors.toList());
    }
}
