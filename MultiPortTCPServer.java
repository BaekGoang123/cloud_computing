package cloud_computing;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MultiPortTCPServer {
    public static void main(String[] args) {
        int startingPort = 8080; // 시작 포트 번호
        int numberOfPorts = 80; // 포트 개수

        for (int i = 0; i < numberOfPorts; i++) {
            int port = startingPort + i;
            Thread thread = new Thread(new PortListener(port));
            thread.start();
        }
    }
}

class PortListener implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private boolean running = true; // 스레드 종료 여부를 나타내는 플래그

    public PortListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on port " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established with " + clientSocket.getInetAddress());

                // 요청을 처리하는 스레드 시작
                Thread thread = new Thread(new RequestHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 대기 후에 서버 종료
        try {
            Thread.sleep(3 * 60 * 1000); // 3분 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stop(); // 서버 종료
    }

    // 서버 종료 메서드
    public void stop() {
        running = false; // 루프를 빠져나오도록 플래그 변경
        try {
            if (serverSocket != null) {
                serverSocket.close(); // 서버 소켓 닫기
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class RequestHandler implements Runnable {
    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // HTTP 요청 파싱 및 처리
            String requestLine = in.readLine();
            if (requestLine != null) {
                // GET 메서드와 엔드포인트 추출
                String[] parts = requestLine.split(" ");
                if (parts.length == 3 && parts[0].equals("GET")) {
                    String endpoint = parts[1];
                    System.out.println("Received GET request for endpoint: " + endpoint);

                    // 클라이언트에게 응답 전송
                    String response = "";
                    String contentType = "text/html";
                    if (endpoint.equals("/")) {
                        response = readHtmlFile("/Users/baeggwanglyeol/eclipse-workspace/cloud_computing/src/cloud_computing/main.html");
                    } else if (endpoint.equals("/family")) {
                        response = readHtmlFile("/Users/baeggwanglyeol/eclipse-workspace/cloud_computing/src/cloud_computing/family.html");
                    } else if (endpoint.equals("/info"))
                    {
                        response = readHtmlFile("/Users/baeggwanglyeol/eclipse-workspace/cloud_computing/src/cloud_computing/info.html");
                    } else if (endpoint.equals("/intro")) {
                        response = readHtmlFile("/Users/baeggwanglyeol/eclipse-workspace/cloud_computing/src/cloud_computing/intro.html");
                    } else {
                        response = "Invalid endpoint";
                    }

                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: " + contentType + "charset=UTF-8");
                    out.println("Content-Length: " + response.getBytes().length); // 수정된 부분
                    out.println();
                    out.println(response);
                    System.out.println("response is sent");
                    out.flush(); // 출력 버퍼 비우기
                } else {
                    System.out.println("Invalid request");
                }
            }

            // 클라이언트 소켓 닫기
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readHtmlFile(String filePath) {
        String content = "";
        try {
            // 파일 경로 확인
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + filePath);
                return "File not found"; // 파일이 없으면 에러 메시지 반환
            }

            // 파일 읽기
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
