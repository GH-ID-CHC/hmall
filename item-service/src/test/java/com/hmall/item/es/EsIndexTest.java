package com.hmall.item.es;

import cn.hutool.core.bean.BeanUtil;
import com.hmall.item.domain.po.Item;
import com.hmall.item.domain.po.ItemDoc;
import com.hmall.item.service.ItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 索引库测试
 *
 * @program: hmall
 * @author:
 * @create: 2024-11-17 18:52
 */
public class EsIndexTest {
    private RestHighLevelClient restClient;


    @Test
    void testEsClint(){
        System.out.println(restClient);
    }

    @BeforeEach
    void setUp(){
        restClient=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.114.128:9200")
        ));
    }
    static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      },\n" +
            "      \"price\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"stock\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"category\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"brand\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"sold\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"commentCount\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"isAD\":{\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"updateTime\":{\n" +
            "        \"type\": \"date\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    /**
     * 创建索引库
     *
     * @throws IOException ioexception
     */
    @Test
    void testCreateEs() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("item");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        //发送请求
        restClient.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 测试查询es
     * 测试是否存在
     * @throws IOException ioexception
     */
    @Test
    void testSelectEs() throws IOException {
        GetIndexRequest request = new GetIndexRequest("item");
//        restClient.indices().get(request, RequestOptions.DEFAULT);
        boolean exists = restClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("存在："+exists);

    }

    /**
     * 测试删除索引库
     *
     * @throws IOException ioexception
     */
    @Test
    void testDeleteEs() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("item");
        restClient.indices().delete(request, RequestOptions.DEFAULT);

    }
    @AfterEach
    void tearDrow(){
        if (restClient!=null){
            try {
                restClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
