package com.cs2300.cch_lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {
    String title;
    String course;
    String bookEdition;
    String condition;
    Integer isbn;
    String additionalInfo;
    Integer contact;
    List<AuthorRequest> authors;
}
