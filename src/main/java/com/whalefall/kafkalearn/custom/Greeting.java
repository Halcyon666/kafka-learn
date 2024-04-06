package com.whalefall.kafkalearn.custom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Halcyon
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greeting {

    private String msg;
    private String name;

}
