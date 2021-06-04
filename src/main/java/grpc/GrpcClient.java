package grpc;

import grpc.server.HelloRequest;
import grpc.server.HelloResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class GrpcClient {
    private static String name="";
    private static HelloServiceGrpc.HelloServiceBlockingStub stub;

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        stub = HelloServiceGrpc.newBlockingStub(channel);
        System.out.println("SYSTEM: Connected to server.");
        setName();
        System.out.println(name+
                ", welcome to Monolith!\nTry 'get current time' or 'list commands'" +
                " for displaying all commands.");
        menu();
        channel.shutdown();
    }

    private static void menu(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            if (message.equals("")) {
                System.out.println("Incorrect input!\n\n");
                menu();
            }
            if (message.equals("exit"))
                return;
            if (message.equals("list commands"))
                System.out.println("\n\nCommand list:" +
                        "\n\t'get current time' - return a system time" +
                        "\n\t'get current date' - return a current date" +
                        "\n\t'get link' - return a link to repository" +
                        "\n\t'get some message' - reverse message"+
                        "\n\t'exit' (application will be closed)\n\n");
            else
                System.out.println("SERVER: " + getServerOutput(message));
        }
    }

    private static void setName(){
        System.out.println("\n\nPlease, enter your name:");
        Scanner scanner = new Scanner(System.in);
        name = scanner.next();
        if (name.equals("")) {
            System.out.println("Incorrect input!\n\n");
            setName();
        }
    }

    private static String getServerOutput(String message){
        HelloResponse response = stub.hello(HelloRequest.newBuilder()
                .setUsername(name)
                .setMessage(message)
                .build());
        return response.getGreeting();
    }
}
