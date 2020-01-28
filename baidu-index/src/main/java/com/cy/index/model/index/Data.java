package com.cy.index.model.index;

import java.util.List;

@lombok.Data
public class Data {
    private List<GeneralRatioItem> generalRatio;
    private List<UserIndexesItem> userIndexes;
    private String uniqid;
}