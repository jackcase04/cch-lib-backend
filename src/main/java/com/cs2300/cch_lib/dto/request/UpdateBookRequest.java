package com.cs2300.cch_lib.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
    public UpdateBookRequest(Integer checkedOutBy) {
        this.title = null;
        this.course = null;
        this.bookEdition = null;
        this.condition = null;
        this.isbn = null;
        this.additionalInfo = null;
        this.checkedOut = null;
        this.pdfId = null;
        this.contact = null;
        this.checkedOutBy = checkedOutBy;
    }

    String title;
    String course;
    String bookEdition;
    String condition;
    Integer isbn;
    String additionalInfo;
    Boolean checkedOut;
    Integer pdfId;
    Integer contact;
    Integer checkedOutBy;
}
