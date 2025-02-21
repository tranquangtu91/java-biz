package com.base.common.dto.formfield;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormField {
    public String code;
    public String name;
    public String labelTooltip;
    public String labelTooltipHtml;

    public String placeHolder;
    public Integer span;
    public FormFieldType type;

    public Object value;
    public Object defaultValue;

    public String pipe = "date";

    // Dùng cho type là text ===============================================
    public Boolean trim = false; // Bỏ khoảng trắng 2 đầu
    public Boolean removeAccent; // Cắt dấu
    // Dùng cho type là text ===============================================

    // Dùng cho type là upload =============================================
    public Boolean uploadMultiple;
    public Long uploadSize; // Dung lượng file tối đa (KB)
    public String uploadFileType;
    public UploadListType uploadListType; // Cách thức thể hiện file được chọn
    public UploadType uploadType = UploadType.Drag; // Cách thao tác để upload file
    // Dùng cho type là upload =============================================

    // Dùng cho type là password ===========================================
    public Boolean passwordVisible;
    // Dùng cho type là password ===========================================

    // Dùng cho type là datePicker, dateRangePicker ========================
    public DatePickerMode datePickerMode;
    public Boolean dataPickerShowTime;
    // Dùng cho type là datePicker, dateRangePicker ========================

    // Dùng cho type là number =============================================
    public Double min;
    public Double max;
    public Double step;
    // Dùng cho type là number =============================================

    // Dùng cho type là select =============================================
    public List<IOption> options;
    public SelectMode selectMode = SelectMode.Default;
    public Boolean isLoading;
    public SelectSource selectSource = SelectSource.Static;
    public String selectSourceUrl;
    public String selectDataKey;
    public Object selectLabelKey; // Type có thể là String hoặc List<String>
    public String selectValueKey;
    public String startsWith;
    public Boolean serverSearch;
    // Dùng cho type là select =============================================

    // Dùng cho type là textarea ===========================================
    public Integer rows;
    // Dùng cho type là textarea ===========================================

    // Thuộc tính validator ================================================
    public Boolean required = false;
    public String pattern;
    public String patternError;
    public Integer maxLength;
    public Integer minLength;
    public String errorTip;
    // Thuộc tính validator ================================================

    // Thuộc tính preview ==================================================
    public Boolean tryPreview = false;
    public ResourceType resourceType;
    public String previewStyle;
    // Thuộc tính preview ==================================================

    public Boolean disabled = false;
    public Boolean inVisible = false;

    public enum FormFieldType {
        Divider, Blank, Text, Textarea, Editor, Password, Email, DatePicker, DateRangePicker, Number, Select, Switch,
        TimePicker, Upload, CheckboxGroup;
    }

    public enum UploadListType {
        Text("text"), Picture("picture"), PictureCard("picture-card");

        public final String value;

        private UploadListType(String value) {
            this.value = value;
        }
    }

    public enum UploadType {
        Drag("drag"), Click("click");

        public final String value;

        private UploadType(String value) {
            this.value = value;
        }
    }

    public enum DatePickerMode {
        Date("date"), Week("week"), Month("month"), Year("year");

        public final String value;

        private DatePickerMode(String value) {
            this.value = value;
        }
    }

    public enum ResourceType {
        Img("img"), Video("video"), Web("web"), Json("json");

        public final String value;

        private ResourceType(String value) {
            this.value = value;
        }
    }

    public enum SelectMode {
        Default("default"), Multiple("multiple"), Tags("tags");

        public final String value;

        private SelectMode(String value) {
            this.value = value;
        }
    }

    public class IOption {
        public String label;
        public Object value;
        public Boolean disabled;
        public Boolean hide;
        public String groupLabel;
    }

    public enum SelectSource {
        Api("api"), Static("static");

        public final String value;

        private SelectSource(String value) {
            this.value = value;
        }
    }
}
