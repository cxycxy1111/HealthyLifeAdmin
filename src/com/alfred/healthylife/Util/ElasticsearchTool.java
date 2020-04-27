package com.alfred.healthylife.Util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchTool {

    private static final String HOST = "localhost";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";
    private static final String ES_NAME = "tip";
    private static final int LENGTH = 10;

    public static RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(HOST, PORT, SCHEMA));
        return new RestHighLevelClient(builder);
    }

    /**
     * 多条件查询，多个字段查询
     *
     * @param value
     * @return
     */
    public static String boolQuery(String value, int from) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders
                .multiMatchQuery(value, "title", "summary");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("del", 0);
        boolQueryBuilder.must(multiMatchQueryBuilder).must(termQueryBuilder);
        return listSearchResult(boolQueryBuilder, from);
    }

    /**
     * QueryBuilder
     *
     * @return
     * @throws IOException
     */
    public static String matchQuery(String value, int from) throws IOException {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", value);
        return listSearchResult(queryBuilder, from);
    }

    /**
     * WildcardQueryBuilder 模糊匹配
     *
     * @return
     * @throws IOException
     */
    public static String wildCardQuery(String value, int from) throws IOException {
        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name",
                "*" + value + "*");
        return listSearchResult(wildcardQueryBuilder, from);
    }

    /**
     * MatchAllQueryBuilder
     *
     * @return
     * @throws IOException
     */
    public static String matchAllQuery(int from) throws IOException {
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        return listSearchResult(matchAllQueryBuilder, from);
    }

    /**
     * 后文段模糊查找方法
     *
     * @param key
     * @param prefix
     * @return
     */
    public static String prefixQuery(String key, String prefix, int from) {
        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery(key, prefix);
        return listSearchResult(prefixQueryBuilder, from);
    }

    /**
     * 用来处理搜索结果，转换成链表
     *
     * @param queryBuilder
     * @return
     */
    public static String listSearchResult(QueryBuilder queryBuilder, int from) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(LENGTH);
        searchSourceBuilder.query(queryBuilder);

        SearchRequest searchRequest = new SearchRequest(ES_NAME);
        searchRequest.source(searchSourceBuilder);
        RestHighLevelClient client = restHighLevelClient();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SearchHits hits = null;
        if (searchResponse != null) {
            hits = searchResponse.getHits();
        }
        SearchHit[] hitsHits = new SearchHit[0];
        if (hits != null) {
            hitsHits = hits.getHits();
        }
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hitsHit : hitsHits) {
            Map<String, Object> sourceAsMap = hitsHit.getSourceAsMap();
            list.add(sourceAsMap);
        }
        return JSON.toJSONString(list);
    }

    /**
     * 升级文档
     *
     * @param id
     * @param title
     * @param summary
     * @param del
     * @throws IOException
     */
    public static void updateDocument(long id, String title, String summary, int del) throws IOException {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(id));
        hashMap.put("title", title);
        hashMap.put("summary", summary);
        hashMap.put("del", del);
        UpdateRequest updateRequest = new UpdateRequest(ES_NAME, String.valueOf(id));
        updateRequest.doc(hashMap);
        restHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 升级文档，不更新DEL字段
     *
     * @param id
     * @param title
     * @param summary
     * @throws IOException
     */
    public static void updateDocumentWithoutDelete(long id, String title, String summary) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", id);
            builder.field("title", title);
            builder.field("summary", summary);
        }
        builder.endObject();
        UpdateRequest updateRequest = new UpdateRequest(ES_NAME, String.valueOf(id));
        updateRequest.doc(builder);
        restHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 升级文档，只更新DEL字段
     *
     * @param id
     * @param del
     * @throws IOException
     */
    public static void updateDocumentWithDelete(long id, int del) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("id", id);
            builder.field("del", del);
        }
        builder.endObject();
        UpdateRequest updateRequest = new UpdateRequest(ES_NAME, String.valueOf(id));
        updateRequest.doc(builder);
        restHighLevelClient().update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 批量插入文档
     *
     * @param list
     * @throws IOException
     */
    public static void bulkRequest(ArrayList<HashMap<String, Object>> list) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap = list.get(i);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.field("id", Util.getIntFromMap(hashMap, "id"))
                    .field("title", hashMap.get("title"))
                    .field("summary", hashMap.get("summary"))
                    .field("del", Util.getIntFromMap(hashMap, "del"));
            builder.endObject();

            IndexRequest indexRequest = new IndexRequest(ES_NAME)
                    .id(String.valueOf(hashMap.get("id")))
                    .source(builder);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient().bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    /**
     * 判断文档是否存在
     *
     * @param id
     * @throws IOException
     */
    public static boolean isExistDocument(long id) throws IOException {
        GetRequest getRequest = new GetRequest("index1", String.valueOf(id));
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient().exists(getRequest, RequestOptions.DEFAULT);
    }

}