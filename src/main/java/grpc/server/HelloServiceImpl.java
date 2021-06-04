package grpc.server;

import grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(
            HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        String username = request.getFirstName();
        String message = request.getMessage();
        switch (message){
            case "get current time":
                message = "current time is " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                break;
            case "get current date":
                message = "current date is " +  new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
                break;
            case "get link":
                message = "here a link! https://github.com/Warestone";
                break;
            case "get some message":
                message = "враг монолита, искоренить!";
                break;
            default:
                message = "sorry, a dont know this command :(";
        }
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(username+", "+message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
