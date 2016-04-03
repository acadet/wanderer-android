package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
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
    public PlaceBLLDTO serialize(PlaceWandererServerDTO source) {
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
    public List<PlaceBLLDTO> serialize(List<PlaceWandererServerDTO> source) {
        return Stream.of(source).map(this::serialize).collect(Collectors.toList());
    }
}
