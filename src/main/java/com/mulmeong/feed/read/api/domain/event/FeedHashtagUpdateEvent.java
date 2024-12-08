package com.mulmeong.feed.read.api.domain.event;

import com.mulmeong.feed.read.api.domain.document.Feed;
import com.mulmeong.feed.read.api.domain.model.Hashtag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedHashtagUpdateEvent {

    private String feedUuid;
    private List<Hashtag> hashtags;
    private LocalDateTime updatedAt;

    public Feed toDocument(Feed existingFeed) {
        return Feed.builder()
            .id(existingFeed.getId())
            .feedUuid(feedUuid)
            .memberUuid(existingFeed.getMemberUuid())
            .title(existingFeed.getTitle())
            .content(existingFeed.getContent())
            .categoryName(existingFeed.getCategoryName())
            .visibility(existingFeed.getVisibility())
            .hashtags(hashtags)
            .mediaList(existingFeed.getMediaList())
            .likeCount(existingFeed.getLikeCount())
            .dislikeCount(existingFeed.getDislikeCount())
            .netLikes(existingFeed.getNetLikes())
            .commentCount(existingFeed.getCommentCount())
            .createdAt(existingFeed.getCreatedAt())
            .updatedAt(updatedAt)
            .build();
    }

}
