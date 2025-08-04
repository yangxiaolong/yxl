package com.lego.yxl.core.msg.sender.support;

import com.google.common.collect.Maps;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class JdbcTemplateBasedLocalMessageRepository implements LocalMessageRepository {
    private static final String SQL_INSERT = "insert into %s " +
            "(orderly, topic, sharding_key, tag, msg_key, msg_id, msg, retry_time, status, create_time, update_time)" +
            " values " +
            "(:orderly, :topic, :shardingKey, :tag, :msgKey, :msgId, :msg, :retryTime, :status, :createTime, :updateTime)";

    private static final String SQL_UPDATE = "update %s " +
            "set " +
            " msg_id = :msgId, " +
            " retry_time = :retryTime," +
            " status = :status," +
            " update_time = :updateTime " +
            "where " +
            " id = :id";

    private static final String SQL_LOAD_BY_UPDATE_TIME = "select " +
            "id, orderly, topic, sharding_key, tag, msg_key, msg_id, msg, retry_time, status, create_time, update_time " +
            "from %s " +
            "where update_time > :updateTime and status in (:errorStatus, :noneStatus) LIMIT :limit";

    private final TransactionTemplate transactionTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String tableName;


    public JdbcTemplateBasedLocalMessageRepository(DataSource dataSource, String tableName) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        this.transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.tableName = tableName;
    }

    @Override
    public void save(LocalMessage message) {
        String sql = buildInsertSql();
        SqlParameterSource ps = new BeanPropertySqlParameterSource(message);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.transactionTemplate.execute(transactionStatus -> {
            this.jdbcTemplate.update(sql, ps, keyHolder);
            return null;
        });

        Number id = keyHolder.getKey();
        if (id != null) {
            message.setId(id.longValue());
        }
    }

    private String buildInsertSql() {
        return String.format(SQL_INSERT, tableName);
    }

    @Override
    public void update(LocalMessage message) {
        String sql = buildUpdateSql();
        SqlParameterSource ps = new BeanPropertySqlParameterSource(message);

        this.transactionTemplate.execute(transactionStatus -> {
            this.jdbcTemplate.update(sql, ps);
            return null;
        });

    }

    private String buildUpdateSql() {
        return String.format(SQL_UPDATE, tableName);
    }

    @Override
    public List<LocalMessage> loadNotSuccessByUpdateGt(Date latestUpdateTime, int size) {
        String sql = buildLoadNotSuccess();
        SqlParameterSource ps = buildParameterSource(latestUpdateTime, size);
        RowMapper<LocalMessage> rowMapper = new BeanPropertyRowMapper<>(LocalMessage.class);

        return this.jdbcTemplate.query(sql, ps, rowMapper);
    }

    private SqlParameterSource buildParameterSource(Date latestUpdateTime, int size) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("updateTime", latestUpdateTime);
        param.put("errorStatus", LocalMessage.STATUS_ERROR);
        param.put("noneStatus", LocalMessage.STATUS_NONE);
        param.put("limit", size);
        return new MapSqlParameterSource(param);
    }

    private String buildLoadNotSuccess() {
        return String.format(SQL_LOAD_BY_UPDATE_TIME, tableName);
    }
}
