package com.example.rxjava3.experimental;

import com.example.rxjava3.rx.jurel.mapping.BaseMapper;

// TODO: Experimental.

// Define an interface for the mapping functionality
public interface ObvestiloMapper<From, To> extends BaseMapper<From, To> {
    // You can add additional methods specific to this mapping if needed
}

// Provide concrete implementations for both directions
//@Singleton
//public class ObvestiloDomainModelMapper implements ObvestiloMapper<ObvestiloDomain, ObvestiloModel> {
//    // Implement the mapping logic from ObvestiloDomain to ObvestiloModel
//    // ...
//}
//
//@Singleton
//public class ObvestiloModelDomainMapper implements ObvestiloMapper<ObvestiloModel, ObvestiloDomain> {
//    // Implement the mapping logic from ObvestiloModel to ObvestiloDomain
//    // ...
//}
