package com.mulmeong.feed.read.api.domain.document;

import com.mulmeong.feed.read.api.domain.model.Hashtag;
import com.mulmeong.feed.read.api.domain.model.Media;
import com.mulmeong.feed.read.api.domain.model.Visibility;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "feed")
public class Feed {

    @Id
    private String id;
    private String feedUuid;
    private String memberUuid;
    private String title;
    private String content;
    private String categoryName;
    private Visibility visibility;
    private List<Hashtag> hashtags;
    private List<Media> mediaList;
    private Long likeCount;
    private Long dislikeCount;
    private Long netLikes;  // likesCount - dislikeCount
    private Long commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Feed(String id, String feedUuid, String memberUuid, String title, String content,
        String categoryName, Visibility visibility, List<Hashtag> hashtags, List<Media> mediaList,
        Long likeCount, Long dislikeCount, Long netLikes, Long commentCount,
        LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.feedUuid = feedUuid;
        this.memberUuid = memberUuid;
        this.title = title;
        this.content = content;
        this.categoryName = categoryName;
        this.visibility = visibility;
        this.hashtags = hashtags;
        this.mediaList = mediaList;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.netLikes = netLikes;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
