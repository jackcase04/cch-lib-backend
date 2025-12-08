package com.cs2300.cch_lib.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLibraryRequest {
    Integer selectedSearchOption;
    String bookTitle;
    String bookAuthor;
    Integer isbn;
    String equipmentName;
}
