package com.cy.index.model.feedindex;

import lombok.Data;

@Data
public class IndexItem {
    private GeneralRatio generalRatio;
    private String data;
    private String endDate;
    private String type;
    private String key;
    private String startDate;
}
