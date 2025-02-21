package com.base.common.service;

import java.util.List;
import java.util.Map;

import com.base.common.dto.data_table_filter.DataTableFilter;
import com.base.common.dto.data_table_response.DataTableResponse;

public interface IEntityService<T> {
    /**
     * Lấy danh sách all record
     * 
     * @return
     */
    List<T> list();

    /**
     * Lấy 1 record theo PK
     * 
     * @param id
     * @param options
     * @return
     */
    T get(Long id, Map<String, Object> options);

    /**
     * Thêm record
     * 
     * @param item
     */
    T save(T item);

    /**
     * Cập nhật 1 record theo PK
     * 
     * @param id
     * @param item
     * @param options
     * @return
     */
    T update(Long id, T item, Map<String, Object> options);

    /**
     * Xóa 1 record theo PK
     * 
     * @param id
     * @return
     */
    T delete(Long id);

    /**
     * Trả danh sách dữ liệu phân trang
     * 
     * @param dataTableFilter
     * @param options
     * @return
     */
    @SuppressWarnings("rawtypes")
    DataTableResponse loadDataTable(DataTableFilter dataTableFilter, LoadDataTableOptions options);
}
