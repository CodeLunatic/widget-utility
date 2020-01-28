/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cy.scel.parse;

/**
 * @author will
 */
public class SougouWord implements Comparable {

    private Integer termFrequency;

    private String wordString;

    private String pinYinString;

    SougouWord(String word, String pinYinString, Integer frequency) {
        this.wordString = word;
        this.pinYinString = pinYinString;
        this.termFrequency = frequency;
    }

    public Integer getTermFrequency() {
        return termFrequency;
    }

    public String getWordString() {
        return wordString;
    }

    public String getPinYinString() {
        return pinYinString;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        } else {
            SougouWord sw = (SougouWord) o;

            if (sw.pinYinString != null && this.pinYinString != null) {
                int v = this.pinYinString.compareTo(sw.pinYinString);
                if (v == 0) {
                    if (sw.termFrequency != null && this.termFrequency != null) {
                        return this.termFrequency.compareTo(sw.termFrequency);
                    } else {
                        if (sw.termFrequency == null) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                } else {
                    return v;
                }
            } else {
                if (sw.pinYinString == null) {
                    return -1;
                } else {
                    return 1;
                }
            }

        }
    }
}
