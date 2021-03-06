// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: cookie_store.proto

package com.example.composepractice;

public interface CookieInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CookieInfo)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>string value = 2;</code>
   * @return The value.
   */
  java.lang.String getValue();
  /**
   * <code>string value = 2;</code>
   * @return The bytes for value.
   */
  com.google.protobuf.ByteString
      getValueBytes();

  /**
   * <code>int64 expiresAt = 3;</code>
   * @return The expiresAt.
   */
  long getExpiresAt();

  /**
   * <code>string domain = 4;</code>
   * @return The domain.
   */
  java.lang.String getDomain();
  /**
   * <code>string domain = 4;</code>
   * @return The bytes for domain.
   */
  com.google.protobuf.ByteString
      getDomainBytes();

  /**
   * <code>string path = 5;</code>
   * @return The path.
   */
  java.lang.String getPath();
  /**
   * <code>string path = 5;</code>
   * @return The bytes for path.
   */
  com.google.protobuf.ByteString
      getPathBytes();

  /**
   * <code>bool secure = 6;</code>
   * @return The secure.
   */
  boolean getSecure();

  /**
   * <code>bool httpOnly = 7;</code>
   * @return The httpOnly.
   */
  boolean getHttpOnly();

  /**
   * <code>bool hostOnly = 8;</code>
   * @return The hostOnly.
   */
  boolean getHostOnly();

  /**
   * <code>bool persistent = 9;</code>
   * @return The persistent.
   */
  boolean getPersistent();
}
