package com.mulmeong.feed.read.api.domain.event;

import com.mulmeong.feed.read.api.domain.document.Feed;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FeedUpdateEvent {

    private String feedUuid;
    private String title;
    private String content;
    private String categoryName;
    private LocalDateTime updatedAt;

    public Feed toDocument(Feed existingFeed) {
        return Feed.builder()
            .id(existingFeed.getId())
            .feedUuid(feedUuid)
            .memberUuid(existingFeed.getMemberUuid())
            .title(title)
            .content(content)
            .categoryName(categoryName)
            .visibility(existingFeed.getVisibility())
            .hashtags(existingFeed.getHashtags())
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
