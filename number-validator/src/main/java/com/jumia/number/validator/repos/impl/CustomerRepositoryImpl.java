package com.jumia.number.validator.repos.impl;

import com.jumia.number.validator.dto.CountryNumberSchema;
import com.jumia.number.validator.dto.SearchCriteria;
import com.jumia.number.validator.models.Customer;
import com.jumia.number.validator.properties.CountryCodeProperties;
import com.jumia.number.validator.repos.CustomCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.sqlite.Function;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
class CustomerRepositoryImpl implements CustomCustomerRepository {
    @PersistenceContext
    private EntityManager em;
    private final CountryCodeProperties countryCodeProperties;
    private String selectDataDetailed;

    @PostConstruct
    void createSqlCases() {
        createFullDetailedQuery();
    }

    private void addSqliteRegexpFunction() {
        Session session = (Session) em.getDelegate();
        session.doWork((connection) -> {
            Function.create(connection, "REGEXP", new Function() {
                @Override
                protected void xFunc() throws SQLException {
                    String expression = value_text(0);
                    String value = value_text(1);
                    if (value == null)
                        value = "";

                    Pattern pattern = Pattern.compile(expression);
                    result(pattern.matcher(value).find() ? 1 : 0);
                }
            });

        });
    }

    private void createFullDetailedQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT *,CASE ");
        for (Map.Entry<String, CountryNumberSchema> entry : countryCodeProperties.getCountriesSchema().entrySet()) {
            queryBuilder.append(String.format("WHEN country = '%s' and  phone REGEXP('%s') THEN 'VALID'", entry.getKey(), entry.getValue().getNumberRegex()));
        }
        queryBuilder.append("ELSE 'NOT_VALID' END state from").append(createCountryCodeQueryCase());
        selectDataDetailed = queryBuilder.toString();
    }

    private String createCountryCodeQueryCase() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("(SELECT *,CASE ");
        for (Map.Entry<String, CountryNumberSchema> entry : countryCodeProperties.getCountriesSchema().entrySet()) {
            queryBuilder.append(String.format("WHEN phone REGEXP('%s') THEN '%s'", entry.getValue().getCountryCode(), entry.getKey()));
        }
        queryBuilder.append("ELSE 'NA' END country from customer)");
        return queryBuilder.toString();
    }

    public Page<Customer> findAllCategorized(SearchCriteria criteria) {
        addSqliteRegexpFunction();
        String condition = criteria.asCondition();
        String customQuery = createQueryString(condition, criteria);
        int total = countByQuery(condition);
        List resultList = Collections.emptyList();
        if (total > 0) {
            resultList = getCriteriaQuery(criteria, customQuery).getResultList();
        }
        return new PageImpl<Customer>(resultList, criteria.asPageable(), total);
    }

    private Query getCriteriaQuery(SearchCriteria criteria, String customQuery) {
        Query query = em.createNativeQuery(customQuery, "CustomerMapping");
        query.setMaxResults(criteria.asPageable().getPageSize());
        query.setFirstResult((int) criteria.asPageable().getOffset());
        return query;
    }

    private String createQueryString(String condition, SearchCriteria criteria) {
        String customQuery = selectDataDetailed;
        customQuery += condition;
        customQuery = QueryUtils.applySorting(customQuery, criteria.asPageable().getSort());
        return customQuery;
    }

    private int countByQuery(String condition) {
        return (int) em.createNativeQuery(String.format("select count(*) from (%s) %s", selectDataDetailed, condition)).getSingleResult();
    }
}
