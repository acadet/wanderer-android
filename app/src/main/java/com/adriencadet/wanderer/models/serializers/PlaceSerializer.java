package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.beans.beans.Picture;
import com.adriencadet.wanderer.beans.beans.Place;
import com.adriencadet.wanderer.dao.dto.PlaceDAODTO;
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
    public Place fromWandererServer(PlaceWandererServerDTO source) {
        return new Place()
            .setId(source.id)
            .setCountry(source.country)
            .setDescription(source.description)
            .setLatitude(source.latitude)
            .setLongitude(source.longitude)
            .setLikes(source.likes)
            .setLiking(source.is_liking)
            .setName(source.name)
            .setVisitDate(new DateTime(source.visit_date))
            .setMainPicture(pictureSerializer.fromWandererServer(source.main_picture));
    }

    @Override
    public List<Place> fromWandererServer(List<PlaceWandererServerDTO> source) {
        return Stream.of(source).map(this::fromWandererServer).collect(Collectors.toList());
    }
}
