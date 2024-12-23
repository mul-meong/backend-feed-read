package com.mulmeong.feed.read.api.application;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.mulmeong.feed.read.api.domain.document.ElasticFeed;
import com.mulmeong.feed.read.api.dto.in.FeedSearchRequestDto;
import com.mulmeong.feed.read.api.dto.out.FeedResponseDto;
import com.mulmeong.feed.read.common.utils.CursorPage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FeedSearchServiceImpl implements FeedSearchService {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final ElasticsearchOperations elasticsearchOperations;

    public CursorPage<FeedResponseDto> searchFeeds(FeedSearchRequestDto requestDto) {

        List<Query> shouldQueries = new ArrayList<>();
        shouldQueries.add(new Query.Builder()
            .match(m -> m
                .field("title")
                .query(requestDto.getKeyword())
                .fuzziness("AUTO"))
            .build());

        shouldQueries.add(new Query.Builder()
            .match(m -> m
                .field("content")
                .query(requestDto.getKeyword())
                .fuzziness("AUTO"))
            .build());

        shouldQueries.add(new Query.Builder()
            .match(m -> m
                .field("categoryName")
                .query(requestDto.getKeyword())
                .fuzziness("AUTO"))
            .build());

        shouldQueries.add(new Query.Builder()
            .match(m -> m
                .field("hashtags.name")
                .query(requestDto.getKeyword())
                .fuzziness("AUTO"))
            .build());

        Query query = new Query.Builder()
            .bool(new BoolQuery.Builder().should(shouldQueries).build())
            .build();

        int curPageNo = Optional.ofNullable(requestDto.getPageNo()).orElse(DEFAULT_PAGE_NUMBER);
        int curPageSize = Optional.ofNullable(requestDto.getPageSize()).orElse(DEFAULT_PAGE_SIZE);

        NativeQuery searchQuery = NativeQuery.builder()
            .withQuery(query)
            .withSort(Sort.by(
                Sort.Order.desc("netLikes"),
                Sort.Order.desc("createdAt")))
            .withPageable(PageRequest.of(curPageNo, curPageSize))
            .build();

        SearchHits<ElasticFeed> searchHits = elasticsearchOperations.search(searchQuery,
            ElasticFeed.class);

        long totalHits = searchHits.getTotalHits();
        String nextCursor = null;
        boolean hasNext = false;

        if (totalHits > curPageSize) {
            hasNext = true;
            nextCursor = searchHits.getSearchHit(curPageSize).getContent().getId();
        }

        return new CursorPage<>(
            searchHits.stream().map(SearchHit::getContent).map(FeedResponseDto::fromDocument)
                .toList(), nextCursor, hasNext, searchHits.getSearchHits().size(), curPageNo);
    }

}
