package com.hyk.learningtest.template;

public interface LineCallback<T> {

  T doSomethingWithLine(String line, T value);

}
