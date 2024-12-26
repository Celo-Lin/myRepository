package com.yl.ad.protobuf;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.1)",
    comments = "Source: YunlianAdx.proto")
public final class DomobGrpc {

  private DomobGrpc() {}

  public static final String SERVICE_NAME = "com.yl.ad.protobuf.Domob";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getAdGetMethod()} instead.
  public static final io.grpc.MethodDescriptor<com.yl.ad.protobuf.AdxDataProtobuf.AdReq,
      com.yl.ad.protobuf.AdxDataProtobuf.AdResp> METHOD_AD_GET = getAdGetMethod();

  private static volatile io.grpc.MethodDescriptor<com.yl.ad.protobuf.AdxDataProtobuf.AdReq,
      com.yl.ad.protobuf.AdxDataProtobuf.AdResp> getAdGetMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<com.yl.ad.protobuf.AdxDataProtobuf.AdReq,
      com.yl.ad.protobuf.AdxDataProtobuf.AdResp> getAdGetMethod() {
    io.grpc.MethodDescriptor<com.yl.ad.protobuf.AdxDataProtobuf.AdReq, com.yl.ad.protobuf.AdxDataProtobuf.AdResp> getAdGetMethod;
    if ((getAdGetMethod = DomobGrpc.getAdGetMethod) == null) {
      synchronized (DomobGrpc.class) {
        if ((getAdGetMethod = DomobGrpc.getAdGetMethod) == null) {
          DomobGrpc.getAdGetMethod = getAdGetMethod =
              io.grpc.MethodDescriptor.<com.yl.ad.protobuf.AdxDataProtobuf.AdReq, com.yl.ad.protobuf.AdxDataProtobuf.AdResp>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "com.yl.ad.protobuf.Domob", "AdGet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yl.ad.protobuf.AdxDataProtobuf.AdReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yl.ad.protobuf.AdxDataProtobuf.AdResp.getDefaultInstance()))
                  .setSchemaDescriptor(new DomobMethodDescriptorSupplier("AdGet"))
                  .build();
          }
        }
     }
     return getAdGetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DomobStub newStub(io.grpc.Channel channel) {
    return new DomobStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DomobBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DomobBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DomobFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DomobFutureStub(channel);
  }

  /**
   */
  public static abstract class DomobImplBase implements io.grpc.BindableService {

    /**
     */
    public void adGet(com.yl.ad.protobuf.AdxDataProtobuf.AdReq request,
        io.grpc.stub.StreamObserver<com.yl.ad.protobuf.AdxDataProtobuf.AdResp> responseObserver) {
      asyncUnimplementedUnaryCall(getAdGetMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAdGetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.yl.ad.protobuf.AdxDataProtobuf.AdReq,
                com.yl.ad.protobuf.AdxDataProtobuf.AdResp>(
                  this, METHODID_AD_GET)))
          .build();
    }
  }

  /**
   */
  public static final class DomobStub extends io.grpc.stub.AbstractStub<DomobStub> {
    private DomobStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DomobStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DomobStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DomobStub(channel, callOptions);
    }

    /**
     */
    public void adGet(com.yl.ad.protobuf.AdxDataProtobuf.AdReq request,
        io.grpc.stub.StreamObserver<com.yl.ad.protobuf.AdxDataProtobuf.AdResp> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAdGetMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DomobBlockingStub extends io.grpc.stub.AbstractStub<DomobBlockingStub> {
    private DomobBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DomobBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DomobBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DomobBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.yl.ad.protobuf.AdxDataProtobuf.AdResp adGet(com.yl.ad.protobuf.AdxDataProtobuf.AdReq request) {
      return blockingUnaryCall(
          getChannel(), getAdGetMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DomobFutureStub extends io.grpc.stub.AbstractStub<DomobFutureStub> {
    private DomobFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DomobFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DomobFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DomobFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yl.ad.protobuf.AdxDataProtobuf.AdResp> adGet(
        com.yl.ad.protobuf.AdxDataProtobuf.AdReq request) {
      return futureUnaryCall(
          getChannel().newCall(getAdGetMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AD_GET = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DomobImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DomobImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AD_GET:
          serviceImpl.adGet((com.yl.ad.protobuf.AdxDataProtobuf.AdReq) request,
              (io.grpc.stub.StreamObserver<com.yl.ad.protobuf.AdxDataProtobuf.AdResp>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DomobBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DomobBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yl.ad.protobuf.AdxDataProtobuf.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Domob");
    }
  }

  private static final class DomobFileDescriptorSupplier
      extends DomobBaseDescriptorSupplier {
    DomobFileDescriptorSupplier() {}
  }

  private static final class DomobMethodDescriptorSupplier
      extends DomobBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DomobMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DomobGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DomobFileDescriptorSupplier())
              .addMethod(getAdGetMethod())
              .build();
        }
      }
    }
    return result;
  }
}
