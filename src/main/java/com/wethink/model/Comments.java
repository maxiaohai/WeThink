package com.wethink.model;

import lombok.Data;
import java.util.Date;

@Data
public class Comments {
    private String id;
    private String articleId;
    private String parentId;
    private String userId;
    private String content;
    private String username;
    private Date createTime;
}
