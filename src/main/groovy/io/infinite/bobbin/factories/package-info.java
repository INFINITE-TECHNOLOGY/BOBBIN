/**
 *
 * This package contains classes searching for Bobbin config.
 * Each class searches in its own location (determined by class code)
 * and if config is not found - it calls its super class
 * to continue search.
 *
 * It seems that for testability using unit tests it is good practice to use
 * inheritance in favor of composition as it simplifies mocking/overloading.
 *
 * Additionally code is quite readable as changes are clearly tracked between classes in
 * same hierarchy.
 */
package io.infinite.bobbin.factories;