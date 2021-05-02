package com.uol.productms.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.uol.productms.model.Product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    
    public static Specification<Product> minPrice(Double minPrice) {
        return new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
        };
    }

    public static Specification<Product> maxPrice(Double maxPrice) {
        return new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    public static Specification<Product> name(String name) {
        return new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
        };
    }

    public static Specification<Product> description(String description) {
        return new Specification<Product>(){
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return description == null ? null : criteriaBuilder.like(root.get("description"), "%" + description + "%");
            }
        };
    }
}