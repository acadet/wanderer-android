package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * PictureSerializerTest
 * <p>
 */
public class PictureSerializerTest {

    IPictureSerializer pictureSerializer;

    @Before
    public void setup() {
        pictureSerializer = new PictureSerializer();
    }

    @After
    public void tearDown() {
        pictureSerializer = null;
    }

    @Test
    public void fromWandererServer() {
        PictureDTO source = new PictureDTO();
        source.id = 56;
        source.url = "htt://foo.com";

        Picture outcome = pictureSerializer.fromWandererServer(source);

        assertThat(outcome).isNotNull();
        assertThat(outcome.getId()).isEqualTo(56);
        assertThat(outcome.getUrl()).isEqualTo("htt://foo.com");
    }

    @Test
    public void fromWandererServerList() {
        PictureDTO p1 = new PictureDTO(), p2 = new PictureDTO(), p3 = new PictureDTO();
        List<PictureDTO> pictures;

        p1.id = 4;
        p1.url = "http://mockito.org/";
        p2.id = 5;
        p2.url = "https://github.com/google/truth";
        p3.id = 7;
        p3.url = "http://google.github.io/truth/";
        pictures = Arrays.asList(p1, p2, p3);

        List<Picture> outcome = pictureSerializer.fromWandererServer(pictures);

        assertThat(outcome).isNotNull();
        assertThat(outcome.size()).isEqualTo(3);
        assertThat(outcome.get(0).getId()).isEqualTo(4);
        assertThat(outcome.get(0).getUrl()).isEqualTo("http://mockito.org/");
        assertThat(outcome.get(1).getId()).isEqualTo(5);
        assertThat(outcome.get(1).getUrl()).isEqualTo("https://github.com/google/truth");
        assertThat(outcome.get(2).getId()).isEqualTo(7);
        assertThat(outcome.get(2).getUrl()).isEqualTo("http://google.github.io/truth/");
    }
}
