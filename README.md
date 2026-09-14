# Java Parallel Web Crawler

A Java-based web crawler that demonstrates concurrent web-page crawling, thread-safe URL processing, dynamic proxies, dependency injection, profiling, JSON configuration, and functional word-count analysis.

## Overview

This project implements both sequential and parallel web crawling strategies. The parallel crawler uses Java concurrency utilities to fetch and process multiple pages efficiently while preventing duplicate URL visits and respecting configured crawl timeouts.

The project also includes a transparent profiling mechanism using Java dynamic proxies to record execution times for annotated methods.

## Key Features

- Sequential and parallel web crawling
- Real web-page crawling
- Recursive parallel processing using `ForkJoinPool`
- Thread-safe URL tracking
- Configurable crawl timeout
- Java dynamic proxy-based method profiling
- JSON configuration loading
- JSON result generation
- Word frequency analysis using Java Stream API
- Dependency injection using Guice
- Automated unit testing

## Technologies

- Java
- Maven
- Guice
- ForkJoinPool
- RecursiveAction
- ConcurrentHashMap
- ConcurrentSkipListSet
- Java Reflection API
- Dynamic Proxy
- Java Stream API
- JSoup
- JSON
- JUnit

## Architecture

The application uses a strategy-based architecture to support different crawling implementations:

```text
Crawler
   |
   +-- SequentialWebCrawler
   |
   +-- ParallelWebCrawler
             |
             +-- ForkJoinPool
             +-- RecursiveAction
             +-- Thread-safe URL tracking
