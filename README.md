# cloud_computing
안녕하십니까 저의 cloud_computing 프로젝트에 찾아주셔서 감사합니다.
기본적으로 5가지 파일이 있습니다.

첫 번째는 MultiPortTCPServer.java 파일입니다.

1.MulyiPortTCPServer 클래스
-  main 메서드:
  - 'startingPort'와 'numberOfPorts' 변수를 정의하여 시작 포트와 포트의 개수를 설정합니다.
  - 포트의 개수만큼 반복문을 통해 'Portlistener' 객체를 생성하고 해당 포트에서 스레드를 시작합니다.
    
2.PortListener 클래스
- 맴버 변수:
  - 'port': 해당 PortListener가 리스닝하는 포트 번호를 저장합니다.
  - 'serverSocket': 서버 소켓을 저장하기 위한 변수입니다.
  - 'ruuning': 스레드 종료 여부를 나타내는 플래그입니다.
- 생성자:
  - 포트 번호를 매개변수로 받아 'port' 변수를 초기화합니다.   
- run 메서드
  - 서버 소켓을 생성하고 해당 포트에서 리스닝을 시작합니다.
  - 클라이언트의 연결을 수락하고 'RequestHandler' 객체를 생성하여 요청을 처리하는 스레드를 시작합니다.
- stop 메서드:
  - 'running' 플래스를 false로 설정하여 'run' 메서드의 루프를 종료하도록 합니다.
  - 서버 소켓을 닫습니다.

3. RequestHandler 클래스
   - 맴버 변수:
     - 'clientSocket': 클라이언트 소켓을 저장하기 위한 변수입니다.
   - 생성자:
     - 클라이언트 소켓을 매개변수로 받아 초기화합니다.
   - run 메서드:
     - 클라이언트 소켓에서 입력 스트림과 출력 스트림을 생성합니다.
     - HTTP GET 요청을 읽고 해당하는 HTML 파일을 읽어 클라이언트에게 응답합니다.
   - readHtmlFile 메서드:
     - 파일 경로를 매개변수로 받아 해당 HTML 파일의 내용을 문자열로 읽어 반화합니다.

나머지는 HTML 파일입니다.
기본적인 서버 구성은 저에 대한 자기소개입니다.
간단한 서버의 구현을 목적으로 하였기 때문에 서버의 기능 자체에는 많은 것을 구현하지 않았습니다.
