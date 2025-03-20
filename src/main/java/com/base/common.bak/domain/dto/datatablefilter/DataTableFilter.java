package com.base.common.domain.dto.datatablefilter;

import java.util.Map;

import org.json.JSONObject;

import com.base.common.application.utils.convert.object.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataTableFilter {
    Integer first;
    Integer rows;
    Map<String, Filter> filters;
    String sortField;
    SortOrder sortOrder = SortOrder.DESC;
    GlobalSearchParam globalSearchParam;

    public enum SortOrder {
        ASC(1), DESC(-1);

        public final Integer val;

        private SortOrder(Integer val) {
            this.val = val;
        }

        @JsonValue
        public Integer getVal() {
            return val;
        }
    }

    public Integer getSortOrder() {
        return sortOrder.val;
    }

    public static DataTableFilter mapToObject(Map<String, Object> map) {
        if (map.containsKey("filters")) {
            if (map.get("filters") instanceof String) {
                String filters = map.get("filters").toString();
                JSONObject jFilters = new JSONObject(filters);
                map.put("filters", jFilters.toMap());
            }
        }
        DataTableFilter dtf = (DataTableFilter) ObjectUtils.mapToObject(DataTableFilter.class, map);
        return dtf;
    }

    public class GlobalSearchParam {
        public String[] keys;
        public Object value;
    }
}
