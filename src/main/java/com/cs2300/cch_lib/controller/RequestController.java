package com.cs2300.cch_lib.controller;

import com.cs2300.cch_lib.dto.request.AddRequestRequest;
import com.cs2300.cch_lib.exception.UnauthorizedException;
import com.cs2300.cch_lib.model.entity.Request;
import com.cs2300.cch_lib.service.AuthenticationService;
import com.cs2300.cch_lib.service.RequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {
    private final RequestService requestService;
    private final AuthenticationService authenticationService;

    public RequestController(RequestService requestService, AuthenticationService authenticationService) {
        this.requestService = requestService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/add")
    public Request addNewRequest(
            @RequestBody AddRequestRequest request,
            HttpSession session
    ) {
        if (!authenticationService.isLoggedIn(session)) {
            throw new UnauthorizedException("Please log in");
        }

        Integer userId = (Integer) session.getAttribute("userId");

        return requestService.addNewRequest(
                userId,
                request.getResourceType(),
                request.getResourceId(),
                request.getRequestType()
        );
    }
}
