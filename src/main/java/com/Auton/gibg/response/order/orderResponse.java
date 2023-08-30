package com.Auton.gibg.response.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class orderResponse {
    private Long order_id;
    private Long status_id;
}
