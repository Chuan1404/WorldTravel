package com.worldstory.travel.utils;

import com.worldstory.travel.specifications.ModelSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@Getter
@Setter
public class Pagination<T> {

    private ModelSpecification<T> modelSpecification = new ModelSpecification<>();

    private List<Specification<T>> specifications = new ArrayList<>();

    public Pagination() {

    }

    public Pagination(Map<String, String> params) {
        if(params.get("isActive") != null && !params.get("isActive").trim().isEmpty())
            specifications.add(modelSpecification.findActive(Boolean.valueOf(params.get("isActive"))));

        if(params.get("kw") != null && !params.get("kw").trim().isEmpty())
            specifications.add(modelSpecification.findByKw(params.get("kw")));

        if(params.get("from") != null && !params.get("from").trim().isEmpty())
            specifications.add(modelSpecification.greaterThanOrEqualTo(Double.parseDouble(params.get("from"))));

        if(params.get("to") != null && !params.get("to").trim().isEmpty())
            specifications.add(modelSpecification.lessThanOrEqualTo(Double.parseDouble(params.get("to"))));
    }

    public Pageable page(String page, String limit) {
        Pageable pageable = null;
        if (limit == null)
            limit = "30";

        if (page == null || Integer.parseInt(page) < 1)
            page = "1";
        try {
            pageable = PageRequest.of(Integer.parseInt(page) - 1, Integer.parseInt(limit));
            return pageable;
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

}
