package com.application.mainApplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class HttpOutBound {
    private String abbreviation;
    private String client_ip;
    private String datetime;
    private Integer day_of_week;
    private Integer day_of_year;
    private Boolean dst;
    private String dst_from;
    private Integer dst_offset;
    private String dst_until;
    private Integer raw_offset;
    private String timezone;
    private Long unixtime;
    private String utc_datetime;
    private String utc_offset;
    private Integer week_number;
}
