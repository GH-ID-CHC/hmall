package com.hmall.item.es;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.hmall.item.domain.po.Item;
import com.hmall.item.domain.po.ItemDoc;
import com.hmall.item.service.ItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
@SpringBootTest(properties = "spring.profiles.active=local")
public class EsDocumentTest {
    private RestHighLevelClient restClient;

    @Autowired
    private ItemService itemService;

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

    /**
     * 创建文档数据
     *
     * @throws IOException ioexception
     */
    @Test
    void testCreateDoc() throws IOException {
        Item item = itemService.getById(100002644680L);
        ItemDoc itemDoc = BeanUtil.copyProperties(item, ItemDoc.class);
        IndexRequest request = new IndexRequest("item").id(itemDoc.getId());
        request.source(JSONUtil.toJsonStr(itemDoc), XContentType.JSON);
        restClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * 测试查询文档
     *
     * @throws IOException ioexception
     */
    @Test
    void testSelectDoc() throws IOException {
        GetRequest request = new GetRequest("item","100002644680");
        GetResponse response = restClient.get(request, RequestOptions.DEFAULT);
        //处理响应结果
        String jsonSource = response.getSourceAsString();
        System.out.println(jsonSource);
    }
    /**
     * 测试删除文档
     *
     * @throws IOException ioexception
     */
    @Test
    void testDeleteDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("item","100002644680");
        restClient.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 测试更新文档
     *
     * @throws IOException ioexception
     */
    @Test
    void testUpdateDoc() throws IOException {
        UpdateRequest request = new UpdateRequest("item", "100002644680");
        request.doc("brand", "小米","price", "1999");
        restClient.update(request, RequestOptions.DEFAULT);
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
