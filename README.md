# Overview

Matteray is a light-weight library for working with safe, yet performant, arrays and matrices. It is
built as an extension upon the existing java collections framework.

**Note:** The library is currently under BETA and is subject to breaking changes.

## Arrays
Benefits of using the arrays of this package compared to standard java arrays:
 * It has immutable arrays, which means they are thread safe and safe from side effects.
 * The arrays support generic types much better than the standard arrays.
 * There are built-in utilities for working with these arrays, which support things
   sorting and reversing arrays.
 * Arrays can be created easily using `Array.of(elements...)` or with lambda function
   `Array.of(size, i -> element)`.

## Matrices
Matrices can be useful when working with grids or doing matrix calculations
 * It has immutable matrices, which means they are thread safe and safe from side effects.
 * The matrices support generic types much better than standard two dimensional arrays, and are
   easier to work with than nested lists.
 * You can get individual rows or columns as arrays.
 * The whole matrix is a collection, which can be iterated through.
 * There are built-in utilities for working with these matrices, which support things
   like rotating or mirroring matrices.
 * Matrices can be created easily using `Matrix.of(rowArrays...)` or with lambda function
   `Matrix.of(rows, columns, (r, c) -> element)`.
