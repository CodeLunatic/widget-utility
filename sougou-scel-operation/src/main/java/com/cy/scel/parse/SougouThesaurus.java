/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cy.scel.parse;

import java.util.List;

/**
 * @author will
 */
public class SougouThesaurus {

    private String name;

    private String category;

    private String description;

    private String example;

    private List<SougouWord> swList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<SougouWord> getSwList() {
        return swList;
    }

    public void setSwList(List<SougouWord> swList) {
        this.swList = swList;
    }

    @Override
    public String toString() {
        return "SougouThesaurus{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", example='" + example + '\'' +
                ", swList=" + swList +
                '}';
    }
}
