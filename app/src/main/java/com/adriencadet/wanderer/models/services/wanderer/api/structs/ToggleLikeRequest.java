package com.adriencadet.wanderer.models.services.wanderer.api.structs;

/**
 * ToggleLikeRequest
 * <p>
 */
public class ToggleLikeRequest {
    public static class Like {
        public String deviceToken;
    }

    public Like like;
}
