package com.web.ncumt.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 輔助產生 Kibana 查詢連結的元件。
 */
@Component
public class KibanaLinkHelper {

    private static final DateTimeFormatter KIBANA_DATE_FORMATTER = DateTimeFormatter.ISO_INSTANT;
    @Value("${kibana.base-url}")
    private String kibanaBaseUrl; // 應為 "http://host/app/discover"
    @Value("${kibana.index-pattern-id}")
    private String indexPatternId;

    /**
     * 根據給定的 Request ID 產生一個指向 Kibana Discover 頁面的查詢連結。
     *
     * <p>此方法模擬 Kibana UI 建立一個篩選器 (Filter) 的行為，而不是直接填充查詢欄。
     * 這是確保查詢在現代 Kibana 版本中穩定生效的關鍵。
     *
     * @param requestId 用於查詢日誌的唯一請求 ID。
     * @return 一個完整的、可直接在瀏覽器中使用的 Kibana 查詢連結。
     */
    public String generateKibanaLink(String requestId) {
        Instant now = Instant.now();
        Instant from = now.minus(15, ChronoUnit.MINUTES);

        String fromTime = KIBANA_DATE_FORMATTER.format(from);
        String toTime = KIBANA_DATE_FORMATTER.format(now);

        // 這是模擬 Kibana UI 中 "request_id: <id>" 篩選器藥丸的複雜物件
        String filterObject = String.format(
                "('$state':(store:appState),meta:(alias:!n,disabled:!f,index:'%s',key:request_id,negate:!f,params:(query:'%s'),type:phrase),query:(match_phrase:(request_id:'%s')))",
                indexPatternId,
                requestId,
                requestId
        );

        // _g 參數 (Global State) 用於設定時間範圍
        String gParam = String.format(
                "(filters:!(),refreshInterval:(pause:!t,value:0),time:(from:'%s',to:'%s'))",
                fromTime,
                toTime
        );

        // _a 參數 (App State) 使用 filterObject，並將主查詢設為空
        String aParam = String.format(
                "(columns:!(error.stack_trace,message),dataSource:(dataViewId:'%s',type:dataView),filters:!(%s),hideChart:!t,interval:auto,query:(language:kuery,query:''),sort:!(!('@timestamp',desc)))",
                indexPatternId,
                filterObject
        );

        // 手動構建最終的 URL，使用 #/? 作為 SPA 路由的起點
        return String.format("%s#/?_g=%s&_a=%s", kibanaBaseUrl, gParam, aParam);
    }
}
