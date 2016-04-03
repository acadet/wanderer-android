package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.dao.dto.PlaceDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

/**
 * PlaceSerializer
 * <p>
 */
class PlaceSerializer implements IPlaceSerializer {
    private IPictureSerializer pictureSerializer;

    PlaceSerializer(IPictureSerializer pictureSerializer) {
        this.pictureSerializer = pictureSerializer;
    }

    @Override
    public PlaceBLLDTO fromWandererServer(PlaceWandererServerDTO source) {
        return new PlaceBLLDTO()
            .setId(source.id)
            .setCountry(source.country)
            .setDescription(source.description)
            .setLatitude(source.latitude)
            .setLongitude(source.longitude)
            .setLikes(source.likes)
            .setName(source.name)
            .setVisitDate(new DateTime(source.visit_date))
            .setMainPicture(pictureSerializer.fromWandererServer(source.main_picture));
    }

    @Override
    public List<PlaceBLLDTO> fromWandererServer(List<PlaceWandererServerDTO> source) {
        return Stream.of(source).map(this::fromWandererServer).collect(Collectors.toList());
    }

    @Override
    public PlaceBLLDTO fromDAO(PlaceDAODTO source) {
        return new PlaceBLLDTO()
            .setId(source.getId())
            .setMainPicture(new PictureBLLDTO().setId(source.getId()))
            .setLatitude(source.getLatitude())
            .setLongitude(source.getLongitude())
            .setDescription(source.getDescription())
            .setVisitDate(new DateTime(source.getVisitDate()))
            .setName(source.getName())
            .setLikes(source.getLikes())
            .setCountry(source.getCountry());
    }

    @Override
    public List<PlaceBLLDTO> fromDAO(List<PlaceDAODTO> source) {
        return Stream.of(source).map(this::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PlaceDAODTO toDAO(PlaceBLLDTO source) {
        PlaceDAODTO outcome = new PlaceDAODTO();

        outcome.setId(source.getId());
        outcome.setVisitDate(source.getVisitDate().toDate());
        outcome.setLatitude(source.getLatitude());
        outcome.setLongitude(source.getLongitude());
        outcome.setDescription(source.getDescription());
        outcome.setMainPictureID(source.getMainPicture().getId());
        outcome.setCountry(source.getCountry());
        outcome.setName(source.getName());
        outcome.setLikes(source.getLikes());

        return outcome;
    }

    @Override
    public List<PlaceDAODTO> toDAO(List<PlaceBLLDTO> source) {
        return Stream.of(source).map(this::toDAO).collect(Collectors.toList());
    }
}
