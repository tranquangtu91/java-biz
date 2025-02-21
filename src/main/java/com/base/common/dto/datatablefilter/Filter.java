package com.base.common.dto.datatablefilter;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    String code;
    MatchMode matchMode = MatchMode.CONTAINS;
    Object value;
    Integer order = 0;
    DataType dataType = DataType.string;

    public enum DataType {
        datetime, number, string
    }

    public enum MatchMode {
        BETWEEN("between"),
        CONTAINS("contains"),
        ENDS_WITH("endsWith"),
        EQUALS("equals"),
        GREATER_THAN("greaterThan"),
        GREATER_THAN_OR_EQUALS("greaterThanOrEquals"),
        IN_LIST("inList"),
        LOWERS_THAN("lowersThan"),
        LOWERS_THAN_OR_EQUALS("lowersThanOrEquals"),
        NOT("not"),
        NOT_IN_LIST("notInList"),
        STARTS_WITH("startsWith");

        public final String value;

        MatchMode(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return this.value;
        }
    }
}
