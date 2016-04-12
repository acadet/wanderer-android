package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * PlaceSerializerTest
 * <p>
 */
public class PlaceSerializerTest {
    IPlaceSerializer   placeSerializer;
    IPictureSerializer pictureSerializer;

    @Before
    public void setup() {
        pictureSerializer = mock(PictureSerializer.class);
        placeSerializer = new PlaceSerializer(pictureSerializer);
    }

    @After
    public void tearDown() {
        placeSerializer = null;
        pictureSerializer = null;
    }


    @Test
    public void fromWandererServer() {
        PictureDTO mockedSourcePicture = new PictureDTO();
        Picture mockedPicture = new Picture();
        PlaceDTO source = new PlaceDTO();

        mockedSourcePicture.id = 5;
        mockedSourcePicture.url = "http://adriencadet.com";

        source.id = 10;
        source.country = "USA";
        source.description = "Foobar";
        source.latitude = 45.7890;
        source.longitude = 34.6768;
        source.likes = 45;
        source.is_liking = true;
        source.name = "Barbar";
        source.visit_date = "2015-08-02";
        source.main_picture = mockedSourcePicture;

        mockedPicture.setId(5);
        mockedPicture.setUrl("http://adriencadet.com");

        when(pictureSerializer.fromWandererServer(mockedSourcePicture)).thenReturn(mockedPicture);

        Place outcome = placeSerializer.fromWandererServer(source);

        assertThat(outcome).isNotNull();
        assertThat(outcome.getId()).isEqualTo(10);
        assertThat(outcome.getCountry()).isEqualTo("USA");
        assertThat(outcome.getDescription()).isEqualTo("Foobar");
        assertThat(outcome.getLatitude()).isEqualTo(45.7890);
        assertThat(outcome.getLongitude()).isEqualTo(34.6768);
        assertThat(outcome.getLikes()).isEqualTo(45);
        assertThat(outcome.isLiking()).isEqualTo(true);
        assertThat(outcome.getName()).isEqualTo("Barbar");
        assertThat(outcome.getVisitDate().year().get()).isEqualTo(2015);
        assertThat(outcome.getVisitDate().monthOfYear().get()).isEqualTo(8);
        assertThat(outcome.getVisitDate().dayOfMonth().get()).isEqualTo(02);
        assertThat(outcome.getMainPicture()).isEqualTo(mockedPicture);
    }
}
