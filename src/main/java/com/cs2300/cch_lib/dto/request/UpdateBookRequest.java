package com.cs2300.cch_lib.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    String title;
    String course;
    String bookEdition;
    String condition;
    Integer isbn;
    String additionalInfo;
    Integer bookId;
}
