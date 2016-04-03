package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
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
    public PictureBLLDTO fromWandererServer(PictureWandererServerDTO source) {
        return new PictureBLLDTO()
            .setId(source.id)
            .setUrl(source.url);
    }

    @Override
    public List<PictureBLLDTO> fromWandererServer(List<PictureWandererServerDTO> source) {
        return Stream.of(source).map(this::fromWandererServer).collect(Collectors.toList());
    }

    @Override
    public PictureBLLDTO fromDAO(PictureDAODTO source) {
        return new PictureBLLDTO()
            .setId(source.getId())
            .setUrl(source.getUrl());
    }

    @Override
    public List<PictureBLLDTO> fromDAO(List<PictureDAODTO> source) {
        return Stream.of(source).map(this::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PictureDAODTO toDAO(PictureBLLDTO source) {
        PictureDAODTO outcome = new PictureDAODTO();

        outcome.setId(source.getId());
        outcome.setUrl(source.getUrl());

        return outcome;
    }

    @Override
    public List<PictureDAODTO> toDAO(List<PictureBLLDTO> source) {
        return Stream.of(source).map(this::toDAO).collect(Collectors.toList());
    }
}
