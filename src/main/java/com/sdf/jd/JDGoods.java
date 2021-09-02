package com.sdf.jd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author barry
 * @date 2021/9/2 3:43 下午
 * @description
 */
@Data
@Builder
@AllArgsConstructor
public class JDGoods {

    private BaseBigFieldInfo baseBigFieldInfo;
    private CategoryInfo categoryInfo;
    private ImageInfo imageInfo;
    private Long skuId;
    private String skuName;

    public JDGoods() {
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class BaseBigFieldInfo {
        private String propCode;
        private String wareQD;
        private String wdis;

        public BaseBigFieldInfo() {
        }
    }


    @Data
    @Builder
    @AllArgsConstructor
    public static class CategoryInfo {
        private Integer cid1;
        private String cid1Name;
        private Integer cid2;
        private String cid2Name;
        private Integer cid3;
        private String cid3Name;

        public CategoryInfo() {
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class ImageInfo {
        private List<UrlInfo> imageList;

    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class  UrlInfo {
        private String url;

        public UrlInfo() {
        }
    }


}
