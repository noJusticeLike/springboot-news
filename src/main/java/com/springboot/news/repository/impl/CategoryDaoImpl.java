package com.springboot.news.repository.impl;

import com.springboot.news.model.Category;
import com.springboot.news.repository.CategoryDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, rowNum) -> {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return category;
    };

    @Override
    @Transactional
    public Long create(Category category) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        PreparedStatementCreator psc = connection -> {
            var ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            return ps;
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Category read(Long id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CATEGORY_ROW_MAPPER, id);
    }

    @Override
    public List<Category> readAll() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, CATEGORY_ROW_MAPPER);
    }

    @Override
    @Transactional
    public void update(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, category.getName(), category.getDescription(), category.getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Category> search(String query) {
        String sql = "SELECT * FROM categories WHERE name LIKE ? OR description LIKE ?";
        String likeQuery = "%" + query + "%";
        return jdbcTemplate.query(sql, CATEGORY_ROW_MAPPER, likeQuery, likeQuery);
    }
}

