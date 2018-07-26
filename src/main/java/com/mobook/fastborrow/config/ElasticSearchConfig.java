package com.mobook.fastborrow.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:42 2018\7\25 0025
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    public TransportClient esClient() throws UnknownHostException{
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Settings settings = Settings.builder()
                .put("cluster.name","mobook")
                //.put("client.transport.sniff","true")
                .build();
        TransportAddress master = new TransportAddress(
               InetAddress.getByName("120.77.152.252"),9300
        );

        TransportClient esClient = new PreBuiltTransportClient(settings)
                .addTransportAddress(master);

        return esClient;
    }

}
