package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

/**
 * PlaceSerializer
 * <p>
 */
class PlaceSerializer implements IPlaceSerializer {

    @Override
    public Place fromDAO(PlaceDTO source) {
        return new Place()
            .setId(source.getId())
            .setMainPicture(new Picture().setId(source.getId()))
            .setLatitude(source.getLatitude())
            .setLongitude(source.getLongitude())
            .setDescription(source.getDescription())
            .setVisitDate(new DateTime(source.getVisitDate()))
            .setName(source.getName())
            .setLiking(source.isLiking())
            .setLikes(source.getLikes())
            .setCountry(source.getCountry());
    }

    @Override
    public List<Place> fromDAO(List<PlaceDTO> source) {
        return Stream.of(source).map(this::fromDAO).collect(Collectors.toList());
    }

    @Override
    public PlaceDTO toDAO(Place source) {
        PlaceDTO outcome = new PlaceDTO();

        outcome.setId(source.getId());
        outcome.setVisitDate(source.getVisitDate().toDate());
        outcome.setLatitude(source.getLatitude());
        outcome.setLongitude(source.getLongitude());
        outcome.setDescription(source.getDescription());
        outcome.setMainPictureID(source.getMainPicture().getId());
        outcome.setCountry(source.getCountry());
        outcome.setName(source.getName());
        outcome.setLiking(source.isLiking());
        outcome.setLikes(source.getLikes());

        return outcome;
    }

    @Override
    public List<PlaceDTO> toDAO(List<Place> source) {
        return Stream.of(source).map(this::toDAO).collect(Collectors.toList());
    }
}
