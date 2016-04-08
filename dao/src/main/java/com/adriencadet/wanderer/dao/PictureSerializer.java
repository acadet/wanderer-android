package com.adriencadet.wanderer.dao;

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
    public Picture fromDAO(PictureDTO source) {
        return new Picture()
            .setId(source.getId())
            .setUrl(source.getUrl());
    }

    @Override
    public List<Picture> fromDAO(List<PictureDTO> source) {
        return Stream.of(source).map(this::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PictureDTO toDAO(Picture source) {
        PictureDTO outcome = new PictureDTO();

        outcome.setId(source.getId());
        outcome.setUrl(source.getUrl());

        return outcome;
    }

    @Override
    public List<PictureDTO> toDAO(List<Picture> source) {
        return Stream.of(source).map(this::toDAO).collect(Collectors.toList());
    }

}
